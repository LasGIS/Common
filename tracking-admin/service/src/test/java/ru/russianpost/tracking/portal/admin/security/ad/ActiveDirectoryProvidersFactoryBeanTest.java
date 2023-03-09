package ru.russianpost.tracking.portal.admin.security.ad;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.object.IsCompatibleType.typeCompatibleWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.function.Consumer;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;

public class ActiveDirectoryProvidersFactoryBeanTest {

    @Test
    public void getObjectShouldReturnsEmptyListWhenResourceIsNotSet() throws Exception {
        final Collection<ActiveDirectoryLdapAuthenticationProvider> providers = factory().getObject();
        assertThat(providers, is(notNullValue()));
        assertThat(providers, empty());
    }

    @Test
    public void getObjectShouldReturnsEmptyListWhenResourceDoesNotExist() throws Exception {
        final Resource resource = mock(Resource.class);
        when(resource.exists()).thenReturn(false);

        final Collection<ActiveDirectoryLdapAuthenticationProvider> providers = factory(f -> f.setAdConfigResource(resource)).getObject();
        assertThat(providers, is(notNullValue()));
        assertThat(providers, empty());

        verify(resource).exists();
        verifyNoMoreInteractions(resource);
    }

    @Test
    public void getObjectShouldReturnsListWithOneElement() throws Exception {
        final String configAsString = (
            "[{"
                + "\"url\" : \"ldap://10.233.1.34\","
                + "\"domain\" : \"rupost.local\","
                + "\"rootDn\" : \"dc=rupost,dc=local\","
                + "\"searchFilter\" : \"(&(objectClass=user)(|(userPrincipalName={0})(sAMAccountName={0})))\""
            + "}]"
        );

        final Resource resource = mock(Resource.class);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(
            new ByteArrayInputStream(configAsString.getBytes(UTF_8))
        );

        final Collection<ActiveDirectoryLdapAuthenticationProvider> providers = factory(f -> f.setAdConfigResource(resource)).getObject();
        assertThat(providers, is(notNullValue()));
        assertThat(providers, hasSize(1));

        assertThat(providers.iterator().next(), is(notNullValue()));

        verify(resource).exists();
        verify(resource).getInputStream();
        verifyNoMoreInteractions(resource);
    }

    private static ActiveDirectoryProvidersFactoryBean factory(final Consumer<ActiveDirectoryProvidersFactoryBean> config) throws Exception {
        final ActiveDirectoryProvidersFactoryBean factory = new ActiveDirectoryProvidersFactoryBean();
        factory.setUserRepo(mock(ServiceUserDao.class));
        config.accept(factory);
        factory.afterPropertiesSet();
        assertObjectTypeAndSingleton(factory);
        return factory;
    }

    private static ActiveDirectoryProvidersFactoryBean factory() throws Exception {
        return factory(f -> {});
    }

    private static void assertObjectTypeAndSingleton(final ActiveDirectoryProvidersFactoryBean factory) throws Exception {
        assertThat(factory.getObjectType(), typeCompatibleWith(Collection.class));
        assertThat(factory.isSingleton(), is(true));
    }

}
