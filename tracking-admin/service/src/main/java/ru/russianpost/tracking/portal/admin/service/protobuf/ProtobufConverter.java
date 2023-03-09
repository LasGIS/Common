/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.protobuf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.api.protobuf.messages.PostalEvent;
import ru.russianpost.tracking.api.protobuf.messages.RecordAlteration;
import ru.russianpost.tracking.commons.utils.PostmarksUtils;
import ru.russianpost.tracking.portal.admin.model.MoneyType;
import ru.russianpost.tracking.portal.admin.model.PersonType;
import ru.russianpost.tracking.portal.admin.model.WeightType;
import ru.russianpost.tracking.portal.admin.model.operation.NiipsOperation;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordCreation;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordDeletion;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordEdition;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordRestoration;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntConsumer;

import static ru.russianpost.tracking.api.protobuf.messages.RecordAlteration.SourceSystem.PORTAL_ADMIN;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.AD_VAL_TAX;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.INSR_RATE;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.MASS_RATE;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.PAYMENT;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.RATE;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.VALUE;

/**
 * Utility class responsible for transforming POJOs to and from protobuf objects.
 * @author sslautin
 * @version 1.0 25.09.2015
 */
@Service
public class ProtobufConverter {

    /* Meaningless strings. */
    private static final String NULL_STR = "null";
    private static final String UNKNOWN_VALUE_STR = "-1";

    /* Date-time formatting. */
    private static final String DATE_TIME_PATTERN = "dd.MM.yyyy'T'HH:mm:ss";

    private static final long DESTINATION_POINT_TYPE = 4L;
    private static final String HOTEL_PROPERTY_NAME = "Hotel";
    private static final Integer OPS_DELIVERY_SMS_TYPE = 1;
    private static final Integer DELIVERY_SMS_TYPE = 2;
    private static final Integer ACCEPTANCE_SMS_TYPE = 7;
    private static final Integer COURIER_DELIVERY_SMS_TYPE = 8;
    private static final Integer UNSUCCESSFUL_DELIVERY_SMS_TYPE = 9;
    private static final Integer OPS_STORAGE_5_DAYS_SMS_TYPE = 11;
    private static final Integer OPS_STORAGE_25_DAYS_SMS_TYPE = 12;
    private static final Integer RETURN_SMS_TYPE = 10;
    private static final String EMS_FORM_TYPE = "ems";
    private static final int EMS_MAIL_TYPE = 7;

    private static final int DEFAULT_TRANS_TYPE = 2;
    private static final long DEFAULT_SALE_TAX = 0;
    private static final int DEFAULT_COUNTRY_OPER = 643;

    private static final RecordAlteration.SourceSystem PORTAL_ADMIN_SOURCE_SYSTEM = PORTAL_ADMIN;

    private final Dictionary dictionary;
    private final String registrationSoftwareVersion;
    private final String portalAdminSoftwareVersion;
    private final String registrationProviderType;
    private final String portalAdminProviderType;

    /**
     * Constructor
     * @param dictionary                  instance of {@link Dictionary}
     * @param registrationSoftwareVersion registration software version
     * @param portalAdminSoftwareVersion  portal admin software version
     * @param registrationProviderType    registration provider type
     * @param portalAdminProviderType     portal admin provider type
     */
    public ProtobufConverter(
        Dictionary dictionary,
        @Value("${registration.software.version}") final String registrationSoftwareVersion,
        @Value("${portal.admin.software.version}") final String portalAdminSoftwareVersion,
        @Value("${registration.provider.type}") final String registrationProviderType,
        @Value("${portal.admin.provider.type}") final String portalAdminProviderType
    ) {
        this.dictionary = dictionary;
        this.registrationSoftwareVersion = registrationSoftwareVersion;
        this.portalAdminSoftwareVersion = portalAdminSoftwareVersion;
        this.registrationProviderType = registrationProviderType;
        this.portalAdminProviderType = portalAdminProviderType;
    }

    /**
     * Convert data into Protobuf message.
     * @param creation creation
     * @return protobuf postal event
     */
    public PostalEvent.Event convertToPostalEvent(final HistoryRecordCreation creation) {
        final ZoneOffset zoneOffset = dictionary.getZoneOffsetForIndex(creation.getIndexOper());
        final PostalEvent.Event.Builder protoBuilder = PostalEvent.Event.newBuilder()
            .setShipmentId(normalizeString(creation.getShipmentId().toUpperCase()))
            .setOperType(creation.getOperType())
            .setOperAttr(creation.getOperAttr())
            .setOperDate(buildOperDate(creation.getOperDate(), creation.getOperTime(), zoneOffset))
            .setIndexOper(creation.getIndexOper())
            .setZoneOffsetSeconds(zoneOffset.getTotalSeconds());
        if (creation.getMass() != null) {
            setWeight(creation.getMass(), protoBuilder);
        }
        protoBuilder.setSoftwareVersion(portalAdminSoftwareVersion);
        protoBuilder.setDataProviderType(portalAdminProviderType);
        protoBuilder.setTimestamp(Instant.now().toEpochMilli());
        protoBuilder.setCountryOper(DEFAULT_COUNTRY_OPER);
        return protoBuilder.build();
    }

    /**
     * Converts POJO class with shipment edition data into Protobuf message.
     * @param author  author
     * @param edition shipment edition data.
     * @return protobuf message.
     */
    public RecordAlteration.HistoryRecordEdition convertToRecordAlteration(final String author, final HistoryRecordEdition edition) {
        RecordAlteration.HistoryRecordEdition.Builder b = RecordAlteration.HistoryRecordEdition.newBuilder();

        // key fields
        b.setTargetShipmentId(edition.getId().getShipmentId());
        b.setTargetIndexOper(edition.getId().getIndexOper());
        b.setTargetOperDate(edition.getId().getOperDate());
        b.setTargetOperType(edition.getId().getOperType());
        b.setTargetOperAttr(edition.getId().getOperAttr());

        b.setSourceSystem(PORTAL_ADMIN_SOURCE_SYSTEM);

        b.setAuthor(author);
        b.setCreated(Instant.now().toEpochMilli());

        if (isWorthProcessing(edition.getComment())) {
            b.setComment(edition.getComment());
        }
        if (isWorthProcessing(edition.getIndexTo(), true)) {
            b.setIndexTo(edition.getIndexTo());
        }
        if (isWorthProcessing(edition.getIndexOper())) {
            b.setIndexOper(edition.getIndexOper());
        }
        if (isWorthProcessing(edition.getInitiator())) {
            b.setInitiator(edition.getInitiator());
        }
        if (null != edition.getMass()) {
            b.setMass(edition.getMass());
        }
        if (null != edition.getOperDate()) {
            b.setOperDate(edition.getOperDate());
        }
        if (null != edition.getPayment()) {
            if (null != edition.getPayment().getValue()) {
                b.setPayment(edition.getPayment().getValue().longValue());
            }
            if (null != edition.getPayment().getCurrency()) {
                b.setPaymentCurrency(edition.getPayment().getCurrency());
            }
        }
        if (null != edition.getValue()) {
            if (null != edition.getValue().getValue()) {
                b.setValue(edition.getValue().getValue().longValue());
            }
            if (null != edition.getValue().getCurrency()) {
                b.setValueCurrency(edition.getValue().getCurrency());
            }
        }
        if (null != edition.getSndr()) {
            b.setSndr(edition.getSndr());
        }
        if (null != edition.getRcpn()) {
            b.setRcpn(edition.getRcpn());
        }
        if (null != edition.getMailCategory()) {
            b.setMailCtg(edition.getMailCategory());
        }
        if (null != edition.getMailType()) {
            b.setMailType(edition.getMailType());
        }

        return b.build();
    }

    /**
     * Converts POJO class with shipment deletion data into Protobuf message.
     * @param author   author
     * @param deletion shipment deletion args.
     * @return protobuf message.
     */
    public RecordAlteration.HistoryRecordDeletion convertToRecordAlteration(final String author, final HistoryRecordDeletion deletion) {
        RecordAlteration.HistoryRecordDeletion.Builder b = RecordAlteration.HistoryRecordDeletion.newBuilder();

        // key fields
        b.setTargetShipmentId(deletion.getId().getShipmentId());
        b.setTargetIndexOper(deletion.getId().getIndexOper());
        b.setTargetOperDate(deletion.getId().getOperDate());
        b.setTargetOperType(deletion.getId().getOperType());
        b.setTargetOperAttr(deletion.getId().getOperAttr());

        b.setSourceSystem(PORTAL_ADMIN_SOURCE_SYSTEM);

        b.setAuthor(author);
        b.setCreated(Instant.now().toEpochMilli());

        if (isWorthProcessing(deletion.getComment())) {
            b.setComment(deletion.getComment());
        }
        if (isWorthProcessing(deletion.getInitiator())) {
            b.setInitiator(deletion.getInitiator());
        }

        return b.build();
    }

    /**
     * Converts POJO class with shipment restoration data into Protobuf message.
     * @param author      author
     * @param restoration shipment deletion args.
     * @return protobuf message.
     */
    public RecordAlteration.HistoryRecordRestoration convertToRecordAlteration(final String author, final HistoryRecordRestoration restoration) {
        RecordAlteration.HistoryRecordRestoration.Builder b = RecordAlteration.HistoryRecordRestoration.newBuilder();

        // key fields
        b.setTargetShipmentId(restoration.getId().getShipmentId());
        b.setTargetIndexOper(restoration.getId().getIndexOper());
        b.setTargetOperDate(restoration.getId().getOperDate());
        b.setTargetOperType(restoration.getId().getOperType());
        b.setTargetOperAttr(restoration.getId().getOperAttr());

        b.setSourceSystem(PORTAL_ADMIN_SOURCE_SYSTEM);

        b.setAuthor(author);
        b.setCreated(Instant.now().toEpochMilli());

        if (isWorthProcessing(restoration.getComment())) {
            b.setComment(restoration.getComment());
        }
        if (isWorthProcessing(restoration.getInitiator())) {
            b.setInitiator(restoration.getInitiator());
        }

        return b.build();
    }

    /**
     * Converts POJO class with shipment creation data into Protobuf message.
     * @param author   author
     * @param creation shipment deletion args.
     * @return protobuf message.
     */
    public RecordAlteration.HistoryRecordCreation convertToRecordAlteration(final String author, final HistoryRecordCreation creation) {
        RecordAlteration.HistoryRecordCreation.Builder b = RecordAlteration.HistoryRecordCreation.newBuilder();
        ZoneOffset zoneOffset = dictionary.getZoneOffsetForIndex(creation.getIndexOper());

        // key fields
        b.setTargetShipmentId(creation.getShipmentId());
        b.setTargetIndexOper(creation.getIndexOper());
        b.setTargetOperDate(buildOperDate(creation.getOperDate(), creation.getOperTime(), zoneOffset));
        b.setTargetOperType(creation.getOperType());
        b.setTargetOperAttr(creation.getOperAttr());

        b.setSourceSystem(PORTAL_ADMIN_SOURCE_SYSTEM);

        b.setAuthor(author);
        b.setCreated(Instant.now().toEpochMilli());

        if (isWorthProcessing(creation.getComment())) {
            b.setComment(creation.getComment());
        }
        if (isWorthProcessing(creation.getInitiator())) {
            b.setInitiator(creation.getInitiator());
        }
        return b.build();
    }

    /**
     * Checks if the string is not empty and has meaningful value.
     * @param fieldValue value from a form input/select field.
     * @return true, if the value should be passed to protobuf, false otherwise.
     */
    private static boolean isWorthProcessing(final String fieldValue) {
        return isWorthProcessing(fieldValue, false);
    }

    /**
     * Checks if the string is not empty and has meaningful value.
     * @param fieldValue value from a form input/select field.
     * @param allowEmpty flag to pass empty value to protobuf.
     * @return true, if the value should be passed to protobuf, false otherwise.
     */
    private static boolean isWorthProcessing(final String fieldValue, final boolean allowEmpty) {
        if (fieldValue != null) {
            final String trimmedValue = fieldValue.trim();
            return (allowEmpty || !trimmedValue.isEmpty()) && !NULL_STR.equals(trimmedValue) && !UNKNOWN_VALUE_STR.equals(trimmedValue);
        }
        return false;
    }

    /**
     * Checks if at least one of the given values is worth processing.
     * @param fieldValues list of values to check.
     * @return true, if at least on of the strings has meaningful value.
     */
    private static boolean isWorthProcessing(final String... fieldValues) {

        boolean result = false;

        for (final String fieldValue : fieldValues) {
            if (isWorthProcessing(fieldValue, false)) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Computes timestamp value from the date and time values.
     * If this values are not acceptable, then simply current timestamp.
     * @param date   date string in format 'dd.MM.yyyy'.
     * @param time   time string in format 'HH:mm:ss'.
     * @param zoneId the time-zone ID, not null.
     * @return timestamp for the given date/time string.
     */
    private long buildOperDate(final String date, final String time, final ZoneId zoneId) {

        final long result;

        if (isWorthProcessing(date)) {
            final String dateTime;
            if (isWorthProcessing(time)) {
                final String normalizedTime = normalizeTime(time);
                dateTime = date + "T" + normalizedTime;
            } else {
                dateTime = date + "T00:00:00";
            }
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).withZone(zoneId);
            result = Instant.from(dtf.parse(dateTime)).toEpochMilli();
        } else {
            result = Instant.now().toEpochMilli();
        }

        return result;
    }

    /**
     * Converts strings with 'rubles'.'kopecks' in just int value of kopecks.
     * @param rubKopecks string representation of some payment value (in rubles and possibly kopecks).
     * @return int value of a payment (in kopecks).
     */
    private int convertKopecks(final String rubKopecks) {
        if (rubKopecks == null || rubKopecks.isEmpty()) {
            return 0;
        }

        final double parsedDouble = Double.parseDouble(rubKopecks);
        return BigDecimal.valueOf(parsedDouble).multiply(BigDecimal.valueOf(100)).intValue();
    }

    /**
     * Converts POJO class with shipment operation data into Protobuf message.
     * @param operation shipment operation data.
     * @return protobuf message.
     */
    public PostalEvent.Event convertToPostalEvent(final NiipsOperation operation) {

        final PostalEvent.Event.Builder protoBuilder = PostalEvent.Event.newBuilder();

        if (isWorthProcessing(operation.getBarCode())) {
            protoBuilder.setShipmentId(normalizeString(operation.getBarCode().toUpperCase()));
        }
        if (isWorthProcessing(operation.getMailTypeID())) {
            protoBuilder.setMailType(Integer.parseInt(operation.getMailTypeID()));
        } else if (EMS_FORM_TYPE.equals(operation.getFormType())) {
            protoBuilder.setMailType(EMS_MAIL_TYPE);
        }
        if (isWorthProcessing(operation.getMailCtgID())) {
            protoBuilder.setMailCtg(Integer.parseInt(operation.getMailCtgID()));
        }
        if (isWorthProcessing(operation.getOperTypeID())) {
            protoBuilder.setOperType(Integer.parseInt(operation.getOperTypeID()));
        }
        if (isWorthProcessing(operation.getOperAttrID())) {
            protoBuilder.setOperAttr(Integer.parseInt(operation.getOperAttrID()));
        }
        final String indexOper = operation.getIndexOper();
        final ZoneOffset clientZoneOffset = dictionary.getZoneOffsetForIndex(indexOper);
        protoBuilder.setZoneOffsetSeconds(clientZoneOffset.getTotalSeconds());
        if (isWorthProcessing(indexOper)) {
            protoBuilder.setIndexOper(indexOper);
        }
        protoBuilder.setOperDate(buildOperDate(operation.getDateOper(), operation.getTimeOper(), clientZoneOffset));
        if (isWorthProcessing(operation.getCountryToID())) {
            protoBuilder.setMailDirect(Integer.parseInt(operation.getCountryToID()));
        }
        if (isWorthProcessing(operation.getIndexTo())) {
            protoBuilder.setIndexTo(normalizeString(operation.getIndexTo()));
        }
        if (isWorthProcessing(operation.getCountryFromID())) {
            protoBuilder.setCountryFrom(Integer.parseInt(operation.getCountryFromID()));
        }
        if (isWorthProcessing(operation.getIndexFrom())) {
            protoBuilder.setIndexFrom(normalizeString(operation.getIndexFrom()));
        }
        if (isWorthProcessing(operation.getTransTypeID())) {
            protoBuilder.setTransType(Integer.parseInt(operation.getTransTypeID()));
        } else {
            protoBuilder.setTransType(DEFAULT_TRANS_TYPE);
        }
        if (isWorthProcessing(operation.getClientCtgID())) {
            protoBuilder.setSendCtg(Integer.parseInt(operation.getClientCtgID()));
        }
        if (isWorthProcessing(operation.getMailRankID())) {
            protoBuilder.setMailRank(Integer.parseInt(operation.getMailRankID()));
        }
        if (isWorthProcessing(operation.getPayTypeID())) {
            protoBuilder.setPayType(Integer.parseInt(operation.getPayTypeID()));
        }
        if (isWorthProcessing(operation.getAccountNumber())) {
            protoBuilder.setAcnt(normalizeString(operation.getAccountNumber()));
        }
        if (isWorthProcessing(operation.getRecipientPhone())) {
            protoBuilder.setPhoneRecipient(normalizeString(operation.getRecipientPhone()));
        }
        final PostalEvent.Event.SmsInfo smsInfo = buildSmsInfo(operation);
        if (smsInfo != null) {
            protoBuilder.setSmsInfoObj(smsInfo);
        }

        if (isWorthProcessing(operation.getSenderName())) {
            final String sndrNameNormalized = normalizeString(operation.getSenderName());
            protoBuilder.setSndr(sndrNameNormalized);

            final PostalEvent.Event.Person person = PostalEvent.Event.Person.newBuilder()
                .setName(sndrNameNormalized)
                .setCategoryid(PersonType.SENDER.getType())
                .build();
            protoBuilder.setSndrPersonInfo(person);
        }

        final String rcpnNameNormalized = normalizeString(operation.getRecipientName());
        if (isWorthProcessing(rcpnNameNormalized)) {
            protoBuilder.setRcpn(rcpnNameNormalized);

            final PostalEvent.Event.Person person = PostalEvent.Event.Person.newBuilder()
                .setName(rcpnNameNormalized)
                .setCategoryid(PersonType.RECIPIENT.getType())
                .build();
            protoBuilder.setRcpnPersonInfo(person);
        }

        if (isWorthProcessing(operation.getPostMarkID())) {
            final List<Integer> postmarks = PostmarksUtils.breakBigPostmark(new BigInteger(operation.getPostMarkID()));
            if (!postmarks.isEmpty()) {
                protoBuilder.setPostMark(postmarks.get(0));
            }
            protoBuilder.addAllPostmarks(postmarks);
        }

        if (isWorthProcessing(operation.getWeight())) {
            setWeight(Integer.valueOf(operation.getWeight()), protoBuilder);
        }

        setMoney(operation, protoBuilder);
        setRcpnAddress(operation, protoBuilder);

        protoBuilder.setSaleTax(DEFAULT_SALE_TAX);
        protoBuilder.setSoftwareVersion(registrationSoftwareVersion);
        protoBuilder.setDataProviderType(registrationProviderType);
        protoBuilder.setTimestamp(Instant.now().toEpochMilli());
        protoBuilder.setCountryOper(DEFAULT_COUNTRY_OPER);

        return protoBuilder.build();
    }

    private void setRcpnAddress(NiipsOperation operation, PostalEvent.Event.Builder protoBuilder) {
        final String recipientAddressNormalized = normalizeString(operation.getRecipientAddress());
        PostalEvent.Event.Point point;

        if (isWorthProcessing(recipientAddressNormalized)) {
            final PostalEvent.Event.Address address = PostalEvent.Event.Address.newBuilder()
                .setStreet(recipientAddressNormalized)
                .build();
            point = PostalEvent.Event.Point.newBuilder()
                .setType(DESTINATION_POINT_TYPE)
                .setAddress(address)
                .build();
            protoBuilder.setRcpnAddress(point);
        } else {
            point = buildRcpnAddress(operation);
            if (point != null) {
                protoBuilder.setRcpnAddress(point);
            }
        }
    }

    private void setWeight(Integer weight, PostalEvent.Event.Builder protoBuilder) {
        protoBuilder.setMass(weight);

        final PostalEvent.Event.WeightInfo weightInfo = PostalEvent.Event.WeightInfo.newBuilder()
            .setValue(weight.doubleValue())
            .setMeasurement("g")
            .setType(WeightType.ACTUAL.getType())
            .build();

        protoBuilder.addAllWeightInfo(Collections.singletonList(weightInfo));
    }

    private void setMoney(NiipsOperation operation, PostalEvent.Event.Builder protoBuilder) {
        fillMoneyIfPresent(operation.getVal(), VALUE, protoBuilder, protoBuilder::setValue);
        fillMoneyIfPresent(operation.getPayment(), PAYMENT, protoBuilder, protoBuilder::setPayment);
        fillMoneyIfPresent(operation.getShipRate(), MASS_RATE, protoBuilder, protoBuilder::setMassRate);
        fillMoneyIfPresent(operation.getInsuranceRate(), INSR_RATE, protoBuilder, protoBuilder::setInsrRate);
        fillMoneyIfPresent(operation.getAdditionalRate(), RATE, protoBuilder, protoBuilder::setRate);
        fillMoneyIfPresent(operation.getAdValTax(), AD_VAL_TAX, protoBuilder, protoBuilder::setAdValTax);
    }

    private void fillMoneyIfPresent(String moneyValue, MoneyType type, PostalEvent.Event.Builder protoBuilder, IntConsumer legacyFieldSetter) {
        if (isWorthProcessing(moneyValue)) {
            final int moneyInKopecks = convertKopecks(moneyValue);
            legacyFieldSetter.accept(moneyInKopecks);
            protoBuilder.addMoneyInfo(
                PostalEvent.Event.MoneyInfo.newBuilder()
                    .setValue(moneyInKopecks)
                    .setMeasurement("RUB")
                    .setType(type.getType())
                    .build()
            );
        }
    }

    /**
     * Replaces ';', '|' and '=' with space.
     * @param value source string.
     * @return normalized string.
     */
    private static String normalizeString(final String value) {
        return value == null ? null : value.replaceAll("[;|=]", " ");
    }

    /**
     * Builds structured recipient address object from the operation data.
     * @param operation shipment operation data.
     * @return structured recipient address object, or null, if the given data doesn't contain needed info.
     */
    private PostalEvent.Event.Point buildRcpnAddress(final NiipsOperation operation) {

        PostalEvent.Event.Point result = null;

        if (isWorthProcessing(
            operation.getAddressNumber(),
            operation.getRegion(),
            operation.getArea(),
            operation.getPlace(),
            operation.getLocation(),
            operation.getStreet(),
            operation.getHouse(),
            operation.getLetter(),
            operation.getSlash(),
            operation.getCorpus(),
            operation.getBuilding(),
            operation.getHotel(),
            operation.getRoom()
        )) {
            final PostalEvent.Event.Point.Builder pointBuilder = PostalEvent.Event.Point.newBuilder();
            final PostalEvent.Event.Address.Builder addressBuilder = PostalEvent.Event.Address.newBuilder();

            if (isWorthProcessing(operation.getAddressType())) {
                addressBuilder.setType(Long.parseLong(operation.getAddressType()));
            }
            if (isWorthProcessing(operation.getAddressNumber())) {
                addressBuilder.setNumaddress(operation.getAddressNumber());
            }
            if (isWorthProcessing(operation.getRegion())) {
                addressBuilder.setRegion(operation.getRegion());
            }
            if (isWorthProcessing(operation.getArea())) {
                addressBuilder.setArea(operation.getArea());
            }
            if (isWorthProcessing(operation.getPlace())) {
                addressBuilder.setPlace(operation.getPlace());
            }
            if (isWorthProcessing(operation.getLocation())) {
                addressBuilder.setLocation(operation.getLocation());
            }
            if (isWorthProcessing(operation.getStreet())) {
                addressBuilder.setStreet(operation.getStreet());
            }
            if (isWorthProcessing(operation.getHotel())) {
                final PostalEvent.Event.Properties property = PostalEvent.Event.Properties.newBuilder()
                    .setName(HOTEL_PROPERTY_NAME)
                    .setValue(operation.getHotel())
                    .build();
                final List<PostalEvent.Event.Properties> properties = new ArrayList<>();
                properties.add(property);
                addressBuilder.addAllProperties(properties);
            }

            if (isWorthProcessing(
                operation.getHouse(),
                operation.getLetter(),
                operation.getSlash(),
                operation.getCorpus(),
                operation.getBuilding(),
                operation.getRoom()
            )) {
                final PostalEvent.Event.House.Builder houseBuilder = PostalEvent.Event.House.newBuilder();
                if (isWorthProcessing(operation.getHouse())) {
                    houseBuilder.setValue(operation.getHouse());
                }
                if (isWorthProcessing(operation.getLetter())) {
                    houseBuilder.setLetter(operation.getLetter());
                }
                if (isWorthProcessing(operation.getSlash())) {
                    houseBuilder.setSlash(operation.getSlash());
                }
                if (isWorthProcessing(operation.getCorpus())) {
                    houseBuilder.setCorpus(operation.getCorpus());
                }
                if (isWorthProcessing(operation.getBuilding())) {
                    houseBuilder.setBuilding(operation.getBuilding());
                }
                if (isWorthProcessing(operation.getRoom())) {
                    houseBuilder.setRoom(operation.getRoom());
                }
                addressBuilder.setHouse(houseBuilder.build());
            }

            pointBuilder.setType(DESTINATION_POINT_TYPE);
            if (isWorthProcessing(operation.getIndexTo())) {
                pointBuilder.setIndex(operation.getIndexTo());
            }
            pointBuilder.setAddress(addressBuilder.build());

            result = pointBuilder.build();
        }

        return result;
    }

    /**
     * Builds sms info object from the operation data.
     * @param operation shipment operation data.
     * @return sms info object, or null, if the given data doesn't contain needed info.
     */
    private PostalEvent.Event.SmsInfo buildSmsInfo(final NiipsOperation operation) {

        PostalEvent.Event.SmsInfo result = null;

        final List<NiipsOperation.SmsInput> smsInputs = operation.getSmsInputs();

        if (smsInputs != null && !smsInputs.isEmpty()) {
            final PostalEvent.Event.SmsInfo.Builder smsInfoBuilder = PostalEvent.Event.SmsInfo.newBuilder();
            final List<PostalEvent.Event.SmsInfo.SmsNotification> smsNotifications = new ArrayList<>();

            for (final NiipsOperation.SmsInput smsInput : smsInputs) {
                final String normalizedTel = normalizePhoneNumber(smsInput.getTelNumber());
                if (isWorthProcessing(normalizedTel)) {
                    final PostalEvent.Event.SmsInfo.SmsNotification.Builder notificationBuilder =
                        PostalEvent.Event.SmsInfo.SmsNotification.newBuilder();
                    notificationBuilder.setSmsTel(normalizedTel);
                    final List<Integer> smsTypes = new ArrayList<>();
                    if (smsInput.isSmsNotificationTypeOPSDelivery()) {
                        smsTypes.add(OPS_DELIVERY_SMS_TYPE);
                    }
                    if (smsInput.isSmsNotificationTypeDelivery()) {
                        smsTypes.add(DELIVERY_SMS_TYPE);
                    }
                    if (smsInput.isSmsNotificationTypeAcceptance()) {
                        smsTypes.add(ACCEPTANCE_SMS_TYPE);
                    }
                    if (smsInput.isSmsNotificationTypeCourierDelivery()) {
                        smsTypes.add(COURIER_DELIVERY_SMS_TYPE);
                    }
                    if (smsInput.isSmsNotificationTypeUnsuccessfulDelivery()) {
                        smsTypes.add(UNSUCCESSFUL_DELIVERY_SMS_TYPE);
                    }
                    if (smsInput.isSmsNotificationTypeOPSStorage5Days()) {
                        smsTypes.add(OPS_STORAGE_5_DAYS_SMS_TYPE);
                    }
                    if (smsInput.isSmsNotificationTypeOPSStorage25Days()) {
                        smsTypes.add(OPS_STORAGE_25_DAYS_SMS_TYPE);
                    }
                    if (smsInput.isSmsNotificationTypeReturn()) {
                        smsTypes.add(RETURN_SMS_TYPE);
                    }
                    notificationBuilder.addAllSmsType(smsTypes);
                    smsNotifications.add(notificationBuilder.build());
                }
            }

            if (!smsNotifications.isEmpty()) {
                smsInfoBuilder.addAllSmsNotification(smsNotifications);
                result = smsInfoBuilder.build();
            }
        }

        return result;
    }

    /**
     * Very simplistic function for phone number normalization.
     * @param phoneNumber source phone number
     * @return normalized number
     */
    private String normalizePhoneNumber(final String phoneNumber) {
        String result = "";

        if (null != phoneNumber) {
            // removing everything but numbers from phoneNumber.
            final String phone = phoneNumber.replaceAll("[^0-9]", "");
            if (!phone.isEmpty()) {
                final int length = phone.length();
                result = length > 10 ? phone.substring(length - 10) : phone;
            }
        }

        return result;
    }

    /**
     * Function to normalize the time to form 'HH:mm:ss'
     * @param time source time.
     * @return normalized time.
     */
    private static String normalizeTime(final String time) {
        final String[] parts = time.split(":");
        return parts.length == 2 ? time + ":00" : time;
    }
}
