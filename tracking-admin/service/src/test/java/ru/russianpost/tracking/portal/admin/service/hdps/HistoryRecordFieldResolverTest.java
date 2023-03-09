/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.hdps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.russianpost.tracking.commons.hdps.dto.MoneyInfo;
import ru.russianpost.tracking.commons.hdps.dto.Person2;
import ru.russianpost.tracking.commons.hdps.dto.WeightInfo;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.CustomsInfoV7;
import ru.russianpost.tracking.commons.hdps.dto.sms.SmsInfo2;
import ru.russianpost.tracking.commons.hdps.dto.sms.SmsNotification2;
import ru.russianpost.tracking.portal.admin.model.WeightType;
import ru.russianpost.tracking.portal.admin.model.admin.history.CustomsInfo;
import ru.russianpost.tracking.portal.admin.model.operation.Country;
import ru.russianpost.tracking.portal.admin.model.operation.Money;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsInformation;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotificationType;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.PAYMENT;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.VALUE;
import static ru.russianpost.tracking.portal.admin.utils.WeightResolver.LB_TO_GRAMS_RATIO;
import static ru.russianpost.tracking.portal.admin.utils.WeightResolver.OZ_TO_GRAMS_RATIO;

@RunWith(MockitoJUnitRunner.class)
public class HistoryRecordFieldResolverTest {

    private static final String CURRENCY = "RUB";
    private static final Double MASS_VALUE = 1000d;
    private static final Double DECLARED_VALUE = 30d;
    private static final Double ACTUAL_VALUE = 10d;
    private static final String SMS_TEL = "445522";
    private static final List<Integer> SMS_TYPES = asList(1, 2, 3);

    @Mock
    private Dictionary dictionary;

    @InjectMocks
    private HistoryRecordFieldResolver fieldResolver;

    @Test
    public void resolveMoneyByTypeTest() {
        final BigInteger value = BigInteger.valueOf(500);

        final List<MoneyInfo> moneyInfoList = new ArrayList<MoneyInfo>() {{
            add(new MoneyInfo(CURRENCY, PAYMENT.getType(), value.intValue()));
        }};

        final Money money = fieldResolver.resolveMoneyByType(moneyInfoList, PAYMENT);

        assertNotNull(money);
        assertEquals(CURRENCY, money.getCurrency());
        assertEquals(value, money.getValue());

        assertNull(fieldResolver.resolveMoneyByType(moneyInfoList, VALUE));
        assertNull(fieldResolver.resolveMoneyByType(emptyList(), null));
    }

    @Test
    public void resolveMoneyValueByTypeTest() {
        final int value = 500;

        final List<MoneyInfo> moneyInfoList = new ArrayList<MoneyInfo>() {{
            add(new MoneyInfo(CURRENCY, PAYMENT.getType(), value));
        }};

        final Money result = fieldResolver.resolveMoneyByType(moneyInfoList, PAYMENT);

        assertNotNull(result);
        assertEquals(value, result.getValue().intValue());

        assertNull(fieldResolver.resolveMoneyByType(moneyInfoList, VALUE));
        assertNull(fieldResolver.resolveMoneyByType(emptyList(), null));
    }

    @Test
    public void resolveMassWithoutActualTest() {
        final List<WeightInfo> weightInfoList = new ArrayList<WeightInfo>() {{
            add(new WeightInfo("g", WeightType.VOLUME.getType(), null));
            add(new WeightInfo("g", WeightType.DECLARED.getType(), DECLARED_VALUE));
        }};

        Long result = fieldResolver.resolveMass(weightInfoList);

        assertNull(result);
    }

    @Test
    public void resolveMassFromAtualAndDeclaredTest() {
        List<WeightInfo> weightInfoList = new ArrayList<WeightInfo>() {{
            add(new WeightInfo("g", WeightType.DECLARED.getType(), DECLARED_VALUE));
            add(new WeightInfo("g", WeightType.ACTUAL.getType(), ACTUAL_VALUE));
        }};

        Long result = fieldResolver.resolveMass(weightInfoList);

        assertNotNull(result);
        assertEquals(ACTUAL_VALUE.longValue(), result.longValue());

        weightInfoList = new ArrayList<WeightInfo>() {{
            add(new WeightInfo("g", WeightType.ACTUAL.getType(), null));
            add(new WeightInfo("g", WeightType.DECLARED.getType(), DECLARED_VALUE));
        }};

        result = fieldResolver.resolveMass(weightInfoList);

        assertNull(result);
    }

    @Test
    public void resolveActualMassWithOnlyActualWeightInListTest() {

        List<WeightInfo> weightInfoList = new ArrayList<WeightInfo>() {{
            add(new WeightInfo("g", WeightType.ACTUAL.getType(), ACTUAL_VALUE));
        }};

        Long result = fieldResolver.resolveMass(weightInfoList);

        assertNotNull(result);
        assertEquals(ACTUAL_VALUE.longValue(), result.longValue());

        weightInfoList = new ArrayList<WeightInfo>() {{
            add(new WeightInfo("g", WeightType.ACTUAL.getType(), null));
        }};

        result = fieldResolver.resolveMass(weightInfoList);

        assertNull(result);
    }

    @Test
    public void resolveMassFromEmptyList() {
        assertNull(fieldResolver.resolveMass(emptyList()));
    }

    @Test
    public void resolveMassInMgTest() {
        final Long result = resolveActualMass("mg");

        assertNotNull(result);
        assertEquals(MASS_VALUE.longValue() / 1000, result.longValue());
    }

    @Test
    public void resolveMassInKgTest() {
        final Long result = resolveActualMass("kg");

        assertNotNull(result);
        assertEquals(MASS_VALUE.longValue() * 1000, result.longValue());
    }

    @Test
    public void resolveMassInLbTest() {
        final Long result = resolveActualMass("lb");

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(MASS_VALUE).multiply(LB_TO_GRAMS_RATIO).longValue(), result.longValue());
    }

    @Test
    public void resolveMassInOzTest() {
        final Long result = resolveActualMass("oz");

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(MASS_VALUE).multiply(OZ_TO_GRAMS_RATIO).longValue(), result.longValue());
    }

    @Test
    public void resolvePostmarksTest() {
        final String postmarkDescription = "Без отметки";
        when(this.dictionary.getRtm02PostMarkName(any(BigInteger.class))).thenReturn(postmarkDescription);

        assertTrue(fieldResolver.resolvePostmarks(null).isEmpty());
        assertTrue(fieldResolver.resolvePostmarks(emptyList()).isEmpty());

        assertFalse(fieldResolver.resolvePostmarks(asList(0, 0)).isEmpty());
        assertEquals(postmarkDescription, fieldResolver.resolvePostmarks(asList(0, 0)).get(0));
    }

    @Test
    public void resolvePersonNameTest() {
        final Person2 person = new Person2(0L, 0L, "Ivan", "hid");

        String name = fieldResolver.resolvePersonName(person);
        assertNotNull(name);
        assertEquals(person.getName(), name);

        person.setName("");

        name = fieldResolver.resolvePersonName(person);
        assertNotNull(name);
        assertEquals("", name);

        person.setName(null);
        assertNull(fieldResolver.resolvePersonName(person));

        assertNull(fieldResolver.resolvePersonName(null));
    }

    @Test
    public void resolveAddressIsEmpty() {
        String ruPostExternalIndex = "644093";
        Integer ruPostExternalCountryCode = 5;

        // not all cases
        Object[][] data = new Object[][]{{null, null, null, null},
            {null, null, 0, ""},
            {"", "", 0, ""},
            {null, "", 0, ""},
            {"", null, 0, ""},
            {null, "644555", null, "upu"},
            {ruPostExternalIndex, null, ruPostExternalCountryCode, null}
        };

        when(this.dictionary.getCountry(ruPostExternalIndex)).thenReturn(ruPostExternalCountryCode);
        when(this.dictionary.getAddressDesc(ruPostExternalIndex)).thenReturn(null);

        String index;
        String indexDesc;
        Integer countryCode;
        String dataProviderType;

        for (Object[] items : data) {
            index = (String) items[0];
            indexDesc = (String) items[1];
            countryCode = (Integer) items[2];
            dataProviderType = (String) items[3];

            assertNull(fieldResolver.resolveAddress(index, indexDesc, countryCode, dataProviderType));
        }
    }

    @Test
    public void resolveAddressWhenIndexAndIndexDescNotDefined() {
        final String nameRu = "Кувейт";
        when(this.dictionary.getCountryById(414)).thenReturn(new Country(414, nameRu, null));

        assertNull(fieldResolver.resolveAddress(null, null, null, null));
        assertNull(fieldResolver.resolveAddress(null, null, 0, null));
        assertNull(fieldResolver.resolveAddress("", "", null, null));
        assertNull(fieldResolver.resolveAddress("", "", 0, null));
        assertEquals(nameRu, fieldResolver.resolveAddress("", "", 414, null));
        assertEquals(nameRu, fieldResolver.resolveAddress(null, null, 414, null));
    }

    @Test
    public void resolveSmsInformationForSmsInfo2Test() {
        SmsInfo2 smsInfo = null;
        assertNull(fieldResolver.resolveSmsInformation(smsInfo));

        smsInfo = new SmsInfo2(emptyList());
        SmsInformation smsInformation = fieldResolver.resolveSmsInformation(smsInfo);
        assertNotNull(smsInformation);
        assertTrue(smsInformation.getSmsNotifications().isEmpty());

        when(dictionary.getSmsNotificationType(SMS_TYPES.get(0))).thenReturn(new SmsNotificationType(SMS_TYPES.get(0), String.valueOf(SMS_TYPES.get(0))));
        when(dictionary.getSmsNotificationType(SMS_TYPES.get(1))).thenReturn(new SmsNotificationType(SMS_TYPES.get(1), String.valueOf(SMS_TYPES.get(1))));
        when(dictionary.getSmsNotificationType(SMS_TYPES.get(2))).thenReturn(new SmsNotificationType(SMS_TYPES.get(2), String.valueOf(SMS_TYPES.get(2))));

        smsInfo = new SmsInfo2(singletonList(new SmsNotification2(SMS_TEL, SMS_TYPES, 0)));
        smsInformation = fieldResolver.resolveSmsInformation(smsInfo);
        assertNotNull(smsInformation);

        final List<ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotification> smsNotifications =
            smsInformation.getSmsNotifications();
        assertFalse(smsNotifications.isEmpty());

        final ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotification notification = smsNotifications.get(0);
        assertNotNull(notification);
        assertEquals(SMS_TEL, notification.getSmsTel());
        assertNotNull(notification.getSmsTypes());
        assertThat(SMS_TYPES, containsInAnyOrder(notification.getSmsTypes().stream().map(SmsNotificationType::getId).toArray()));
    }

    @Test
    public void resolveCustomsInfo() {
        assertNull(fieldResolver.resolveCustomsInfo(null));

        CustomsInfoV7 customsInfoV7 = new CustomsInfoV7(ONE, 0, ZERO, 5L, 7);
        final CustomsInfo customsInfo = fieldResolver.resolveCustomsInfo(customsInfoV7);

        assertNotNull(customsInfo);
        assertEquals(customsInfoV7.getCustomsPayment(), customsInfo.getCustomsPayment());
        assertEquals(customsInfoV7.getCustomsStatus(), customsInfo.getCustomsStatus());
        assertEquals(customsInfoV7.getAmountOfUnpaid(), customsInfo.getAmountOfUnpaid());
    }

    private Long resolveActualMass(final String measurement) {
        return fieldResolver.resolveMassByType(getActualWeightInfoList(measurement), WeightType.ACTUAL);
    }

    private List<WeightInfo> getActualWeightInfoList(final String measurement) {
        return new ArrayList<WeightInfo>() {{
            add(new WeightInfo(measurement, WeightType.ACTUAL.getType(), MASS_VALUE));
        }};
    }
}
