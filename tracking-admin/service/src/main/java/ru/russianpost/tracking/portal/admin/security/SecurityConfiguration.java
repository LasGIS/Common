/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.security.web.header.writers.DelegatingRequestMatcherHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import ru.russianpost.tracking.portal.admin.security.ad.ActiveDirectoryProvidersFactoryBean;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_ADMIN;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_AUTHORIZED_OPERATOR_SUPPORT;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_BARCODE_AUTOMATIZATION_ADMIN_USER_TO_UFPS;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_BARCODE_AUTOMATIZATION_USER;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_BARCODE_AUTOMATIZATION_VIEW_ONLY;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_BARCODE_PROVIDER;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_BARCODE_PROVIDER_SOAP;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_BARCODE_PROVIDER_VIEW_ONLY;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_CORR_USER;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_CUSTOMS_DECLARATION;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_ELASTIC_STATISTIC;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_EMS_USER;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_MRPO_USER;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_ONLINE_PAYMENT_MARK;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_OPS_USER_GENERATOR;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_PROFILE_MANAGE_USER;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_PROFILE_STAT_USER;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_PROFILE_VIEW_USER;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_TRACKING_BY_PHONE;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_TRACKING_USER;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_UPU_MONITORING;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 24, 2015)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    private static final String REACT_APP_ROOT_HTML = "/admin.html";
    private static final String REACT_APP_ROOT_HTML = "/";

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final RememberMeServices persistentTokenBasedRememberMeServices;
    private final ActiveDirectoryProvidersFactoryBean activeDirectoryAuthProvidersFactory;

    private final String applicationBaseUrl;

    /**
     * Constructor
     *
     * @param daoAuthenticationProvider              instance of {@link DaoAuthenticationProvider}
     * @param persistentTokenBasedRememberMeServices instance of {@link RememberMeServices}
     * @param activeDirectoryAuthProvidersFactory    instance of {@link ActiveDirectoryProvidersFactoryBean}
     * @param baseUrl                                application base url
     */
    public SecurityConfiguration(
        DaoAuthenticationProvider daoAuthenticationProvider,
        RememberMeServices persistentTokenBasedRememberMeServices,
        ActiveDirectoryProvidersFactoryBean activeDirectoryAuthProvidersFactory,
        @Value("${application.base.url}") String baseUrl
    ) {
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.persistentTokenBasedRememberMeServices = persistentTokenBasedRememberMeServices;
        this.activeDirectoryAuthProvidersFactory = activeDirectoryAuthProvidersFactory;
        this.applicationBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        requireNonNull(this.activeDirectoryAuthProvidersFactory.getObject()).forEach(auth::authenticationProvider);
        auth.authenticationProvider(this.daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .headers().cacheControl().disable().addHeaderWriter(new DelegatingRequestMatcherHeaderWriter(
                new NegatedRequestMatcher(
                    new OrRequestMatcher(
                        new AntPathRequestMatcher("/style/flags/**"),
                        new AntPathRequestMatcher("/style/fonts/**"),
                        new AntPathRequestMatcher("/style/img/**")
                    )
                ),
                new CacheControlHeadersWriter()
            ))
            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/config/").authenticated()
            .antMatchers("/login.html*").permitAll()
            .antMatchers("/logout").authenticated()
            .antMatchers("/service/src/main/webapp/static/**").permitAll()
            .antMatchers("/api/v1/profile/find").hasAnyAuthority(profileManagementGroup())
            .antMatchers("/api/v1/profile/stat/**").hasAnyAuthority(profileManagementGroup())
            .antMatchers("/api/v1/user/find").hasAnyAuthority(of(ROLE_PROFILE_MANAGE_USER, ROLE_PROFILE_STAT_USER))
            .antMatchers(GET, "/api/v1/profile/**").hasAnyAuthority(profileManagementGroup())
            .antMatchers(POST, "/api/v1/profile/**").hasAnyAuthority(of(ROLE_PROFILE_MANAGE_USER, ROLE_PROFILE_STAT_USER))
            .antMatchers(DELETE, "/api/v1/profile/**").hasAnyAuthority(of(ROLE_PROFILE_MANAGE_USER, ROLE_PROFILE_STAT_USER))
            .antMatchers("/api/v1/operation/**").hasAnyAuthority(of(ROLE_CORR_USER))
            .antMatchers("/api/v1/correction*").hasAnyAuthority(of(ROLE_CORR_USER))
            .antMatchers("/api/v1/barcode/*/histoty/verbose").hasAnyAuthority(of(ROLE_CORR_USER))
            .antMatchers("/api/v1/barcode/**").authenticated()
            .antMatchers("/api/v2/multiple-tracking/**").authenticated()
            .antMatchers("/api/v1/emsevt-manual/**").authenticated()
            .antMatchers("/api/v1/barcode-provider/view/**").hasAnyAuthority(barcodeProviderGroup())
            .antMatchers("/api/v1/barcode-provider/config/**").hasAuthority(ROLE_BARCODE_PROVIDER.name())
            .antMatchers("/api/v1/barcode-provider/user/**").hasAuthority(ROLE_BARCODE_PROVIDER_SOAP.name())
            .antMatchers(PUT, "/api/v1/barcode-automatization/user/**").hasAuthority(ROLE_BARCODE_AUTOMATIZATION_ADMIN_USER_TO_UFPS.name())
            .antMatchers(GET, "/api/v1/barcode-automatization/user/**")
            .hasAnyAuthority(of(ROLE_BARCODE_AUTOMATIZATION_ADMIN_USER_TO_UFPS, ROLE_BARCODE_AUTOMATIZATION_USER))
            .antMatchers("/api/v1/barcode-automatization/admin-portal-user").hasAuthority(ROLE_BARCODE_AUTOMATIZATION_ADMIN_USER_TO_UFPS.name())
            .antMatchers("/api/v1/barcode-automatization/ufps")
            .hasAnyAuthority(of(
                ROLE_BARCODE_AUTOMATIZATION_ADMIN_USER_TO_UFPS,
                ROLE_BARCODE_AUTOMATIZATION_USER,
                ROLE_BARCODE_AUTOMATIZATION_VIEW_ONLY
            ))
            .antMatchers("/api/v1/barcode-automatization/allocate").hasAuthority(ROLE_BARCODE_AUTOMATIZATION_USER.name())
            .antMatchers("/api/v1/barcode-automatization/suitable-range").hasAuthority(ROLE_BARCODE_AUTOMATIZATION_USER.name())
            .antMatchers("/api/v1/barcode-automatization/allocation/search")
            .hasAnyAuthority(of(ROLE_BARCODE_AUTOMATIZATION_USER, ROLE_BARCODE_AUTOMATIZATION_VIEW_ONLY))
            .antMatchers(GET, "/api/v1/barcode-provider/booking/**").hasAnyAuthority(barcodeProviderGroup())
            .antMatchers(DELETE, "/api/v1/barcode-provider/booking/**").hasAnyAuthority(barcodeProviderAdminGroup())
            .antMatchers(GET, "/api/v1/barcode-provider/booking/**").hasAnyAuthority(barcodeProviderGroup())
            .antMatchers(DELETE, "/api/v1/barcode-provider/booking/**").hasAnyAuthority(barcodeProviderAdminGroup())
            .antMatchers(GET, "/api/v1/barcode-provider/international-containers/**").hasAnyAuthority(barcodeProviderGroup())
            .antMatchers("/api/v1/admin/**").hasAuthority(ROLE_ADMIN.name())
            .antMatchers("/dict/ops/**").hasAnyAuthority(operationRegistrationGroup())
            .antMatchers("/api/data/**").hasAnyAuthority(operationRegistrationGroup())
            .antMatchers("/js/**").permitAll()
            .antMatchers("/style/**").permitAll()
            .antMatchers("/monitoring/**").permitAll()
            .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs").permitAll()
            .antMatchers("/api/v1/online-payment-mark/**").hasAuthority(ROLE_ONLINE_PAYMENT_MARK.name())
            .antMatchers("/api/v1/authorized-operator-support/**").hasAuthority(ROLE_AUTHORIZED_OPERATOR_SUPPORT.name())
            .antMatchers("/api/v1/elastic/stat/**").hasAuthority(ROLE_ELASTIC_STATISTIC.name())
            .antMatchers("/api/v1/customs-declaration/**").hasAuthority(ROLE_CUSTOMS_DECLARATION.name())
            .antMatchers("/api/v1/history-by-phone/**").hasAuthority(ROLE_TRACKING_BY_PHONE.name())
            .antMatchers("/api/v1/upu-monitoring/**", "/api/v1/international-monitoring/**").hasAuthority(ROLE_UPU_MONITORING.name())
            .antMatchers("/api/v1/online-payment-mark/user/ops/generate").hasAuthority(ROLE_OPS_USER_GENERATOR.name())
            .antMatchers("/**").authenticated()
            .and()
            .addFilterAfter(rootRedirectionFilter(), FilterSecurityInterceptor.class)
            .csrf().disable()
            .rememberMe()
            .rememberMeServices(this.persistentTokenBasedRememberMeServices)
            .and()
            .formLogin()
            .loginProcessingUrl("/login")
            .loginPage(applicationBaseUrl + "/login.html")
            .failureUrl(applicationBaseUrl + "/login.html?error=invalid_username_or_password")
            .defaultSuccessUrl(applicationBaseUrl + "/", true)
            .successHandler(afterLoginHandler())
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl(applicationBaseUrl + "/")
            .and()
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler());
    }

    /**
     * RootRedirectionFilter
     *
     * @return RootRedirectionFilter
     */
    private RootRedirectionFilter rootRedirectionFilter() {
        Map<Role, String> roleToStartPageUrlMap = new EnumMap<>(Role.class);
        roleToStartPageUrlMap.put(ROLE_TRACKING_USER, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_EMS_USER, "/ems.html");
        roleToStartPageUrlMap.put(ROLE_MRPO_USER, "/international.html");
        roleToStartPageUrlMap.put(ROLE_CORR_USER, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_PROFILE_MANAGE_USER, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_PROFILE_VIEW_USER, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_PROFILE_STAT_USER, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_BARCODE_PROVIDER, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_BARCODE_PROVIDER_SOAP, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_ADMIN, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_ONLINE_PAYMENT_MARK, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_BARCODE_PROVIDER_VIEW_ONLY, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_BARCODE_AUTOMATIZATION_USER, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_BARCODE_AUTOMATIZATION_VIEW_ONLY, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_BARCODE_AUTOMATIZATION_ADMIN_USER_TO_UFPS, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_AUTHORIZED_OPERATOR_SUPPORT, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_ELASTIC_STATISTIC, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_CUSTOMS_DECLARATION, REACT_APP_ROOT_HTML);
        roleToStartPageUrlMap.put(ROLE_TRACKING_BY_PHONE, REACT_APP_ROOT_HTML);
        return new RootRedirectionFilter(applicationBaseUrl, "/", "/login.html", "/login.html?error=no_roles", roleToStartPageUrlMap);
    }

    /**
     * AfterLoginHandler
     *
     * @return AfterLoginHandler
     */
    private AfterLoginHandler afterLoginHandler() {
        Map<Role, String> roleToPageNameMap = new EnumMap<>(Role.class);
        roleToPageNameMap.put(ROLE_TRACKING_USER, "tracking");
        roleToPageNameMap.put(ROLE_EMS_USER, "ems");
        roleToPageNameMap.put(ROLE_MRPO_USER, "international");
        roleToPageNameMap.put(ROLE_CORR_USER, "corrections");
        roleToPageNameMap.put(ROLE_PROFILE_MANAGE_USER, "profile-manage");
        roleToPageNameMap.put(ROLE_PROFILE_VIEW_USER, "profile-view");
        roleToPageNameMap.put(ROLE_PROFILE_STAT_USER, "profile-stat");
        return new AfterLoginHandler(applicationBaseUrl + "/", "AVAILABLE_PAGES", roleToPageNameMap);
    }

    /**
     * RedirectingAccessDeniedHandler
     *
     * @return RedirectingAccessDeniedHandler
     */
    private RedirectingAccessDeniedHandler accessDeniedHandler() {
        return new RedirectingAccessDeniedHandler("/api/", "/");
    }

    private static String[] of(Role... roles) {
        return Stream.of(roles).map(Role::name).toArray(String[]::new);
    }

    private static String[] profileManagementGroup() {
        return of(ROLE_PROFILE_MANAGE_USER, ROLE_PROFILE_STAT_USER, ROLE_PROFILE_VIEW_USER);
    }

    private String[] barcodeProviderGroup() {
        final List<Role> roles = Arrays.stream(barcodeProviderAdminGroup()).map(Role::by).collect(Collectors.toList());
        roles.add(ROLE_BARCODE_PROVIDER_VIEW_ONLY);

        return of(roles.toArray(new Role[0]));
    }

    private String[] barcodeProviderAdminGroup() {
        return of(ROLE_BARCODE_PROVIDER, ROLE_BARCODE_PROVIDER_SOAP);
    }

    private static String[] operationRegistrationGroup() {
        return of(ROLE_EMS_USER, ROLE_MRPO_USER);
    }
}
