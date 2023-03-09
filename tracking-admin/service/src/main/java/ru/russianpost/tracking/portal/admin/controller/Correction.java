/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrection;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionSourceSystem;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionType;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionJournalEntry;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionJournalResponse;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionType;
import ru.russianpost.tracking.portal.admin.model.ui.AdminUser;
import ru.russianpost.tracking.portal.admin.service.hdps.CorrectionsJournal;
import ru.russianpost.tracking.portal.admin.service.mappers.CorrectionTypeMapper;
import ru.russianpost.tracking.portal.admin.service.operation.correction.OperationCorrectionHistoryService;
import ru.russianpost.tracking.portal.admin.service.users.CorrectionAuthorInfoService;

import javax.validation.constraints.NotNull;
import java.time.DateTimeException;
import java.time.zone.ZoneRulesException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.russianpost.tracking.commons.model.HistoryRecordCorrectionSourceSystem.PORTAL_ADMIN;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Jan 21, 2016)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/correction")
public class Correction extends BaseController {

    private static final long SIXTY_DAYS_IN_MILLIS = 60L * 24L * 3600L * 1000L;

    private final StopWatchFactory stopWatchFactory;
    private final CorrectionTypeMapper getCorrectionType;
    private final CorrectionAuthorInfoService correctionAuthorInfoService;
    private final OperationCorrectionHistoryService operationCorrectionHistoryService;

    /**
     * List of corrections within specified interval
     * @param from            timestamp in local time zone for the start of the interval specifying
     * @param to              timestamp in local time zone for the end of the interval specifying
     * @param correctionTypes any combination of {@link CorrectionType} values, if null or empty - filter is not applied
     * @param sourceSystem    selected source system
     * @return List of corrections within specified interval with link to the next page
     * @throws ServiceUnavailableException correction information provider is unavailable
     */
    @ResponseBody
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public CorrectionJournalResponse list(
        @NotNull @RequestParam("from") long from,
        @NotNull @RequestParam("to") long to,
        @RequestParam(value = "corrType", required = false) String correctionTypes,
        @RequestParam(value = "sourceSystem") HistoryRecordCorrectionSourceSystem sourceSystem
    ) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("Correction:list");
        try {
            if (Math.abs(to - from) > SIXTY_DAYS_IN_MILLIS) {
                throw new DateTimeException("Interval is too long");
            }
            List<HistoryRecordCorrectionType> types = parse(correctionTypes);
            final CorrectionsJournal correctionsJournal = operationCorrectionHistoryService.getCorrections(
                from, to, types, sourceSystem
            );
            final List<HistoryRecordCorrection> earliestCorrections = correctionsJournal.getCorrections();
            final Map<String, AdminUser> authorNameToUser = resolveAuthorNameToUser(sourceSystem, earliestCorrections);

            final List<CorrectionJournalEntry> journalEntries = earliestCorrections.stream()
                .map(c -> new CorrectionJournalEntry(
                    c.getCreated(),
                    c.getOriginShipmentId(),
                    authorNameToUser.get(c.getAuthor()),
                    getCorrectionType.by(c.getType()),
                    c.getInitiator(),
                    c.getComment()
                ))
                .collect(Collectors.toList());
            return new CorrectionJournalResponse(journalEntries, correctionsJournal.getNextPageTimestamp());
        } finally {
            stopWatch.stop();
        }
    }

    private List<HistoryRecordCorrectionType> parse(String correctionTypes) {
        if (correctionTypes != null) {
            List<HistoryRecordCorrectionType> types = Stream.of(correctionTypes.split(","))
                .map(CorrectionType::by)
                .map(getCorrectionType::by)
                .collect(Collectors.toList());
            EnumSet<HistoryRecordCorrectionType> all = EnumSet.allOf(HistoryRecordCorrectionType.class);
            if (all.containsAll(types) && types.containsAll(all)) {
                // if collection already contains all types we disable any filtering
                return Collections.emptyList();
            }
            return types;
        } else {
            return Collections.emptyList();
        }
    }

    private Map<String, AdminUser> resolveAuthorNameToUser(
        HistoryRecordCorrectionSourceSystem sourceSystem,
        List<HistoryRecordCorrection> earliestCorrections
    ) {
        if (PORTAL_ADMIN == sourceSystem) {
            return earliestCorrections.stream()
                .map(HistoryRecordCorrection::getAuthor)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Function.identity(), correctionAuthorInfoService::resolveAuthor));
        }
        return emptyMap();
    }

    /**
     * Common internal error exception
     */
    @ExceptionHandler({DateTimeException.class, ZoneRulesException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidTimeZone() {
        // The method is empty because @ResponseStatus is enough
    }

}
