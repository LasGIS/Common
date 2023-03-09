/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.controller.writer.CsvResponse;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.ui.AccessTypeStat;
import ru.russianpost.tracking.portal.admin.model.ui.CompanyInfo;
import ru.russianpost.tracking.portal.admin.model.ui.ProfileStatistics;
import ru.russianpost.tracking.portal.admin.model.ui.ProfileStatisticsPlainRecord;
import ru.russianpost.tracking.portal.admin.service.statistics.ProfileReportService;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileStatService;
import ru.russianpost.tracking.web.model.core.PostUser;
import ru.russianpost.tracking.web.model.core.Protocol;
import ru.russianpost.tracking.web.model.utils.notification.service.Paging;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Dec 21, 2015)
 */
@RestController
@RequestMapping("/api/v1/profile/stat")
public class ProfileStat extends BaseController {

    private final List<String> headers;

    private final ProfileStatService profileStatService;
    private final ProfileReportService profileReportService;

    /**
     * ProfileStatController
     *
     * @param headersRaw headersRaw separated by ';' character
     * @param profileStatService profile statistics provider
     * @param profileReportService profile rich statistics provider
     */
    public ProfileStat(
        @Value("${unlimited.report.headers}") String headersRaw,
        ProfileStatService profileStatService,
        ProfileReportService profileReportService
    ) {
        this.headers = Arrays.asList(headersRaw.split(";"));
        this.profileStatService = profileStatService;
        this.profileReportService = profileReportService;
    }

    /**
     * Return statistics for unlimited profiles
     *
     * @param from from (paging)
     * @param count profile count
     * @return statistics
     * @throws ServiceUnavailableException service not available
     */
    @GetMapping(value = "/unlimited", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProfileStatistics> getUnlimited(
        @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
        @RequestParam(value = "count", required = false, defaultValue = "10") Integer count
    ) throws ServiceUnavailableException {
        return profileStatService.getProfileStatsByAccessType("UNLIMITED", new Paging(from, count))
            .stream()
            .map(s -> new ProfileStatistics(s.getCreated(),
                CompanyInfo.from(s.getProfile().getCompany()),
                s.getProfile()
                    .getPostUsers()
                    .stream()
                    .map(PostUser::getEmail)
                    .collect(Collectors.toList()),
                s.getProfile().getLogin(),
                s.getRtm34(),
                s.getFc(),
                s.getProfile().getClientType().getCode(),
                s.getProfile().getInternalComment()
            ))
            .collect(Collectors.toList());
    }

    /**
     * Overall unique users statistics for protocol
     * @param protocolName protocol name
     * @return stat list
     * @throws ServiceUnavailableException statistics service unavailable
     */
    @GetMapping(value = "/unique-users/{protocol}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<AccessTypeStat> getOverallUniqueUserStatistics(
        @PathVariable("protocol") String protocolName
    ) throws ServiceUnavailableException {
        return profileStatService.getOverallUniqueUserStats(Protocol.of(protocolName))
            .stream()
            .map(b -> new AccessTypeStat(b.getDay(), b.getValue(), accessTypeByLimit(b.getLimit())))
            .collect(Collectors.toList());
    }

    /**
     * Overall access type statistics
     *
     * @return stat list
     * @throws ServiceUnavailableException statistics service unavailable
     */
    @GetMapping(value = "/access-type", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<AccessTypeStat> getAccessTypeUserStatistics(
    ) throws ServiceUnavailableException {
        return profileStatService.getAccessTypeStatistics().stream()
            .map(b -> new AccessTypeStat(b.getDay(), b.getValue(), b.getAccessType()))
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/unlimited/report", produces = "text/csv;charset=utf-8")
    @ResponseBody
    public CsvResponse<ProfileStatisticsPlainRecord> getUnlimitedReport() {

        return new CsvResponse<>(profileReportService.getStatistics("UNLIMITED")
            .stream()
            .map(s -> new ProfileStatisticsPlainRecord(formatDate(s.getCreated()),
                s.getProfile().getCompany() == null ? "" : s.getProfile()
                    .getCompany()
                    .getName(),
                s.getProfile()
                    .getPostUsers()
                    .stream()
                    .map(PostUser::getEmail)
                    .collect(Collectors.joining(", ")),
                s.getProfile().getLogin(),
                s.getRtm34(),
                s.getFc(),
                formatClientType(s.getProfile().getClientType().getCode()),
                s.getProfile().getInternalComment()
            ))
            .collect(Collectors.toList()), headers, generateReportName());
    }

    private String formatDate(Long ts) {
        return DateTimeFormatter
            .ofPattern("dd.MM.uuuu")
            .format(LocalDateTime.ofInstant(Instant.ofEpochMilli(ts), ZoneId.of("UTC")));
    }

    private String generateReportName() {
        return "unlimited_" + DateTimeFormatter.ofPattern("uuuuMMdd").format(LocalDate.now()) + ".csv";
    }

    private static final Map<String, String> REGISTERED_CIENT_TYPES = ImmutableMap.of(
        "GOVERMENTAL", "Государственный",
        "INTERNAL", "Внутренний (Почта или почтовые сервисы)",
        "COMMERCIAL", "Коммерческий"
    );

    private String formatClientType(String clientTypeCode) {
        return REGISTERED_CIENT_TYPES.getOrDefault(clientTypeCode, "");
    }

    private String accessTypeByLimit(Integer limit) {
        if (limit == null) {
            return null;
        }
        if (Integer.valueOf(100).equals(limit)) {
            return "limited";
        }
        if (Integer.valueOf(Integer.MAX_VALUE).equals(limit)) {
            return "unlimited";
        }
        return limit.toString();
    }
}
