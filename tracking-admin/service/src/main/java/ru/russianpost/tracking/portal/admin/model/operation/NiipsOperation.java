/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import ru.russianpost.tracking.portal.admin.validation.constraints.NotBlankRange;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * POJO representation of a shipment operation from registration form (NIIPS variant).
 * @author sslautin
 * @version 1.0 28.09.2015
 */
@Getter
@Setter
@NoArgsConstructor
public class NiipsOperation {

    /*
     * Note for patterns below: empty strings are allowed. Add @NotBlank annotation, if necessary.
     */
    private static final String SHIPMENT_ID_REGEX = "^((([ABCDELNPRSUVZ][A-Z])|([G][AD]))[0-9]{9}[A-Z]{2})?$";
    private static final String INDEX_REGEX = "^([0-9]{6})?$";
    private static final String POSITIVE_INTEGER_REGEX = "^([0-9]+)?$";
    private static final String POSITIVE_FRACTIONAL_REGEX = "^(([0-9]+)([.][0-9]{2})?)?$";
    private static final String DATE_REGEX = "^((0[1-9]|1[0-9]|2[0-9]|3[01])\\.(0[1-9]|1[0-2])\\.[2-9]\\d{3})?$";
    private static final String TIME_REGEX = "^((([0,1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9]))?$";

    /* только цифры */
    private static final String ADDRESS_FIELD_REGEX_1 = "^([0-9]+)?$";
    /* только буквы (кириллица) */
    private static final String ADDRESS_FIELD_REGEX_4 = "^([\\p{InCyrillic}]+)?$";
    /* только буквы (кириллица) и цифры */
    private static final String ADDRESS_FIELD_REGEX_3 = "^([\\p{InCyrillic}0-9]+)?$";
    /* только буквы (кириллица), цифры, пробел, точка и дефис */
    private static final String ADDRESS_FIELD_REGEX_2 = "^([\\p{InCyrillic}0-9 .-]+)?$";
    /* только буквы (кириллица и латиница), цифры, пробел, точка и дефис */
    private static final String ADDRESS_FIELD_REGEX_5 = "^([\\p{InCyrillic}a-zA-Z0-9 .-]+)?$";

    private String timeZone;
    private String formType;
    private String localDate;
    private String action;
    private String mailTypeID;
    private String mailCtgID;
    @Range(min = 0, message = "Не указан вид операции.")
    private String operTypeID;
    @Range(min = 0, message = "Не указан атрибут операции.")
    private String operAttrID;
    @NotBlank(message = "Не указана дата операции.")
    @Pattern(
        regexp = DATE_REGEX,
        message = "Некорректный формат даты операции. Допускается только формат дд.мм.гггг."
    )
    private String dateOper;
    @Pattern(
        regexp = TIME_REGEX,
        message = "Некорректный формат времени операции. Допускается только формат чч:мм:сс."
    )
    private String timeOper;
    @NotBlank(message = "Не указан индекс места операции.")
    @Pattern(
        regexp = INDEX_REGEX,
        message = "Некорректный формат индекса места операции."
    )
    private String indexOper;
    @NotBlank(message = "Не указан штриховой идентификатор.")
    @Pattern(
        regexp = SHIPMENT_ID_REGEX,
        flags = {Pattern.Flag.CASE_INSENSITIVE},
        message = "Некорректный формат штрихового идентификатора."
    )
    private String barCode;
    @Pattern(
        regexp = POSITIVE_INTEGER_REGEX,
        message = "Некорректный формат фактического веса."
    )
    @NotBlankRange(min = 0, max = 40000, message = "Фактический вес должен быть в пределах от 0 до 40000 грамм.")
    private String weight;
    private String volumeWeight;
    private String length;
    private String width;
    private String height;
    @Pattern(
        regexp = POSITIVE_FRACTIONAL_REGEX,
        message = "Некорректный формат объявленной ценности."
    )
    @NotBlankRange(min = 0, max = 50000, message = "Объявленная ценность должна быть в пределах от 0 до 50000 рублей.")
    private String val;
    @Pattern(
        regexp = POSITIVE_FRACTIONAL_REGEX,
        message = "Некорректный формат размера наложенного платежа."
    )
    @NotBlankRange(min = 0, max = 50000, message = "Размер наложенного платежа должен быть в пределах от 0 до 50000 рублей.")
    private String payment;
    @Pattern(
        regexp = POSITIVE_FRACTIONAL_REGEX,
        message = "Некорректный формат тарифного сбора."
    )
    @NotBlankRange(min = 0, max = 999999, message = "Тарифный сбор должен быть в пределах от 0 до 999999 рублей.")
    private String shipRate;
    @Pattern(
        regexp = POSITIVE_FRACTIONAL_REGEX,
        message = "Некорректный формат сбора за авиа пересылку."
    )
    @NotBlankRange(min = 0, max = 999999, message = "Сбор за авиа пересылку должен быть в пределах от 0 до 999999 рублей.")
    private String aviaRate;
    private String insuranceRate;
    @Pattern(
        regexp = POSITIVE_FRACTIONAL_REGEX,
        message = "Некорректный формат дополнительного сбора."
    )
    @NotBlankRange(min = 0, max = 999999, message = "Дополнительный сбор должен быть в пределах от 0 до 999999 рублей.")
    private String additionalRate;
    @Pattern(
        regexp = POSITIVE_FRACTIONAL_REGEX,
        message = "Некорректный формат НДС."
    )
    @NotBlankRange(min = 0, max = 50000, message = "НДС должен быть в пределах от 0 до 50000 рублей.")
    private String adValTax;
    private String countryToID;
    @Pattern(
        regexp = INDEX_REGEX,
        message = "Некорректный формат индекса места назначения."
    )
    private String indexTo;
    private String countryFromID;
    @Pattern(
        regexp = INDEX_REGEX,
        message = "Некорректный формат индекса места подачи."
    )
    private String indexFrom;
    private String transTypeID;
    private String clientCtgID;
    private String mailRankID;
    private String postMarkID;
    private String payTypeID;
    @Length(max = 60, message = "Количество символов в поле \"Ф.И.О. / Название организации (Отправитель)\" должно быть не более 60.")
    private String senderName;
    @Length(max = 40, message = "Количество символов в поле \"Номер договора\" должно быть не более 40.")
    private String accountNumber;
    @Length(max = 147, message = "Количество символов в поле \"Ф.И.О. / Название организации (Получатель)\" должно быть не более 147.")
    private String recipientName;
    private String recipientAddress;
    private String addressType;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_1,
        message = "Некорректный формат поля \"№\". Допустимы только цифры."
    )
    @Length(max = 15, message = "Количество символов в поле \"№\" должно быть не более 15.")
    private String addressNumber;
    private String region;
    private String area;
    private String place;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_2,
        message = "Некорректный формат поля \"Поселение/микрорайон/квартал/территория\"." +
            " Допустимы только буквы (кириллица), цифры, пробел, точка и дефис."
    )
    @Length(max = 200, message = "Количество символов в поле \"Поселение/микрорайон/квартал/территория\" должно быть не более 200.")
    private String location;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_2,
        message = "Некорректный формат поля \"Улица\". Допустимы только буквы (кириллица), цифры, пробел, точка и дефис."
    )
    @Length(max = 200, message = "Количество символов в поле \"Улица\" должно быть не более 200.")
    private String street;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_3,
        message = "Некорректный формат поля \"Здание\". Допустимы только буквы (кириллица) и цифры."
    )
    @Length(max = 60, message = "Количество символов в поле \"Здание\" должно быть не более 60.")
    private String house;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_4,
        message = "Некорректный формат поля \"Лит.\". Допустимы только буквы (кириллица)."
    )
    @Length(max = 2, message = "Количество символов в поле \"Лит.\" должно быть не более 2.")
    private String letter;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_3,
        message = "Некорректный формат поля \"Дробь\". Допустимы только буквы (кириллица) и цифры."
    )
    @Length(max = 8, message = "Количество символов в поле \"Дробь\" должно быть не более 8.")
    private String slash;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_3,
        message = "Некорректный формат поля \"Корп.\". Допустимы только буквы (кириллица) и цифры."
    )
    @Length(max = 8, message = "Количество символов в поле \"Корп.\" должно быть не более 8.")
    private String corpus;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_3,
        message = "Некорректный формат поля \"Стр.\". Допустимы только буквы (кириллица) и цифры."
    )
    @Length(max = 8, message = "Количество символов в поле \"Стр.\" должно быть не более 8.")
    private String building;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_5,
        message = "Некорректный формат поля \"Гостиница\". Допустимы только буквы (кириллица и латиница), цифры, пробел, точка и дефис."
    )
    @Length(max = 50, message = "Количество символов в поле \"Гостиница\" должно быть не более 50.")
    private String hotel;
    @Pattern(
        regexp = ADDRESS_FIELD_REGEX_3,
        message = "Некорректный формат поля \"Помещение\". Допустимы только буквы (кириллица) и цифры."
    )
    @Length(max = 8, message = "Количество символов в поле \"Помещение\" должно быть не более 8.")
    private String room;
    private String recipientPhone;
    private String smsNotificationEnabled;
    private String smsNotificationBlank;
    private List<SmsInput> smsInputs;

    /**
     * Returns short string representation of this object, just for logging into kibana.
     * @return short string representation.
     */
    public String toShortString() {
        return "NiipsOperation{" +
            "formType='" + formType + '\'' +
            ", action='" + action + '\'' +
            ", barCode='" + barCode + '\'' +
            ", dateOper='" + dateOper + '\'' +
            ", timeOper='" + timeOper + '\'' +
            ", timeZone='" + timeZone + '\'' +
            ", operTypeID='" + operTypeID + '\'' +
            ", operAttrID='" + operAttrID + '\'' +
            ", indexOper='" + indexOper + '\'' +
            '}';
    }

    @Override
    public String toString() {
        return "NiipsOperation{" +
            "timeZone='" + timeZone + '\'' +
            ", formType='" + formType + '\'' +
            ", localDate='" + localDate + '\'' +
            ", action='" + action + '\'' +
            ", mailTypeID='" + mailTypeID + '\'' +
            ", mailCtgID='" + mailCtgID + '\'' +
            ", operTypeID='" + operTypeID + '\'' +
            ", operAttrID='" + operAttrID + '\'' +
            ", dateOper='" + dateOper + '\'' +
            ", timeOper='" + timeOper + '\'' +
            ", indexOper='" + indexOper + '\'' +
            ", barCode='" + barCode + '\'' +
            ", weight='" + weight + '\'' +
            ", volumeWeight='" + volumeWeight + '\'' +
            ", length='" + length + '\'' +
            ", width='" + width + '\'' +
            ", height='" + height + '\'' +
            ", val='" + val + '\'' +
            ", payment='" + payment + '\'' +
            ", shipRate='" + shipRate + '\'' +
            ", aviaRate='" + aviaRate + '\'' +
            ", insuranceRate='" + insuranceRate + '\'' +
            ", additionalRate='" + additionalRate + '\'' +
            ", adValTax='" + adValTax + '\'' +
            ", countryToID='" + countryToID + '\'' +
            ", indexTo='" + indexTo + '\'' +
            ", countryFromID='" + countryFromID + '\'' +
            ", indexFrom='" + indexFrom + '\'' +
            ", transTypeID='" + transTypeID + '\'' +
            ", clientCtgID='" + clientCtgID + '\'' +
            ", mailRankID='" + mailRankID + '\'' +
            ", postMarkID='" + postMarkID + '\'' +
            ", payTypeID='" + payTypeID + '\'' +
            ", senderName='" + senderName + '\'' +
            ", accountNumber='" + accountNumber + '\'' +
            ", recipientName='" + recipientName + '\'' +
            ", recipientAddress='" + recipientAddress + '\'' +
            ", addressType='" + addressType + '\'' +
            ", addressNumber='" + addressNumber + '\'' +
            ", region='" + region + '\'' +
            ", area='" + area + '\'' +
            ", place='" + place + '\'' +
            ", location='" + location + '\'' +
            ", street='" + street + '\'' +
            ", house='" + house + '\'' +
            ", letter='" + letter + '\'' +
            ", slash='" + slash + '\'' +
            ", corpus='" + corpus + '\'' +
            ", building='" + building + '\'' +
            ", hotel='" + hotel + '\'' +
            ", room='" + room + '\'' +
            ", recipientPhone='" + recipientPhone + '\'' +
            ", smsNotificationEnabled='" + smsNotificationEnabled + '\'' +
            ", smsNotificationBlank='" + smsNotificationBlank + '\'' +
            ", smsInputs=" + smsInputs +
            '}';
    }

    /**
     * Aggregator for sms related fields.
     */
    @Setter
    @Getter
    @NoArgsConstructor
    public static class SmsInput {
        private String telNumber;
        private boolean smsNotificationTypeAcceptance;
        private boolean smsNotificationTypeOPSDelivery;
        private boolean smsNotificationTypeCourierDelivery;
        private boolean smsNotificationTypeDelivery;
        private boolean smsNotificationTypeUnsuccessfulDelivery;
        private boolean smsNotificationTypeOPSStorage5Days;
        private boolean smsNotificationTypeOPSStorage25Days;
        private boolean smsNotificationTypeReturn;

        @Override
        public String toString() {
            return "SmsInput{"
                + "telNumber='"
                + telNumber
                + '\''
                + ", smsNotificationTypeAcceptance="
                + smsNotificationTypeAcceptance
                + ", smsNotificationTypeOPSDelivery="
                + smsNotificationTypeOPSDelivery
                + ", smsNotificationTypeDelivery="
                + smsNotificationTypeDelivery
                + ", smsNotificationTypeCourierDelivery="
                + smsNotificationTypeCourierDelivery
                + ", smsNotificationTypeUnsuccessfulDelivery="
                + smsNotificationTypeUnsuccessfulDelivery
                + ", smsNotificationTypeOPSStorage5Days="
                + smsNotificationTypeOPSStorage5Days
                + ", smsNotificationTypeOPSStorage25Days="
                + smsNotificationTypeOPSStorage25Days
                + ", smsNotificationTypeReturn="
                + smsNotificationTypeReturn
                + '}';
        }
    }
}
