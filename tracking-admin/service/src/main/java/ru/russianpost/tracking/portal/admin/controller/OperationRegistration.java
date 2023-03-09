/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.controller;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.api.protobuf.messages.PostalEvent;
import ru.russianpost.tracking.portal.admin.ConfigHolder;
import ru.russianpost.tracking.portal.admin.model.operation.FullnessValidationResult;
import ru.russianpost.tracking.portal.admin.model.operation.JsonResponse;
import ru.russianpost.tracking.portal.admin.model.operation.NiipsOperation;
import ru.russianpost.tracking.portal.admin.model.operation.OpsInfo;
import ru.russianpost.tracking.portal.admin.service.OasuRpoRtm30Client;
import ru.russianpost.tracking.portal.admin.service.kafka.KafkaService;
import ru.russianpost.tracking.portal.admin.service.postoffice.PostOfficeClient;
import ru.russianpost.tracking.portal.admin.service.protobuf.ProtobufConverter;
import ru.russianpost.tracking.portal.admin.service.util.Rtm30ContainerBuilder;
import ru.russianpost.tracking.portal.admin.utils.KibanaLogger;
import ru.russianpost.tracking.portal.admin.validation.validators.Rtm05FullnessValidator;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;

/**
 * Controller responsible for handling requests from operation registration pages.
 * @author mkitchenko
 * @version 1.0 08.10.2018
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class OperationRegistration {
    private static final boolean USE_RTM_30 = ConfigHolder.CONFIG.getBoolean("rtm30.enable");
    private static final String RTM_30_DEFAULT_CREATION_INDEX = ConfigHolder.CONFIG.getString("rtm30.container.creation.index");
    private static final String RTM_30_DEFAULT_SOFTWARE_VERSION = ConfigHolder.CONFIG.getString("rtm30.container.software.version");
    private static final String RTM_30_DLL_VERSION = ConfigHolder.CONFIG.getString("rtm30.container.dll.version");
    private static final boolean LOG_WEB_INPUT_XML = ConfigHolder.CONFIG.getBoolean("rtm30.log.web.input.xml");

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final String EMS_FORM_TYPE = "ems";
    private static final String EMS_INTERNATIONAL_INDEX_TO = "104010";
    private static final String MRPO_INTERNATIONAL_INDEX_TO = "104000";
    private static final String INDEX_NOT_EXIST_MESSAGE = "{0} не зарегистрирован в эталонном справочнике.";
    private static final String CHECKING_INDEXES_IS_NOT_AVAILABLE = "Проверка введенных индексов недоступна. Повторите операцию позже.";

    private final StopWatchFactory stopWatchFactory;

    private final PostOfficeClient postOfficeClient;
    private final OasuRpoRtm30Client oasuRpoRtm30Client;
    private final KafkaService kafkaService;
    private final ProtobufConverter protobufConverter;
    private final TaskExecutor rtm30SendingTaskExecutor;

    /**
     * Registers the operation from form data (Old API).
     * @param niipsOperation form data.
     * @param bindingResult  bean validation result.
     * @return json response.
     */
    @PostMapping(value = "/api/data")
    public JsonResponse registerNiipsOperation(
        @Valid @RequestBody final NiipsOperation niipsOperation,
        final BindingResult bindingResult
    ) {

        final StopWatch stopWatch = stopWatchFactory.getStopWatch(
            "OperationRegistration:registerNiipsOperation:" + niipsOperation.getAction()
        );

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username;
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName();
        } else {
            KibanaLogger.logOperationRegistrationInfo(niipsOperation, "anonymous", false, "authenticationError", null);
            stopWatch.stop("OperationRegistration:registerNiipsOperation:authenticationError");
            return new JsonResponse(false, "Пользователь не аутентифицирован.");
        }

        final List<JsonResponse.ValidationError> validationErrors = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                .forEach(fieldError -> validationErrors.add(new JsonResponse.ValidationError(fieldError.getField(), fieldError.getDefaultMessage())));
        }

        if (EMS_FORM_TYPE.equals(niipsOperation.getFormType()) && !isValidCheckDigit(niipsOperation.getBarCode())) {
            validationErrors.add(
                new JsonResponse.ValidationError("", "Некорректный контрольный разряд ШПИ (EMS-идентификатора).")
            );
        }

        validateIndexes(niipsOperation, validationErrors);

        final FullnessValidationResult validationResult = Rtm05FullnessValidator.validate(niipsOperation);
        if (validationResult.hasErrors()) {
            for (final String errorMessage : validationResult.getErrorMessages()) {
                validationErrors.add(new JsonResponse.ValidationError("", errorMessage));
            }
        }

        if (!validationErrors.isEmpty()) {
            KibanaLogger.logOperationRegistrationInfo(niipsOperation, username, false, "validationError", null);
            stopWatch.stop("OperationRegistration:registerNiipsOperation:validationError");
            return new JsonResponse(false, null, validationErrors);
        }

        log.debug("Registering operation: {}", niipsOperation);
        final PostalEvent.Event event;
        try {
            event = protobufConverter.convertToPostalEvent(niipsOperation);
            log.debug("Converted to protobuf:\n{}", event);

            final String niipsOperationShortString = niipsOperation.toShortString();
            if ("add".equals(niipsOperation.getAction())) {

                if (USE_RTM_30) {
                    try {
                        rtm30SendingTaskExecutor.execute(new Rtm30SendingTask(event));
                    } catch (Exception e) {
                        log.error("Cannot send RTM-30 data to Niips, error in TaskExecutor.", e);
                    }
                }

                final byte[] key = niipsOperation.getBarCode().getBytes(DEFAULT_CHARSET);
                final byte[] value = event.toByteArray();
                kafkaService.produceToInput(key, value);
                log.debug("Protobuf message has been sent to kafka.");
                log.info(
                    "Operation \"{}\" has been successfully registered by user \"{}\".",
                    niipsOperationShortString,
                    username
                );
            } else {
                log.info("Operation \"{}\" has been successfully validated.", niipsOperationShortString);
            }
        } catch (Exception e) {
            log.error("Cannot register operation: " + niipsOperation, e);
            KibanaLogger.logOperationRegistrationInfo(niipsOperation, username, false, "internalError", null);
            stopWatch.stop("OperationRegistration:registerNiipsOperation:fail");
            return new JsonResponse(false, "Произошла внутренняя ошибка приложения. Обратитесь в службу поддержки.");
        }

        final String successMessage;
        if ("add".equals(niipsOperation.getAction())) {
            successMessage = "Данные об операции были успешно сохранены в сервисе отслеживания отправлений. Введите информацию о следующей операции.";
        } else if ("check".equals(niipsOperation.getAction())) {
            successMessage = "Ошибок нет.";
        } else {
            successMessage = "OK.";
        }

        KibanaLogger.logOperationRegistrationInfo(niipsOperation, username, true, "ok", event);
        stopWatch.stop();
        return new JsonResponse(true, successMessage);
    }

    private void validateIndexes(final NiipsOperation niipsOperation, final List<JsonResponse.ValidationError> validationErrors) {
        final boolean isIndexToValid = isValidationErrorsNotContainsField(validationErrors, "indexTo");
        final boolean isIndexFromValid = isValidationErrorsNotContainsField(validationErrors, "indexFrom");
        final boolean isIndexOperValid = isValidationErrorsNotContainsField(validationErrors, "indexOper");

        OpsInfo indexToOpsInfo = OpsInfo.empty();
        OpsInfo indexFromOpsInfo = OpsInfo.empty();
        OpsInfo indexOperOpsInfo = OpsInfo.empty();

        if (isIndexToValid) {
            indexToOpsInfo = resolveIndexToOpsInfo(niipsOperation);
        }

        if (isIndexFromValid) {
            indexFromOpsInfo = resolveOpsInfo(niipsOperation.getIndexFrom());
        }

        if (isIndexOperValid) {
            indexOperOpsInfo = resolveOpsInfo(niipsOperation.getIndexOper());
        }

        if (isNull(indexToOpsInfo) || isNull(indexFromOpsInfo) || isNull(indexOperOpsInfo)) {
            validationErrors.add(
                new JsonResponse.ValidationError("", CHECKING_INDEXES_IS_NOT_AVAILABLE)
            );
        } else {
            if (!addErrorIfIndexNotValid(indexToOpsInfo, "Индекс места назначения", validationErrors)) {
                niipsOperation.setRegion(indexToOpsInfo.getRegion());
                niipsOperation.setArea(indexToOpsInfo.getDistrict());
                niipsOperation.setPlace(indexToOpsInfo.getSettlement());
            }
            addErrorIfIndexNotValid(indexFromOpsInfo, "Индекс места подачи", validationErrors);
            addErrorIfIndexNotValid(indexOperOpsInfo, "Индекс места операции", validationErrors);
        }
    }

    private boolean isValidationErrorsNotContainsField(final List<JsonResponse.ValidationError> validationErrors, final String field) {
        return validationErrors.stream().noneMatch(validationError -> Objects.equals(validationError.getField(), field));
    }

    @Nullable
    private OpsInfo resolveIndexToOpsInfo(NiipsOperation niipsOperation) {
        final String region = niipsOperation.getRegion();
        final String district = niipsOperation.getArea();
        final String settlement = niipsOperation.getPlace();
        final String indexTo = niipsOperation.getIndexTo();

        if (
            EMS_FORM_TYPE.equals(niipsOperation.getFormType())
                && (isNull(region) || region.isEmpty())
                && (isNull(district) || district.isEmpty())
                && (isNull(settlement) || settlement.isEmpty())
                && !MRPO_INTERNATIONAL_INDEX_TO.equals(indexTo)
                && !EMS_INTERNATIONAL_INDEX_TO.equals(indexTo)
        ) {
            return resolveOpsInfo(indexTo);
        }
        return OpsInfo.empty();
    }

    /**
     * @param opsInfo          instance of {@link OpsInfo}
     * @param indexDescription index description
     * @param validationErrors validation errors list
     * @return true if error was added otherwise false
     */
    private boolean addErrorIfIndexNotValid(
        @NonNull final OpsInfo opsInfo,
        final String indexDescription,
        final List<JsonResponse.ValidationError> validationErrors
    ) {
        if (!OpsInfo.empty().equals(opsInfo) && isNull(opsInfo.getIndex())) {
            validationErrors.add(
                new JsonResponse.ValidationError("", format(INDEX_NOT_EXIST_MESSAGE, indexDescription))
            );
            return true;
        }
        return false;
    }

    /**
     * @param index index to resolve
     * @return instance of {@link OpsInfo}.
     */
    @Nullable
    private OpsInfo resolveOpsInfo(final String index) {
        return StringUtils.hasText(index) ? postOfficeClient.loadOpsInfo(index) : OpsInfo.empty();
    }

    /**
     * Validates barcode's check-digit.
     */
    private boolean isValidCheckDigit(final String barCode) {

        boolean result = true;

        if (barCode != null && barCode.length() == 13) {
            final int barCodeNum1 = Character.getNumericValue(barCode.charAt(2));
            final int barCodeNum2 = Character.getNumericValue(barCode.charAt(3));
            final int barCodeNum3 = Character.getNumericValue(barCode.charAt(4));
            final int barCodeNum4 = Character.getNumericValue(barCode.charAt(5));
            final int barCodeNum5 = Character.getNumericValue(barCode.charAt(6));
            final int barCodeNum6 = Character.getNumericValue(barCode.charAt(7));
            final int barCodeNum7 = Character.getNumericValue(barCode.charAt(8));
            final int barCodeNum8 = Character.getNumericValue(barCode.charAt(9));
            final int barCodeWeight = (barCodeNum1 * 8) + (barCodeNum2 * 6) + (barCodeNum3 * 4) + (barCodeNum4 * 2)
                + (barCodeNum5 * 3) + (barCodeNum6 * 5) + (barCodeNum7 * 9) + (barCodeNum8 * 7);
            int checkDigit = 11 - (barCodeWeight % 11);
            if (checkDigit == 10) {
                checkDigit = 0;
            }
            if (checkDigit == 11) {
                checkDigit = 5;
            }
            final int barCodeCheckDigit = Character.getNumericValue(barCode.charAt(10));
            if (barCodeCheckDigit != checkDigit) {
                result = false;
            }
        }

        return result;
    }

    /**
     * Runnable task of sending rtm-30 data for the given postal event.
     */
    private final class Rtm30SendingTask implements Runnable {

        private final PostalEvent.Event event;

        /**
         * Full constructor.
         * @param event postal event, which should be sent as RTM-30.
         */
        Rtm30SendingTask(final PostalEvent.Event event) {
            this.event = event;
        }

        @Override
        public void run() {
            try {
                sendRtm30ToNiips();
            } catch (Exception e) {
                log.error("Cannot send RTM-30 data to Niips.", e);
            }
        }

        /**
         * Generates RTM-30 message (xml string) from the given postal event and sends it to OASU RPO as byte array.
         */
        private void sendRtm30ToNiips() {
            final StopWatch stopWatch = stopWatchFactory.getStopWatch("sendRtm30ToNiips");
            log.info("Sending postal event to OASU RPO as RTM-30 message.");

            final String indexOper = event.hasIndexOper() ? event.getIndexOper() : RTM_30_DEFAULT_CREATION_INDEX;
            final String softwareVersion = event.hasSoftwareVersion() ? event.getSoftwareVersion() : RTM_30_DEFAULT_SOFTWARE_VERSION;

            try {
                final StopWatch stopWatch2 = stopWatchFactory.getStopWatch("sendRtm30ToNiips:buildRtm30Xml");
                final String rtm30Xml = new Rtm30ContainerBuilder(indexOper, softwareVersion, RTM_30_DLL_VERSION)
                    .append(event)
                    .toXmlString();
                stopWatch2.stop();

                if (LOG_WEB_INPUT_XML) {
                    log.info("Generated RTM-30 XML from Web Input event:\n{}", rtm30Xml);
                }

                oasuRpoRtm30Client.sendRtm30Data(rtm30Xml);
            } catch (Exception e) {
                log.error("Cannot send RTM-30 data to Niips.", e);
            }
            stopWatch.stop();
        }
    }
}
