package ru.russianpost.tracking.portal.admin.repository.holders;

import org.junit.Test;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareVersionCodeExtractor;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.IntStream;

import static java.lang.Character.MAX_CODE_POINT;
import static java.lang.Character.MIN_CODE_POINT;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class SoftwareVersionCodeExtractorTest {

    @Test
    public void processShouldReturnNullWhenVersionIsBlank() {
        asList(null, "", createWhitespacesSequence()).forEach(v -> {
            @SuppressWarnings("unchecked")
            final Collection<String> exclusions = mock(Collection.class);

            assertThat(new SoftwareVersionCodeExtractor(exclusions).process(v), is(nullValue()));

            verifyNoInteractions(exclusions);
        });
    }

    @Test
    public void processShouldReturnVersionWhenItIsContainedInExclusions() {
        final String version = "version";

        @SuppressWarnings("unchecked")
        final Collection<String> exclusions = mock(Collection.class);
        when(exclusions.contains(version)).thenReturn(true);

        assertThat(new SoftwareVersionCodeExtractor(exclusions).process(version), sameInstance(version));

        verify(exclusions).contains(version);
    }

    @Test
    public void testProductionValues() {
        final String exclusion = "P1.18.02.14_otto";
        asList(
            new Pair("DW2.7", "DW"),
            new Pair("DW2.8", "DW"),
            new Pair("DW2.9", "DW"),
            new Pair("DW3.10", "DW"),
            new Pair("DW3.11", "DW"),
            new Pair("DW3.12", "DW"),
            new Pair("DW3.2", "DW"),
            new Pair("DW3.3", "DW"),
            new Pair("DW3.4", "DW"),
            new Pair("DW3.5", "DW"),
            new Pair("DW3.6", "DW"),
            new Pair("DW3.7", "DW"),
            new Pair("DW3.8", "DW"),
            new Pair("DW3.9", "DW"),
            new Pair("ED1.0.0", "ED"),
            new Pair("EO1.0.0.0", "EO"),
            new Pair("EO5.6.0.7805", "EO"),
            new Pair("EO5.6.0.7825", "EO"),
            new Pair("EO6.0.0.7766", "EO"),
            new Pair("EPS1.0", "EPS"),
            new Pair("I2.5.11.4", "I"),
            new Pair("I2.5.11.7", "I"),
            new Pair("I2.5.12.6", "I"),
            new Pair("K4.5", "K"),
            new Pair("NW41.03", "NW"),
            new Pair("NW41.04", "NW"),
            new Pair("NW41.05", "NW"),
            new Pair("NW41.06", "NW"),
            new Pair("NW41.07", "NW"),
            new Pair("NW41.08", "NW"),
            new Pair("NW41.09", "NW"),
            new Pair("NW41.10", "NW"),
            new Pair("NW41.11", "NW"),
            new Pair("NW41.12", "NW"),
            new Pair("NW41.13", "NW"),
            new Pair("NW41.14", "NW"),
            new Pair("NW41.15", "NW"),
            new Pair("NW41.16", "NW"),
            new Pair("N41.01", "N"),
            new Pair("Pv4.5", "Pv"),
            new Pair("Pv4.6", "Pv"),
            new Pair("Pv4.8", "Pv"),
            new Pair("Pv4.9", "Pv"),
            new Pair("P1.11.XX", "P"),
            new Pair("P1.11.27", "P"),
            new Pair("P1.11.30", "P"),
            new Pair("P1.11.32", "P"),
            new Pair("P1.11.33", "P"),
            new Pair("P1.14", "P"),
            new Pair("P1.14.09", "P"),
            new Pair("P1.16.03", "P"),
            new Pair("P1.16.04", "P"),
            new Pair("P1.17.08", "P"),
            new Pair("P1.17.09", "P"),
            new Pair("P1.18.00", "P"),
            new Pair("P1.18.01.13", "P"),
            new Pair("P1.18.01.14", "P"),
            new Pair("P1.18.02.00", "P"),
            new Pair("P1.18.02.08", "P"),
            new Pair("P1.18.02.09", "P"),
            new Pair("P1.18.02.10", "P"),
            new Pair("P1.18.06.12", "P"),
            new Pair("P1.18.06.14", "P"),
            new Pair("P1.21.04.08", "P"),
            new Pair("R8.11", "R"),
            new Pair("R8.14.2", "R"),
            new Pair("R8.14.3", "R"),
            new Pair("R8.14.7", "R"),
            new Pair("R8.15.1", "R"),
            new Pair("R8.16.10", "R"),
            new Pair("R8.16.3", "R"),
            new Pair("R8.16.4", "R"),
            new Pair("R8.16.5", "R"),
            new Pair("R8.16.7", "R"),
            new Pair("R8.16.9", "R"),
            new Pair("R8.17.1", "R"),
            new Pair("R8.18.1", "R"),
            new Pair("R8.19.1", "R"),
            new Pair("R8.9", "R"),
            new Pair("SM01.04.017", "SM"),
            new Pair("SM01.04.018", "SM"),
            new Pair("SM01.05.001", "SM"),
            new Pair("SM01.05.002", "SM"),
            new Pair("SM01.05.003", "SM"),
            new Pair("SM01.05.004", "SM"),
            new Pair("SM01.05.007", "SM"),
            new Pair("SM01.05.009", "SM"),
            new Pair("SM01.05.010", "SM"),
            new Pair("THSASC.01", "THSASC"),
            new Pair("UAR_1.0", "UAR"),
            new Pair("VZ1.2.3.5", "VZ"),
            new Pair("WN01.00", "WN"),
            new Pair("WP_1.1.14.4", "WP"),
            new Pair("WP_1.15.7.8", "WP"),
            new Pair("WP_1.16.2.13", "WP"),
            new Pair("WP_1.16.2.24", "WP"),
            new Pair("WP_1.16.2.9", "WP"),
            new Pair("WP_1.16.3.31", "WP"),
            new Pair("WP_1.16.3.49", "WP"),
            new Pair("WP_1.16.3.50", "WP"),
            new Pair("WP_1.17.8.2", "WP"),
            new Pair("WP_1.17.8.3", "WP"),
            new Pair("WP_1.17.8.5", "WP"),
            new Pair("WP_1.18.0.36", "WP"),
            new Pair("WP_1.18.100.12", "WP"),
            new Pair("WP_1.18.102.14", "WP"),
            new Pair("WP_1.18.1.4", "WP"),
            new Pair("WP_1.20.2.3", "WP"),
            new Pair("WP_1.20.3.14", "WP"),
            new Pair("WP_1.20.5.11", "WP"),
            new Pair("WP_1.20.5.118", "WP"),
            new Pair("WP_1.20.6.15", "WP"),
            new Pair("WP_1.20.6.17", "WP"),
            new Pair("WP_1.20.6.17time11", "WP"),
            new Pair("WP_1.20.7.2", "WP"),
            new Pair("WP_1.20.7.6", "WP"),
            new Pair("WP_1.20.8.8", "WP"),
            new Pair("W01.00", "W"),
            new Pair(exclusion, exclusion)
        ).forEach(p -> assertThat(new SoftwareVersionCodeExtractor(Collections.singletonList(exclusion)).process(p._1), is(p._2)));
    }

    static String createWhitespacesSequence() {
        return IntStream.rangeClosed(MIN_CODE_POINT, MAX_CODE_POINT).parallel()
            .filter(Character::isWhitespace)
            .collect(
                StringBuilder::new,
                StringBuilder::appendCodePoint,
                StringBuilder::append
            ).toString();
    }

    private static class Pair {
        final String _1;
        final String _2;

        Pair(final String _1, final String _2) {
            this._1 = _1;
            this._2 = _2;
        }

    }

}
