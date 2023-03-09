package ru.russianpost.tracking.portal.admin.repository.holders;

import org.junit.Test;
import org.springframework.core.io.Resource;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareHolderFactoryBean;

import java.io.ByteArrayInputStream;
import java.util.function.Consumer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class SoftwareHolderFactoryBeanTest {

    @Test
    public void getObjectTypeShouldReturnSoftwareHolderClass() throws Exception {
        assertSame(factory().getObjectType(), SoftwareHolder.class);
    }

    @Test
    public void isSingletonShouldReturnTruey() throws Exception {
        assertThat(factory().isSingleton(), is(true));
    }

    @Test
    public void getObjectShouldReturnNullWhenResourceIsNotSet() throws Exception {
        assertThat(factory().getObject(), is(nullValue()));
    }

    @Test
    public void getObjectShouldReturnNullWhenResourceDoesNotExist() throws Exception {
        final Resource resource = mock(Resource.class);
        when(resource.exists()).thenReturn(false);

        assertThat(factory(f -> f.setKnownNames(resource)).getObject(), is(nullValue()));

        verify(resource).exists();
        verifyNoMoreInteractions(resource);
    }

    @Test
    public void getObjectShouldReturnNotNullInstance() throws Exception {
        final String configAsString = (
            "{"
                + "\"notFoundName\":\"notFoundName\","
                + "\"exclusions\":[\"code1\"],"
                + "\"knownNames\":{"
                    + "\"code\":\"name0\","
                    + "\"code1\":\"name1\""
                + "}"
            + "}"
        );

        final Resource resource = mock(Resource.class);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(
            new ByteArrayInputStream(configAsString.getBytes(UTF_8))
        );

        final SoftwareHolder holder = factory(f -> f.setKnownNames(resource)).getObject();
        assertThat(holder, is(notNullValue()));

        assertThat(holder.getSoftwareNameByVersion("fake"), is("notFoundName"));
        assertThat(holder.getSoftwareNameByVersion("code0"), is("name0"));
        assertThat(holder.getSoftwareNameByVersion("code_2"), is("name0"));
        assertThat(holder.getSoftwareNameByVersion("code.3"), is("name0"));
        assertThat(holder.getSoftwareNameByVersion("code1"), is("name1"));

        verify(resource).exists();
        verify(resource).getInputStream();
        verifyNoMoreInteractions(resource);
    }

    private static SoftwareHolderFactoryBean factory(final Consumer<SoftwareHolderFactoryBean> config) throws Exception {
        final SoftwareHolderFactoryBean factory = new SoftwareHolderFactoryBean();
        config.accept(factory);
        factory.afterPropertiesSet();
        return factory;
    }

    private static SoftwareHolderFactoryBean factory() throws Exception {
        return factory(f -> {});
    }

}
