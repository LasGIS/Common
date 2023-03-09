/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.validation.validators;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NoArgsConstructor;
import ru.russianpost.tracking.portal.admin.model.operation.FullnessValidationResult;
import ru.russianpost.tracking.portal.admin.model.operation.NiipsOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

/**
 * Utility class responsible for fullness validation of shipment operations by rtm-05 rules.
 * @author sslautin
 * @version 1.0 08.10.2015
 */
@NoArgsConstructor(access = PRIVATE)
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public final class Rtm05FullnessValidator {

    /* Meaningless strings. */
    private static final String NULL_STR = "null";
    private static final String UNKNOWN_VALUE_STR = "-1";

    private static final int UNKNOWN_VALUE = -1;
    private static final String EMS_FORM_TYPE = "ems";

    private static final int ERROR_MESSAGES_COLLECTION_INITIAL_CAPACITY = 24;
    private static final ImmutableMap<Field, Set<Integer>> ACCEPTABLE_OPER_TYPES = new ImmutableMap.Builder<Field, Set<Integer>>()
        .put(Field.MAIL_TYPE, ImmutableSet.of(1, 2, 3, 4, 8, 12))
        .put(Field.MAIL_CTG, ImmutableSet.of(1, 2, 3, 4, 8, 12))
        .put(Field.MASS, ImmutableSet.of(1, 2, 3, 4, 5, 6))
        .put(Field.RATE, ImmutableSet.of(1, 2, 6))
        .put(Field.MAIL_DIRECT, ImmutableSet.of(1, 2, 3, 4, 8))
        .put(Field.INDEX_TO, ImmutableSet.of(1, 3, 4))
        .put(Field.COUNTRY_FROM, ImmutableSet.of(2, 3, 8, 15, 16))
        .put(Field.MAIL_RANK, ImmutableSet.of(1, 2))
        .put(Field.RCPN, ImmutableSet.of(1, 2, 3, 4))
        .build();

    /**
     * Checks if shipment operation data object has all necessary fields.
     * @param niipsOperation shipment operation data object.
     * @return validation result object with error messages if any.
     */
    public static FullnessValidationResult validate(final NiipsOperation niipsOperation) {

        final List<String> errorMessages = new ArrayList<>(ERROR_MESSAGES_COLLECTION_INITIAL_CAPACITY);

        final int operType = safelyParseInt(niipsOperation.getOperTypeID());
        final int mailCtg = safelyParseInt(niipsOperation.getMailCtgID());
        final int mailDirect = safelyParseInt(niipsOperation.getCountryToID());
        final int countryFrom = safelyParseInt(niipsOperation.getCountryFromID());
        final int addressType = safelyParseInt(niipsOperation.getAddressType());
        final MailDirectionType mailDirectionType = getMailDirectionType(mailDirect, countryFrom);
        // Don`t worry. It`s always true. See ems.js#serializeFormToJson().
        final boolean isEmsForm = EMS_FORM_TYPE.equals(niipsOperation.getFormType());

        if (isMailTypeRequired(operType, isEmsForm) && isWrongValue(niipsOperation.getMailTypeID())) {
            errorMessages.add("Не указан вид отправления.");
        }
        if (isMailCtgRequired(operType) && isWrongValue(niipsOperation.getMailCtgID())) {
            errorMessages.add("Не указана категория отправления.");
        }
        if (isTimeOperRequired(isEmsForm) && isWrongValue(niipsOperation.getTimeOper())) {
            errorMessages.add("Не указано время операции.");
        }
        if (isMassRequired(operType) && isWrongValue(niipsOperation.getWeight())) {
            errorMessages.add("Не указан фактический вес.");
        }
        if (
            isValueRequired(operType, mailCtg) && (
                isWrongValue(niipsOperation.getVal())
                    || ("0".equals(niipsOperation.getVal()))
                    || ("0.00".equals(niipsOperation.getVal()))
            )
        ) {
            if ("0".equals(niipsOperation.getVal()) || "0.00".equals(niipsOperation.getVal())) {
                errorMessages.add(
                    "Не указана объявленная ценность." +
                        " Для данной категории отправления объявленная ценность должна быть больше 0."
                );
            } else {
                errorMessages.add("Не указана объявленная ценность.");
            }
        }
        if (
            isPaymentRequired(operType, mailCtg) && (
                isWrongValue(niipsOperation.getPayment())
                    || ("0".equals(niipsOperation.getPayment()))
                    || ("0.00".equals(niipsOperation.getPayment()))
            )
        ) {
            if ("0".equals(niipsOperation.getPayment()) || "0.00".equals(niipsOperation.getPayment())) {
                errorMessages.add(
                    "Не указан размер наложенного платежа." +
                        " Для данной категории отправления размер наложенного платежа должен быть больше 0."
                );
            } else {
                errorMessages.add("Не указан размер наложенного платежа.");
            }
        }
        if (isMassRateRequired(operType) && isWrongValue(niipsOperation.getShipRate())) {
            errorMessages.add("Не указан тарифный сбор.");
        }
        if (isAirRateRequired(operType, isEmsForm) && isWrongValue(niipsOperation.getAviaRate())) {
            errorMessages.add("Не указан сбор за авиа пересылку.");
        }
        if (isRateRequired(operType) && isWrongValue(niipsOperation.getAdditionalRate())) {
            errorMessages.add("Не указан дополнительный сбор.");
        }
        if (isAdValTaxRequired(operType) && isWrongValue(niipsOperation.getAdValTax())) {
            errorMessages.add("Не указан НДС.");
        }
        if (isMailDirectRequired(operType) && isWrongValue(niipsOperation.getCountryToID())) {
            errorMessages.add("Не указана страна назначения.");
        }
        if (isIndexToRequired(operType) && isWrongValue(niipsOperation.getIndexTo())) {
            errorMessages.add("Не указан индекс места назначения.");
        }
        if (isCountryFromRequired(operType) && isWrongValue(niipsOperation.getCountryFromID())) {
            errorMessages.add("Не указана страна подачи.");
        }
        if (isIndexFromRequired(operType, mailDirectionType) && isWrongValue(niipsOperation.getIndexFrom())) {
            errorMessages.add("Не указан индекс места подачи.");
        }
        if (isTransTypeRequired(operType, mailDirectionType, isEmsForm) && isWrongValue(niipsOperation.getTransTypeID())) {
            errorMessages.add("Не указан способ пересылки.");
        }
        if (isSendCtgRequired(operType) && isWrongValue(niipsOperation.getClientCtgID())) {
            errorMessages.add("Не указана категория клиента.");
        }
        if (isMailRankRequired(operType) && isWrongValue(niipsOperation.getMailRankID())) {
            errorMessages.add("Не указан разряд.");
        }
        if (isPostMarkRequired(operType) && isWrongValue(niipsOperation.getPostMarkID())) {
            errorMessages.add("Не указаны отметки.");
        }
        if (isPayTypeRequired(operType) && isWrongValue(niipsOperation.getPayTypeID())) {
            errorMessages.add("Не указана форма оплаты.");
        }
        if (isSndrRequired(operType, isEmsForm) && isWrongValue(niipsOperation.getSenderName())) {
            errorMessages.add("Не указаны Ф.И.О. / Название организации (Отправитель).");
        }
        if (isRcpnRequired(operType, isEmsForm) && isWrongValue(niipsOperation.getRecipientName())) {
            errorMessages.add("Не указаны Ф.И.О. / Название организации (Получатель).");
        }
        if (isHotelRequired(addressType, isEmsForm) && isWrongValue(niipsOperation.getHotel())) {
            errorMessages.add("Не указана гостиница.");
        }

        final List<NiipsOperation.SmsInput> smsInputs = niipsOperation.getSmsInputs();
        if (smsInputs != null && !smsInputs.isEmpty()) {
            for (final NiipsOperation.SmsInput smsInput : smsInputs) {
                final String telNumber = smsInput.getTelNumber();
                final boolean isAnySmsTypeChecked = smsInput.isSmsNotificationTypeAcceptance()
                    || smsInput.isSmsNotificationTypeOPSDelivery()
                    || smsInput.isSmsNotificationTypeCourierDelivery()
                    || smsInput.isSmsNotificationTypeDelivery()
                    || smsInput.isSmsNotificationTypeUnsuccessfulDelivery()
                    || smsInput.isSmsNotificationTypeOPSStorage5Days()
                    || smsInput.isSmsNotificationTypeOPSStorage25Days()
                    || smsInput.isSmsNotificationTypeReturn();

                if ((telNumber != null && !telNumber.isEmpty()) && !isAnySmsTypeChecked) {
                    errorMessages.add("Не указан тип уведомления для номера " + telNumber + ".");
                }
                if ((telNumber == null || telNumber.isEmpty()) && isAnySmsTypeChecked) {
                    errorMessages.add("Не указан номер телефона для SMS уведомления.");
                }
            }
        }

        final FullnessValidationResult result = new FullnessValidationResult();
        result.setErrorMessages(errorMessages);

        return result;
    }

    /**
     * Parses the given string by {@link Integer#parseInt(String)},
     * but if cannot parse, then returns {@link Rtm05FullnessValidator#UNKNOWN_VALUE} instead of throwing exception.
     * @param value string representation of an integer value.
     * @return integer value.
     */
    private static int safelyParseInt(final String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return UNKNOWN_VALUE;
        }
    }

    private static boolean isMailTypeRequired(final int operType, final boolean isEmsForm) {
        return !isEmsForm && ACCEPTABLE_OPER_TYPES.get(Field.MAIL_TYPE).contains(operType);
    }

    private static boolean isMailCtgRequired(final int operType) {
        return ACCEPTABLE_OPER_TYPES.get(Field.MAIL_CTG).contains(operType);
    }

    private static boolean isTimeOperRequired(boolean isEmsForm) {
        return isEmsForm;
    }

    private static boolean isMassRequired(final int operType) {
        return ACCEPTABLE_OPER_TYPES.get(Field.MASS).contains(operType);
    }

    private static boolean isValueRequired(final int operType, final int mailCtg) {
        return operType == 1 && (mailCtg == 2 || mailCtg == 4);
    }

    private static boolean isPaymentRequired(final int operType, final int mailCtg) {
        return operType == 1 && mailCtg == 4;
    }

    private static boolean isMassRateRequired(final int operType) {
        return operType == 1;
    }

    private static boolean isAirRateRequired(final int operType, final boolean isEmsForm) {
        return operType == 1 && !isEmsForm;
    }

    private static boolean isRateRequired(final int operType) {
        return ACCEPTABLE_OPER_TYPES.get(Field.RATE).contains(operType);
    }

    private static boolean isAdValTaxRequired(final int operType) {
        return operType == 1;
    }

    private static boolean isMailDirectRequired(final int operType) {
        return ACCEPTABLE_OPER_TYPES.get(Field.MAIL_DIRECT).contains(operType);
    }

    private static boolean isIndexToRequired(final int operType) {
        return ACCEPTABLE_OPER_TYPES.get(Field.INDEX_TO).contains(operType);
    }

    private static boolean isCountryFromRequired(final int operType) {
        return ACCEPTABLE_OPER_TYPES.get(Field.COUNTRY_FROM).contains(operType);
    }

    private static boolean isIndexFromRequired(final int operType, final MailDirectionType mailDirectionType) {
        return operType == 2 && (mailDirectionType == MailDirectionType.INTERNAL || mailDirectionType == MailDirectionType.OUTGOING);
    }

    private static boolean isTransTypeRequired(final int operType, final MailDirectionType mailDirectionType, final boolean isEmsForm) {
        return !isEmsForm && (
            operType == 1
                || (operType == 2 && (mailDirectionType == MailDirectionType.OUTGOING || mailDirectionType == MailDirectionType.INCOMING))
                || operType == 3
                || operType == 4
        );
    }

    private static boolean isSendCtgRequired(final int operType) {
        return operType == 1;
    }

    private static boolean isMailRankRequired(final int operType) {
        return ACCEPTABLE_OPER_TYPES.get(Field.MAIL_RANK).contains(operType);
    }

    private static boolean isPostMarkRequired(final int operType) {
        return operType == 1;
    }

    private static boolean isPayTypeRequired(final int operType) {
        return operType == 1;
    }

    private static boolean isSndrRequired(final int operType, final boolean isEmsForm) {
        return isEmsForm && (operType == 1);
    }

    private static boolean isRcpnRequired(final int operType, final boolean isEmsForm) {
        return isEmsForm && ACCEPTABLE_OPER_TYPES.get(Field.RCPN).contains(operType);
    }

    private static boolean isHotelRequired(final int addressType, final boolean isEmsForm) {
        return isEmsForm && (addressType == 5);
    }

    private static MailDirectionType getMailDirectionType(final int mailDirect, final int countryFrom) {
        if (mailDirect == 643 && countryFrom == 643) {
            return MailDirectionType.INTERNAL;
        } else if (mailDirect != 643 && countryFrom == 643) {
            return MailDirectionType.OUTGOING;
        } else if (mailDirect == 643) {
            return MailDirectionType.INCOMING;
        } else {
            return MailDirectionType.UNKNOWN;
        }
    }

    /**
     * Checks the string has wrong value or not.
     * @param value value from a form input/select field.
     * @return true, if the value shouldn`t be passed to protobuf, false otherwise.
     */
    private static boolean isWrongValue(final String value) {
        if (value != null) {
            final String trim = value.trim();
            return trim.isEmpty() || NULL_STR.equals(trim) || UNKNOWN_VALUE_STR.equals(trim);
        }
        return true;
    }

    /**
     * Type of mail direction.
     * OUTGOING and INCOMING corresponds to international mail.
     */
    private enum MailDirectionType {
        UNKNOWN, OUTGOING, INCOMING, INTERNAL
    }

    /**
     * Field types enum
     */
    private enum Field {
        MAIL_TYPE,
        MAIL_CTG,
        MASS,
        RATE,
        MAIL_DIRECT,
        INDEX_TO,
        COUNTRY_FROM,
        MAIL_RANK,
        RCPN
    }
}
