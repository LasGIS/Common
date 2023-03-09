package ru.russianpost.tracking.portal.admin.repository.holders;

import org.junit.Test;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareHolder.VersionPreprocessor;

import java.util.Map;
import java.util.stream.IntStream;

import static java.lang.Character.MAX_CODE_POINT;
import static java.lang.Character.MIN_CODE_POINT;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class SoftwareHolderTest {

    @Test
    public void softwareHolderReturnsNotFoundValueWhenConstructorArgumentIsNull() {
        asList("testNotFound", null).forEach(nfv -> {
            assertThat(
                new SoftwareHolder(null, nfv).getSoftwareNameByVersion("fake"),
                sameInstance(nfv)
            );
        });
    }

    @Test
    public void softwareHolderReturnsNotFoundValueWhenVersionIsBlank() {
        final String nfv = "notFound";
        asList(null, "", createWhitespacesSequence()).forEach(v -> {
            @SuppressWarnings("unchecked")
            final Map<String, String> map = mock(Map.class);
            assertThat(
                new SoftwareHolder(map, nfv).getSoftwareNameByVersion(v),
                sameInstance(nfv)
            );
            verifyNoInteractions(map);
        });
    }

    @Test
    public void softwareHolderReturnsNotFoundValueWhenMapContainsBlankValue() {
        final String nfv = "notFound";
        final String version = "version";
        asList(null, "", createWhitespacesSequence()).forEach(name -> {
            @SuppressWarnings("unchecked")
            final Map<String, String> map = mock(Map.class);
            when(map.get(eq(version))).thenReturn(name);

            assertThat(
                new SoftwareHolder(map, nfv).getSoftwareNameByVersion(version),
                sameInstance(nfv)
            );

            verify(map).get(eq(version));
            verifyNoMoreInteractions(map);
        });
    }

    @Test
    public void softwareHolderReturnsValueFromMap() {
        final String fv = "found";
        final String version = "version";

        @SuppressWarnings("unchecked")
        final Map<String, String> map = mock(Map.class);
        when(map.get(eq(version))).thenReturn(fv);

        assertThat(
            new SoftwareHolder(map, null).getSoftwareNameByVersion(version),
            sameInstance(fv)
        );

        verify(map).get(eq(version));
        verifyNoMoreInteractions(map);
    }

    @Test
    public void softwareHolderWithCustomVersionPreprocessorReturnsValueFromMap() {
        final String fv = "found";
        final String version = "version";
        final String processedVersion = "processed";

        final VersionPreprocessor preprocessor = mock(VersionPreprocessor.class);
        when(preprocessor.process(version)).thenReturn(processedVersion);

        @SuppressWarnings("unchecked")
        final Map<String, String> map = mock(Map.class);
        when(map.get(processedVersion)).thenReturn(fv);

        assertThat(
            new SoftwareHolder(map, preprocessor, null).getSoftwareNameByVersion(version),
            sameInstance(fv)
        );

        verify(preprocessor).process(version);
        verify(map).get(processedVersion);
        verifyNoMoreInteractions(map, preprocessor);
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

}
