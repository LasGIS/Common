/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.ConfigHolder;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;
import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.operations.OperationInfo;
import ru.russianpost.tracking.portal.admin.security.Role;
import ru.russianpost.tracking.web.model.core.AccessType;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static ru.russianpost.tracking.web.model.utils.Strings.isBlank;
import static ru.russianpost.tracking.web.model.utils.Strings.isNotBlank;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 04, 2015)
 */
@RestController
@RequestMapping("/api/v1")
public class Config extends BaseController {

    /**
     * Default limited access type name.
     */
    private static final String LIMITED_ACCESS_TYPE_NAME = "LIMITED";

    private final Dictionary dictionary;
    private final ServiceUserDao userRepo;
    private final String dadataUrl;
    private final String trackingApiPortalDictionariesUrl;
    private final String naumenTrackingSupportUrl;

    private final long incomeDateOperDateWarningInterval;
    private final long loadDateIncomeDateWarningInterval;
    private final int bookingsCountOnThePage;
    private final MultipleTrackingConfig multipleTrackingConfig;
    private final BarcodeAutomatizationConfig barcodeAutomatizationConfig;
    private final EmsevtManualConfig emsevtManualConfig;
    private final InternationalMonitoringConfig internationalMonitoringConfig;

    /**
     * Constructor.
     *
     * @param dictionary                       dictionary
     * @param userRepo                         user repository
     * @param dadataUrl                        dadata Url
     * @param trackingApiPortalDictionariesUrl tracking api portal dictionaries Url
     * @param naumenTrackingSupportUrl         naumen tracking support Url
     */
    public Config(
        Dictionary dictionary,
        ServiceUserDao userRepo,
        @Value("${ru.russianpost.dadata.suggestions.service.url}") String dadataUrl,
        @Value("${ru.russianpost.tracking.api.portal.dictionaries.url}") String trackingApiPortalDictionariesUrl,
        @Value("${naumen.tracking.support.url}") String naumenTrackingSupportUrl
    ) {
        super();
        this.dictionary = dictionary;
        this.userRepo = userRepo;
        this.dadataUrl = UriComponentsBuilder.fromUriString(dadataUrl).path("/party").toUriString();
        this.trackingApiPortalDictionariesUrl = trackingApiPortalDictionariesUrl;
        this.naumenTrackingSupportUrl = naumenTrackingSupportUrl;
        final com.typesafe.config.Config config = ConfigHolder.CONFIG;
        this.incomeDateOperDateWarningInterval = config.getLong("incomeDate.operDate.warning.interval.minutes");
        this.loadDateIncomeDateWarningInterval = config.getLong("loadDate.incomeDate.warning.interval.minutes");
        this.bookingsCountOnThePage = config.getInt("bookings.count.on.the.page");
        this.multipleTrackingConfig = new MultipleTrackingConfig(
            config.getInt("multiple.tracking.barcode.limit"),
            config.getInt("multiple.tracking.barcode.limit-per-subtask"),
            config.getInt("multiple.tracking.update.interval.seconds")
        );
        this.barcodeAutomatizationConfig = new BarcodeAutomatizationConfig(
            config.getInt("barcode.automatization.barcode.limit")
        );
        this.emsevtManualConfig = new EmsevtManualConfig(
            config.getInt("emsevt.manual.barcode.limit"),
            config.getInt("emsevt.manual.update.interval.seconds")
        );
        this.internationalMonitoringConfig = new InternationalMonitoringConfig(
            config.getInt("international.monitoring.update.interval.seconds"),
            config.getInt("international.monitoring.cacheable.wait.seconds"),
            config.getBoolean("international.monitoring.show")
        );
    }

    /**
     * Config representation
     */
    @lombok.Value
    @SuppressWarnings("RedundantModifiersValueLombok")
    public static class Configuration {
        private final UserInfo user;
        private final String dadataUrl;
        private final String trackingApiPortalDictionariesUrl;
        private final String naumenTrackingSupportUrl;
        private final AccessType defaultLimitedAccessType;
        private final Map<Integer, List<Integer>> allowedToCreateOperations;
        private final List<OperationInfo> operationOrderList;
        private final Map<String, Object> dictionaries;
        private final List<String> authorities;
        private final long incomeDateOperDateWarningInterval;
        private final long loadDateIncomeDateWarningInterval;
        private final int bookingsCountOnThePage;
        private final MultipleTrackingConfig multipleTracking;
        private final BarcodeAutomatizationConfig barcodeAutomatization;
        private final EmsevtManualConfig emsevtManual;
        private final InternationalMonitoringConfig internationalMonitoring;
    }

    /**
     * Multiple tracking configuration
     */
    @lombok.Value
    @SuppressWarnings("RedundantModifiersValueLombok")
    public static class MultipleTrackingConfig {
        private final int barcodeLimit;
        private final int barcodeLimitPerSubtask;
        private final int updateIntervalSeconds;
    }

    /**
     * Emsevt manual sender configuration
     */
    @lombok.Value
    @SuppressWarnings("RedundantModifiersValueLombok")
    public static class EmsevtManualConfig {
        private final int barcodeLimit;
        private final int updateIntervalSeconds;
    }

    /**
     * Emsevt manual sender configuration
     */
    @lombok.Value
    @SuppressWarnings("RedundantModifiersValueLombok")
    public static class InternationalMonitoringConfig {
        private final int updateIntervalSeconds;
        private final int cacheableWaitSeconds;
        private final Boolean show;
    }

    /**
     * Barcode automatization configuration
     */
    @lombok.Value
    @SuppressWarnings("RedundantModifiersValueLombok")
    public static class BarcodeAutomatizationConfig {
        private final int barcodeLimit;
    }

    /**
     * User information representation.
     */
    public static final class UserInfo {

        private final String username;
        private final String fullName;

        /**
         * Constructor.
         *
         * @param username Username (login)
         * @param fullName Full name
         */
        public UserInfo(final String username, final String fullName) {
            this.username = username;
            this.fullName = fullName;
        }

        public String getUsername() {
            return username;
        }

        public String getFullName() {
            return fullName;
        }
    }

    /**
     * Returns configuration.
     *
     * @param req  HttpServletRequest
     * @param user user
     * @return config configuration
     */
    @GetMapping(value = "/config")
    public Configuration get(final HttpServletRequest req, final Authentication user) {
        final AccessType accessType = dictionary
            .getPredefinedAccessTypes()
            .stream()
            .filter(a -> LIMITED_ACCESS_TYPE_NAME.equals(a.getName()))
            .findFirst()
            .orElse(null);
        if (accessType == null) {
            throw new IllegalStateException("Can't find predefined access type with name '" + LIMITED_ACCESS_TYPE_NAME + "'.");
        }
        Map<String, Object> dictionaries = new HashMap<>();
        dictionaries.put("operTypes", dictionary.getRtm02OperTypes());
        dictionaries.put("operAttrs", dictionary.getRtm02OperAttrs());
        dictionaries.put("countries", dictionary.getCountries());
        dictionaries.put("mailCategories", dictionary.getRtm02MailCategories());
        dictionaries.put("mailTypes", dictionary.getRtm02MailTypes());
        dictionaries.put("currencies", dictionary.getCurrencyDecimalPlaces());
        dictionaries.put("smsTypeOrder", dictionary.getSmsTypeOrderMap());
        dictionaries.put("customsStatuses", dictionary.getRtm02CustomsStatuses());
        dictionaries.put("packageTypes", dictionary.getPackageTypes());
        dictionaries.put("packageKinds", dictionary.getPackageKinds());

        if (req.isUserInRole(Role.ROLE_ADMIN.name())) {
            dictionaries.put(
                "roles",
                Arrays.stream(Role.values()).filter(r -> r != Role.UNKNOWN).collect(toLinkedMap(Role::name, Function.identity()))
            );
        }

        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return new Configuration(
            buildUserInfo(user.getName()),
            this.dadataUrl,
            this.trackingApiPortalDictionariesUrl,
            this.naumenTrackingSupportUrl,
            accessType,
            dictionary.getAllowedToAddOperations(),
            dictionary.getOperationOrderList(),
            dictionaries,
            authorities,
            incomeDateOperDateWarningInterval,
            loadDateIncomeDateWarningInterval,
            bookingsCountOnThePage,
            multipleTrackingConfig,
            barcodeAutomatizationConfig,
            emsevtManualConfig,
            internationalMonitoringConfig
        );
    }

    private UserInfo buildUserInfo(String username) {
        final ServiceUser serviceUser = userRepo.getServiceUser(username);
        return new UserInfo(username, makeFullName(serviceUser));
    }

    /**
     * Returns full name representation of ServiceUser.
     *
     * @param u ServiceUser
     * @return Full name
     */
    private static String makeFullName(final ServiceUser u) {
        if (u == null || isBlank(u.getSurname())) {
            return null;
        } else {
            final StringBuilder sb = new StringBuilder();
            sb.append(u.getSurname());
            if (isNotBlank(u.getName())) {
                sb.append(' ').append(u.getName());
                if (isNotBlank(u.getPatronymic())) {
                    sb.append(' ').append(u.getPatronymic());
                }
            }
            return sb.toString();
        }
    }

    private Collector<Role, ?, LinkedHashMap<String, Role>> toLinkedMap(Function<Role, String> keyMapper, Function<Role, Role> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper, (k, v) -> {
            throw new IllegalStateException("Duplicate key " + k);
        }, LinkedHashMap::new);
    }
}
