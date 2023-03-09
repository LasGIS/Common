/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.util;

import ru.russianpost.tracking.api.protobuf.messages.PostalEvent;
import ru.russianpost.tracking.rtm30_stubs_st1440.Address;
import ru.russianpost.tracking.rtm30_stubs_st1440.Container;
import ru.russianpost.tracking.rtm30_stubs_st1440.House;
import ru.russianpost.tracking.rtm30_stubs_st1440.Item;
import ru.russianpost.tracking.rtm30_stubs_st1440.Money;
import ru.russianpost.tracking.rtm30_stubs_st1440.ObjectFactory;
import ru.russianpost.tracking.rtm30_stubs_st1440.Operation;
import ru.russianpost.tracking.rtm30_stubs_st1440.Person;
import ru.russianpost.tracking.rtm30_stubs_st1440.Point;
import ru.russianpost.tracking.rtm30_stubs_st1440.Properties;
import ru.russianpost.tracking.rtm30_stubs_st1440.SmsInfo;
import ru.russianpost.tracking.rtm30_stubs_st1440.SmsNotification;
import ru.russianpost.tracking.rtm30_stubs_st1440.SmsType;
import ru.russianpost.tracking.rtm30_stubs_st1440.Weight;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Class responsible for building consistent RTM-30 "Container" structure.
 * @author sslautin
 * @version 1.0 21.12.2015
 */
public class Rtm30ContainerBuilder {

    private static final ObjectFactory RTM30_OBJECT_FACTORY = new ObjectFactory();
    private static final JAXBContext JAXB_CONTEXT = initJaxbContext();
    private static final DatatypeFactory DATATYPE_FACTORY = initDatatypeFactory();
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final String XML_PROLOG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final DateTimeFormatter CONTAINER_ID_DTF =
        DateTimeFormatter.ofPattern("yyyyMMddHHmmssZ").withZone(ZoneId.systemDefault());
    private static final int RU_COUNTRY_CODE = 643;
    private static final String WEIGHT_MEASUREMENT = "kg";
    private static final String MONEY_CURRENCY = "RUB";

    /**
     * Internal representation of a container under construction.
     */
    private final Container container = RTM30_OBJECT_FACTORY.createContainer();

    /**
     * Within one container each element should has unique id.
     */
    private int nextElementId = 1;

    /**
     * Constructor. Initializes RTM-30 "Container" structure with the given parameters.
     * @param creationIndex   index of place of message (container) creation. Possibly equals to indexOper of the container's operations.
     * @param softwareVersion software id for use as container's version.
     * @param dllVersion      version of Niips DLL library.
     */
    public Rtm30ContainerBuilder(final String creationIndex, final String softwareVersion, final String dllVersion) {

        container.setId(generateContainerId(creationIndex));
        container.setVersion(softwareVersion);
        container.setVerdll(dllVersion);
    }

    /**
     * Converts the given postal events to rtm30 structures and appends them to internal container under construction.
     * @param events postal events.
     * @return this builder instance.
     */
    public Rtm30ContainerBuilder append(final List<PostalEvent.Event> events) {

        for (final PostalEvent.Event event : events) {
            this.append(event);
        }

        return this;
    }

    /**
     * Converts the given postal event to rtm30 structures and appends them to internal container under construction.
     * @param event postal event.
     * @return this builder instance.
     */
    public Rtm30ContainerBuilder append(final PostalEvent.Event event) {

        final List<Operation> operations = container.getOperation();
        operations.add(buildOperation(event));
        return this;
    }

    /**
     * Returns base64 encoded xml representation of the RTM-30 "Container" structure.
     * @return constructed rtm30 container as base64 encoded xml string.
     * @throws Exception if cannot convert container to XML string.
     */
    public byte[] toBase64ByteArray() throws Exception {

        return BASE64_ENCODER.encode(toXmlString().getBytes(DEFAULT_CHARSET));
    }

    /**
     * Returns xml representation of the RTM-30 "Container" structure.
     * @return constructed rtm30 container as xml string.
     * @throws Exception if an Jaxb Exception while marshalling occurred.
     */
    public String toXmlString() throws Exception {

        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            final Marshaller marshaller = JAXB_CONTEXT.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.marshal(RTM30_OBJECT_FACTORY.createContainer(container), os);
        } catch (JAXBException e) {
            throw new Exception("Cannot convert constructed RTM-30 container to XML string.", e);
        }

        final String xmlContent = new String(os.toByteArray(), DEFAULT_CHARSET);

        return xmlContent.isEmpty() ? xmlContent : XML_PROLOG + xmlContent;
    }

    /**
     * Initializes Jaxb context with {@link Container} class.<br>
     * <br>
     * The JAXBContext class is thread safe, but the Marshaller, Unmarshaller, and Validator classes are not thread safe.
     * It's recommended to initialize JAXBContext (and only it) only once.
     * @return initialized Jaxb context or null in case of an error.
     * @throws RuntimeException if cannot create new JAXBContext instance.
     */
    private static JAXBContext initJaxbContext() {

        final JAXBContext jaxbContext;

        try {
            jaxbContext = JAXBContext.newInstance(Container.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Cannot initialize JAXBContext.", e);
        }

        return jaxbContext;
    }

    /**
     * Initializes new instance of DatatypeFactory.
     * @return initialized DatatypeFactory or null in case of an error.
     * @throws RuntimeException if cannot create new DatatypeFactory instance.
     */
    private static DatatypeFactory initDatatypeFactory() {

        final DatatypeFactory datatypeFactory;

        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Cannot initialize DatatypeFactory.", e);
        }

        return datatypeFactory;
    }

    /**
     * Generates new string identifier for RTM-30 "Container" element.
     * Container id consists from "yyyyMMddHHmmssZ" format timestamp and index of place of message (container) creation.
     * Example: "20151012154344+0600620057".
     * @param containerCreationIndex index of place of message (container) creation.
     * @return new string identifier, based on current date-time in system default timezone, plus the given index.
     */
    private static String generateContainerId(final String containerCreationIndex) {

        return CONTAINER_ID_DTF.format(Instant.now()) + containerCreationIndex;
    }

    /**
     * Converts date-time in milliseconds to XMLGregorianCalendar instance with time zone +03.00 (Moscow).
     * @param timestamp date-time in milliseconds.
     * @return XMLGregorianCalendar instance.
     */
    private static XMLGregorianCalendar convertDate(final long timestamp) {

        return convertDate(timestamp, ZoneOffset.ofHours(3).getTotalSeconds());
    }

    /**
     * Converts date-time in milliseconds to XMLGregorianCalendar instance with the given time zone offset.
     * @param timestamp             date-time in milliseconds.
     * @param timezoneOffsetSeconds time zone offset in seconds.
     * @return XMLGregorianCalendar instance.
     */
    private static XMLGregorianCalendar convertDate(final long timestamp, final int timezoneOffsetSeconds) {

        final ZonedDateTime zonedDateTime =
            ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.ofTotalSeconds(timezoneOffsetSeconds));
        final GregorianCalendar gc = GregorianCalendar.from(zonedDateTime);

        final XMLGregorianCalendar xmlGregorianCalendar = DATATYPE_FACTORY.newXMLGregorianCalendar(gc);
        xmlGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);

        return xmlGregorianCalendar;
    }

    /**
     * Builds RTM-30 property structure with the given name and value.
     * @param name  property name.
     * @param value property value.
     * @return new RTM-30 property.
     */
    private static Properties buildProperty(final String name, final String value) {

        final Properties property = RTM30_OBJECT_FACTORY.createProperties();

        property.setName(name);
        property.setValue(value);

        return property;
    }

    /**
     * Computes DirectCtg from MailDirect and CountryFrom.
     * @param mailDirect  destination country code.
     * @param countryFrom origin country code.
     * @return 1 - Internal, 2 - International, null - unknown.
     */
    private static Integer computeDirectCtg(final Integer mailDirect, final Integer countryFrom) {

        if (mailDirect != null && mailDirect == RU_COUNTRY_CODE && countryFrom != null && countryFrom == RU_COUNTRY_CODE) {
            return 1;
        } else if ((mailDirect != null && mailDirect != RU_COUNTRY_CODE) || (countryFrom != null && countryFrom != RU_COUNTRY_CODE)) {
            return 2;
        } else {
            return null;
        }
    }

    /**
     * Converts grams value to kilograms value. 1 gram = 0.001 kilogram.
     * @param grams grams value.
     * @return kilograms value.
     */
    private static double convertToKg(final long grams) {

        final BigDecimal divided = BigDecimal.valueOf(grams).divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);
        return divided.doubleValue();
    }

    /**
     * Builds RTM-30 "Operation" element from the given postal event.
     * Note: "Operation" contains one "Item", which also will be constructed from the postal event.
     * @param event postal event protobuf.
     * @return constructed rtm30 operation.
     */
    private Operation buildOperation(final PostalEvent.Event event) {

        final Operation operation = RTM30_OBJECT_FACTORY.createOperation();
        operation.setId(nextElementId++);

        if (event.hasOperType()) {
            operation.setType(event.getOperType());
        }
        if (event.hasOperAttr()) {
            operation.setCategoryid(event.getOperAttr());
        }
        if (event.hasOperDate()) {
            if (event.hasZoneOffsetSeconds()) {
                operation.setDate(convertDate(event.getOperDate(), event.getZoneOffsetSeconds()));
            } else {
                operation.setDate(convertDate(event.getOperDate()));
            }
        }
        if (event.hasPayType()) {
            operation.getProperties().add(buildProperty("PayType", String.valueOf(event.getPayType())));
        }

        if (event.hasIndexOper()) {
            final Point operPoint = buildPoint(1, event.getIndexOper());
            if (event.hasCountryOper()) {
                operPoint.setCountryRUcode(event.getCountryOper());
            }
            operation.setPoint(operPoint);
        }

        final List<Item> items = operation.getItem();
        items.add(buildItem(event));

        return operation;
    }

    /**
     * Builds RTM-30 "Item" element from the given postal event.
     * @param event postal event protobuf.
     * @return constructed rtm30 item.
     */
    private Item buildItem(final PostalEvent.Event event) {

        final Item item = RTM30_OBJECT_FACTORY.createItem();
        item.setId(nextElementId++);

        if (event.hasShipmentId()) {
            item.setBarcode(event.getShipmentId());
        }
        if (event.hasInternum()) {
            item.setOwnnumber(event.getInternum());
        }
        if (event.hasMailCtg()) {
            item.setCategoryid(event.getMailCtg());
        }
        Integer mailDirect = null;
        if (event.hasMailDirect()) {
            mailDirect = event.getMailDirect();
        }
        Integer countryFrom = null;
        if (event.hasCountryFrom()) {
            countryFrom = event.getCountryFrom();
        }
        item.setDirectctg(computeDirectCtg(mailDirect, countryFrom));
        if (event.hasTransType()) {
            item.setTranstype(event.getTransType());
        }
        if (event.hasMailType()) {
            item.setType(event.getMailType());
        }
        if (event.hasBarcodeLinkedMail()) {
            item.setBarcodeLinkedMail(event.getBarcodeLinkedMail());
        }
        fillItemProperties(item, event);

        if (event.hasIndexNext()) {
            final Point nextPoint = buildPoint(2, event.getIndexNext());
            if (event.hasCountryNext()) {
                nextPoint.setCountryRUcode(event.getCountryNext());
            }
            item.getPoint().add(nextPoint);
        }
        if (event.hasIndexFrom()) {
            final Point creationPlacePoint = buildPoint(3, event.getIndexFrom());
            if (event.hasSndrAddress()) {
                final PostalEvent.Event.Point sndrPoint = event.getSndrAddress();
                if (sndrPoint.hasAddress()) {
                    creationPlacePoint.setAddress(buildAddress(sndrPoint.getAddress()));
                }
            }
            final Point originPoint = buildPoint(10, event.getIndexFrom());
            if (countryFrom != null) {
                originPoint.setCountryRUcode(countryFrom);
            }
            item.getPoint().add(creationPlacePoint);
            item.getPoint().add(originPoint);
        }
        if (event.hasIndexTo()) {
            final Point destinationPoint = buildPoint(4, event.getIndexTo());
            if (mailDirect != null) {
                destinationPoint.setCountryRUcode(mailDirect);
            }
            if (event.hasRcpnAddress()) {
                final PostalEvent.Event.Point rcpnPoint = event.getRcpnAddress();
                if (rcpnPoint.hasAddress()) {
                    destinationPoint.setAddress(buildAddress(rcpnPoint.getAddress()));
                }
            }
            item.getPoint().add(destinationPoint);
        }
        if (event.hasMass()) {
            final double valueInKg = convertToKg(event.getMass());
            final Weight declaredWeight = buildWeight(1, valueInKg);
            final Weight actualWeight = buildWeight(2, valueInKg);
            item.getWeight().add(declaredWeight);
            item.getWeight().add(actualWeight);
        }
        if (event.hasVolumeWeight()) {
            final Weight volumeWeight = buildWeight(3, convertToKg(event.getVolumeWeight()));
            item.getWeight().add(volumeWeight);
        }
        if (event.hasPayment()) {
            final Money paymentMoney = buildMoney(1, Math.toIntExact(event.getPayment()));
            item.getMoney().add(paymentMoney);
        }
        if (event.hasValue()) {
            final Money valueMoney = buildMoney(2, Math.toIntExact(event.getValue()));
            item.getMoney().add(valueMoney);
        }
        if (event.hasMassRate()) {
            final Money massRateMoney = buildMoney(3, Math.toIntExact(event.getMassRate()));
            item.getMoney().add(massRateMoney);
        }
        if (event.hasInsrRate()) {
            final Money insrRateMoney = buildMoney(4, Math.toIntExact(event.getInsrRate()));
            item.getMoney().add(insrRateMoney);
        }
        if (event.hasAirRate()) {
            final Money airRateMoney = buildMoney(5, Math.toIntExact(event.getAirRate()));
            item.getMoney().add(airRateMoney);
        }
        if (event.hasAdValTax()) {
            final Money adValTaxMoney = buildMoney(6, Math.toIntExact(event.getAdValTax()));
            item.getMoney().add(adValTaxMoney);
        }
        if (event.hasRate()) {
            final Money rateMoney = buildMoney(7, Math.toIntExact(event.getRate()));
            item.getMoney().add(rateMoney);
        }
        if (event.hasCustomDuty()) {
            final Money customDutyMoney = buildMoney(8, Math.toIntExact(event.getCustomDuty()));
            item.getMoney().add(customDutyMoney);
        }
        if (event.hasCompulsoryPayment()) {
            final Money compulsoryPaymentMoney = buildMoney(9, Math.toIntExact(event.getCompulsoryPayment()));
            item.getMoney().add(compulsoryPaymentMoney);
        }
        if (event.hasSndr()) {
            final Person sender = buildPerson(2, event.getSndr());
            if (event.hasSendCtg()) {
                sender.getProperties().add(buildProperty("SendCtg", String.valueOf(event.getSendCtg())));
            }
            if (event.hasAcnt()) {
                sender.getProperties().add(buildProperty("Acnt", event.getAcnt()));
            }
            if (event.hasInn()) {
                sender.getProperties().add(buildProperty("Inn", String.valueOf(event.getInn())));
            }
            if (event.hasKpp()) {
                sender.getProperties().add(buildProperty("Kpp", String.valueOf(event.getKpp())));
            }
            item.getPerson().add(sender);
        }
        if (event.hasRcpn()) {
            final Person recipient = buildPerson(3, event.getRcpn());
            item.getPerson().add(recipient);
        }
        if (event.hasSmsInfoObj()) {
            item.setSmsInfo(buildSmsInfo(event.getSmsInfoObj()));
        }

        return item;
    }

    /**
     * Fills the given rtm30 item with properties from the postal event.
     * @param item  rtm30 item to be filled.
     * @param event postal event protobuf.
     * @return filled rtm30 item.
     */
    private Item fillItemProperties(final Item item, final PostalEvent.Event event) {

        if (event.hasIdItem()) {
            item.getProperties().add(buildProperty("IDitem", String.valueOf(event.getIdItem())));
        }
        if (event.hasMailRank()) {
            item.getProperties().add(buildProperty("MailRank", String.valueOf(event.getMailRank())));
        }
        if (event.hasPostMark()) {
            item.getProperties().add(buildProperty("PostMark", String.valueOf(event.getPostMark())));
        }
        if (event.hasLength()) {
            item.getProperties().add(buildProperty("Length", String.valueOf(event.getLength())));
        }
        if (event.hasWidth()) {
            item.getProperties().add(buildProperty("Width", String.valueOf(event.getWidth())));
        }
        if (event.hasHeight()) {
            item.getProperties().add(buildProperty("Height", String.valueOf(event.getHeight())));
        }
        if (event.hasStoreNum()) {
            item.getProperties().add(buildProperty("StoreNum", String.valueOf(event.getStoreNum())));
        }
        if (event.hasRackNum()) {
            item.getProperties().add(buildProperty("RackNum", String.valueOf(event.getRackNum())));
        }
        if (event.hasSubRackNum()) {
            item.getProperties().add(buildProperty("SubRackNum", String.valueOf(event.getSubRackNum())));
        }

        return item;
    }

    /**
     * Builds RTM-30 "Point" element.
     * @param type  point type (see RTM-30).
     * @param index address index.
     * @return constructed rtm30 point.
     */
    private Point buildPoint(final int type, final String index) {

        final Point point = RTM30_OBJECT_FACTORY.createPoint();
        point.setId(nextElementId++);

        point.setType(type);
        point.setIndex(index);

        return point;
    }

    /**
     * Builds RTM-30 "Address" element.
     * @param eventAddress address from postal event protobuf.
     * @return constructed rtm30 address.
     */
    private Address buildAddress(final PostalEvent.Event.Address eventAddress) {

        final Address address = RTM30_OBJECT_FACTORY.createAddress();
        address.setId(nextElementId++);

        final List<PostalEvent.Event.Properties> eventAddressProperties = eventAddress.getPropertiesList();
        for (final PostalEvent.Event.Properties eventAddressProperty : eventAddressProperties) {
            address.getProperties().add(buildProperty(eventAddressProperty.getName(), eventAddressProperty.getValue()));
        }

        if (eventAddress.hasType()) {
            address.setType((int) eventAddress.getType());
        }
        if (eventAddress.hasRegion()) {
            address.setRegion(eventAddress.getRegion());
        }
        if (eventAddress.hasArea()) {
            address.setArea(eventAddress.getArea());
        }
        if (eventAddress.hasPlace()) {
            address.setPlace(eventAddress.getPlace());
        }
        if (eventAddress.hasStreet()) {
            address.setStreet(eventAddress.getStreet());
        }
        if (eventAddress.hasLocation()) {
            address.getProperties().add(buildProperty("Location", eventAddress.getLocation()));
        }
        if (eventAddress.hasNumaddress()) {
            address.getProperties().add(buildProperty("NumAddress", eventAddress.getNumaddress()));
        }

        if (eventAddress.hasHouse()) {
            final PostalEvent.Event.House eventHouse = eventAddress.getHouse();
            final House house = RTM30_OBJECT_FACTORY.createHouse();

            if (eventHouse.hasValue()) {
                house.setValue(eventHouse.getValue());
            }
            if (eventHouse.hasLetter()) {
                house.setLetter(eventHouse.getLetter());
            }
            if (eventHouse.hasSlash()) {
                house.setSlash(eventHouse.getSlash());
            }
            if (eventHouse.hasCorpus()) {
                house.setCorpus(eventHouse.getCorpus());
            }
            if (eventHouse.hasBuilding()) {
                house.setBuilding(eventHouse.getBuilding());
            }
            if (eventHouse.hasRoom()) {
                house.setRoom(eventHouse.getRoom());
            } else if (eventAddress.hasRoom()) {
                house.setRoom(eventAddress.getRoom());
            }

            address.setHouse(house);
        }

        return address;
    }

    /**
     * Builds RTM-30 "Weight" element.
     * @param type      weight type (see RTM-30).
     * @param valueInKg value in kilograms.
     * @return constructed rtm30 weight.
     */
    private Weight buildWeight(final int type, final double valueInKg) {

        final Weight weight = RTM30_OBJECT_FACTORY.createWeight();
        weight.setId(nextElementId++);

        weight.setType(type);
        weight.setMeasurement(WEIGHT_MEASUREMENT);
        weight.setValue(valueInKg);

        return weight;
    }

    /**
     * Builds RTM-30 "Money" element.
     * @param type  money type (see RTM-30).
     * @param value money value. Note: for currency "RUB" value must be in kopecks, according to RTM 0030.10-15 document.
     * @return constructed rtm30 money.
     */
    private Money buildMoney(final int type, final int value) {

        final Money money = RTM30_OBJECT_FACTORY.createMoney();
        money.setId(nextElementId++);

        money.setType(type);
        money.setCurrency(MONEY_CURRENCY);
        money.setValue(value);

        return money;
    }

    /**
     * Builds RTM-30 "Person" element.
     * @param type person type (see RTM-30).
     * @param name person name.
     * @return constructed rtm30 person.
     */
    private Person buildPerson(final int type, final String name) {

        final Person person = RTM30_OBJECT_FACTORY.createPerson();
        person.setId(nextElementId++);

        person.setType(type);
        person.setName(name);

        return person;
    }

    /**
     * Builds RTM-30 "SmsInfo" element.
     * @param eventSmsInfo sms info from postal event protobuf.
     * @return constructed rtm30 smsInfo.
     */
    private SmsInfo buildSmsInfo(final PostalEvent.Event.SmsInfo eventSmsInfo) {

        final SmsInfo smsInfo = RTM30_OBJECT_FACTORY.createSmsInfo();
        smsInfo.setId(nextElementId++);

        for (final PostalEvent.Event.SmsInfo.SmsNotification eventSmsNotification : eventSmsInfo.getSmsNotificationList()) {
            if (eventSmsNotification.hasSmsTel()) {
                final SmsNotification smsNotification = RTM30_OBJECT_FACTORY.createSmsNotification();
                smsNotification.setSmsTel(eventSmsNotification.getSmsTel());

                for (final Integer eventSmsType : eventSmsNotification.getSmsTypeList()) {
                    final SmsType smsType = RTM30_OBJECT_FACTORY.createSmsType();
                    smsType.setValue(eventSmsType);
                    smsNotification.getSmsType().add(smsType);
                }

                smsInfo.getSmsNotification().add(smsNotification);
            }
        }

        return smsInfo;
    }
}
