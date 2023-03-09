package ru.russianpost.tracking.portal.admin.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotificationType;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.AllowedToAddOperationsHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.HideReasonDescriptionsHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.IndexCountryHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SmsNotificationTypesHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SmsTypeOrderHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareHolderFactoryBean;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.operations.OperationOrderHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.CountryHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.IndexMapHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.LocalizedOperationAttributeMapHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.LocalizedTypeMapHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.PredefinedAccessTypesHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.currencies.CurrenciesDecimalPlacesHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.timezones.TimeZoneHolder;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertSame;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RemoteDictionaryTest {

    private ApplicationContext context;
    private SoftwareHolder softwareHolder;
    private SmsNotificationTypesHolder smsNotificationTypesHolder;
    private DictionaryService dictionary;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Autowired
    public void setSoftwareHolder(SoftwareHolder softwareHolder) {
        this.softwareHolder = softwareHolder;
    }

    @Autowired
    public void setSmsNotificationTypesHolder(SmsNotificationTypesHolder smsNotificationTypesHolder) {
        this.smsNotificationTypesHolder = smsNotificationTypesHolder;
    }

    @Before
    public void setUp() throws Exception {
        dictionary = this.context.getAutowireCapableBeanFactory().createBean(DictionaryService.class);
    }

    @Test
    public void getSoftwareNameByVersionShouldDelegateCallToHolder() {
        assertThat(mockingDetails(this.softwareHolder).isMock(), is(true));

        final DictionaryService d = dictionary;
        assertThat(d, is(notNullValue()));

        final String softwareName = "softwareName";
        final String softwareVersion = "softwareVersion";

        when(this.softwareHolder.getSoftwareNameByVersion(softwareVersion)).thenReturn(softwareName);

        assertThat(d.getSoftwareNameByVersion(softwareVersion), sameInstance(softwareName));

        verify(this.softwareHolder).getSoftwareNameByVersion(softwareVersion);
        verifyNoMoreInteractions(this.softwareHolder);
    }

    @Test
    public void getSmsNotificationTypeShouldDelegateCallToHolder() {
        assertThat(mockingDetails(this.softwareHolder).isMock(), is(true));

        final DictionaryService d = dictionary;
        assertThat(d, is(notNullValue()));

        final SmsNotificationType expected = new SmsNotificationType(1, "О поступлении отправления в адресное ОПС");
        when(smsNotificationTypesHolder.getSmsNotificationType(1)).thenReturn(expected);

        final SmsNotificationType actual = dictionary.getSmsNotificationType(1);

        assertSame(expected, actual);

        verify(this.smsNotificationTypesHolder).getSmsNotificationType(1);
        verifyNoMoreInteractions(this.softwareHolder);
    }

    @Configuration
    static class RemoteDictionaryConfig {

        @Bean
        @Qualifier("operTypeMapHolder")
        LocalizedTypeMapHolder operTypeMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("mailTypeMapHolder")
        LocalizedTypeMapHolder mailTypeMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("mailRankMapHolder")
        LocalizedTypeMapHolder mailRankMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("sendCategoryMapHolder")
        LocalizedTypeMapHolder sendCategoryMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("mailCategoryMapHolder")
        LocalizedTypeMapHolder mailCategoryMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("customsStatusMapHolder")
        LocalizedTypeMapHolder customsStatusMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("postMarkMapHolder")
        LocalizedTypeMapHolder postMarkMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("transTypeMapHolder")
        LocalizedTypeMapHolder transTypeMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("postalOrderEventTypeMapHolder")
        LocalizedTypeMapHolder postalOrderEventTypeMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("operAttributeMapHolder")
        LocalizedOperationAttributeMapHolder operAttributeMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("indexMapHolder")
        IndexMapHolder indexMapHolder() {
            return null;
        }

        @Bean
        @Qualifier("countryHolder")
        CountryHolder countryHolder() {
            return null;
        }

        @Bean
        @Qualifier("timeZoneHolder")
        TimeZoneHolder timeZoneHolder() {
            return null;
        }

        @Bean
        @Qualifier("currenciesDecimalPlacesHolder")
        CurrenciesDecimalPlacesHolder currenciesDecimalPlacesHolder() {
            return null;
        }

        @Bean
        @Qualifier("indexCountryHolder")
        IndexCountryHolder indexCountryHolder() {
            return null;
        }

        @Bean
        @Qualifier("predefinedAccessTypesHolder")
        PredefinedAccessTypesHolder predefinedAccessTypesHolder() {
            return null;
        }

        @Bean
        @Qualifier("softwareHolder")
        SoftwareHolderFactoryBean softwareHolder() throws Exception {
            final SoftwareHolderFactoryBean result = mock(SoftwareHolderFactoryBean.class);
            when(result.getObjectType()).thenCallRealMethod();
            when(result.isSingleton()).thenCallRealMethod();
            when(result.getObject()).thenReturn(mock(SoftwareHolder.class));

            return result;
        }

        @Bean
        @Qualifier("smsNotificationTypesHolder")
        SmsNotificationTypesHolder smsNotificationTypesHolder() throws Exception {
            return mock(SmsNotificationTypesHolder.class);
        }

        @Bean
        @Qualifier("smsTypeOrderHolder")
        SmsTypeOrderHolder smsTypeOrderHolder() {
            return null;
        }

        @Bean
        @Qualifier("hideReasonDescriptionsHolder")
        HideReasonDescriptionsHolder hideReasonDescriptionsHolder() throws Exception {
            return mock(HideReasonDescriptionsHolder.class);
        }

        @Bean
        @Qualifier("operationOrderHolder")
        OperationOrderHolder operationOrderHolder() {
            return null;
        }

        @Bean
        @Qualifier("allowedToAddOperationsHolder")
        AllowedToAddOperationsHolder allowedToAddOperationsHolder() {
            return null;
        }

        @Bean
        @Qualifier("backendDictionary")
        BackendDictionary backendDictionary() {
            return null;
        }
    }
}
