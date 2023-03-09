/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderFileUser;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.FileUserEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation.Regexp.DATE;
import static ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation.Regexp.EMAIL;
import static ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation.Regexp.INN;
import static ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation.Regexp.POSTAL_CODE;

/**
 * Validator impl.
 * @author MKitchenko
 */
@Service
public class ValidatorImpl implements Validator {

    private static final Pattern INN_REGEXP = Pattern.compile(INN);
    private static final Pattern DATE_REGEXP = Pattern.compile(DATE);
    private static final Pattern POSTAL_CODE_REGEXP = Pattern.compile(POSTAL_CODE);
    private static final Pattern EMAIL_REGEXP = Pattern.compile(EMAIL);

    private static final String INN_ERROR_REASON =
        String.format("Значение поля '%s' должно содержать десять/двенадцать цифр", FileUserEnum.INN.getField());
    private static final String DATE_ERROR_REASON =
        String.format("Значение поля '%s' должно соответствовать формату \"ДД.ММ.ГГГГ\"", FileUserEnum.DAT_DOG.getField());
    private static final String POSTAL_ERROR_REASON =
        String.format("Значение поля '%s' должно содержать шесть цифр", FileUserEnum.POSTAL_CODE.getField());
    private static final String EMAIL_ERROR_REASON =
        String.format("Значение поля '%s' содержит некорректный email", FileUserEnum.NOTIFICATION_EMAIL.getField());

    @Override
    public Map<Integer, List<ValidationError>> validateBarcodeProviderFileUsers(Map<Integer, BarcodeProviderFileUser> users) {
        return users.entrySet().stream()
            .map(entry -> new Pair<>(entry.getKey(), validateBarcodeProviderFileUser(entry.getValue())))
            .filter(pair -> !pair.getValue().isEmpty())
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private List<ValidationError> validateBarcodeProviderFileUser(BarcodeProviderFileUser user) {
        final List<ValidationError> errors = new ArrayList<>();
        if (Objects.nonNull(user)) {
            if (user.getCompanyName().isEmpty()) {
                final String field = FileUserEnum.COMPANY_NAME.getField();
                errors.add(new ValidationError(field, user.getCompanyName(), String.format("Поле '%s' не заполнено", field)));
            }

            validateFieldWithRegex(FileUserEnum.INN.getField(), user.getInn(), INN_REGEXP, INN_ERROR_REASON).ifPresent(errors::add);
            validateFieldWithRegex(FileUserEnum.DAT_DOG.getField(), user.getDatDog(), DATE_REGEXP, DATE_ERROR_REASON).ifPresent(errors::add);
            validateFieldWithRegex(FileUserEnum.POSTAL_CODE.getField(), user.getPostalCode(), POSTAL_CODE_REGEXP, POSTAL_ERROR_REASON)
                .ifPresent(errors::add);
            validateFieldWithRegex(FileUserEnum.NOTIFICATION_EMAIL.getField(), user.getNotificationEmail(), EMAIL_REGEXP, EMAIL_ERROR_REASON)
                .ifPresent(errors::add);
        }
        return errors;
    }

    private Optional<ValidationError> validateFieldWithRegex(String field, String value, Pattern pattern, String errorReason) {
        return isValueValid(value, pattern) ? Optional.empty() :
            Optional.of(new ValidationError(field, value, errorReason));
    }

    private boolean isValueValid(String value, Pattern pattern) {
        return Objects.nonNull(value) && pattern.matcher(value).matches();
    }

    /**
     * Pair
     * @param <K> key
     * @param <V> value
     */
    @Getter
    @RequiredArgsConstructor
    private static class Pair<K, V> {
        private final K key;
        private final V value;
    }
}
