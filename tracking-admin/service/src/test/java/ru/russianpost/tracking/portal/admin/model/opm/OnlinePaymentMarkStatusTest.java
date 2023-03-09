package ru.russianpost.tracking.portal.admin.model.opm;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static ru.russianpost.tracking.portal.admin.model.opm.OnlinePaymentMarkStatus.fromValue;

/**
 * OnlinePaymentMarkStatusTest
 * @author MKitchenko
 * @version 1.0 25.08.2020
 */
public class OnlinePaymentMarkStatusTest {

    @Test
    public void testFromValue() throws Exception {
        assertNull(fromValue(null));
        assertNull(fromValue("wrong value"));
        assertTrue(Arrays.stream(OnlinePaymentMarkStatus.values()).allMatch(i -> fromValue(i.name().toLowerCase()) == i));
        assertTrue(Arrays.stream(OnlinePaymentMarkStatus.values()).allMatch(i -> fromValue(i.name()) == i));
    }
}
