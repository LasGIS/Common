/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileStatService;
import ru.russianpost.tracking.portal.admin.utils.CollectionUtils;
import ru.russianpost.tracking.web.model.admin.AdminProfileStat;
import ru.russianpost.tracking.web.model.utils.notification.service.Paging;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 22, 2015)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DailyReportRestProfileReportService implements ProfileReportService {

    private final ProfileStatService profileStatService;

    private volatile List<AdminProfileStat> unlimitedStat = emptyList();

    @Override
    public List<AdminProfileStat> getStatistics(String accessType) {
        return unlimitedStat;
    }

    @Override
    public void reload() throws ServiceUnavailableException {
        log.info("Loading pre-calculated reports...");
        unlimitedStat = loadStatistics();
        log.info("Loading complete");
    }

    private List<AdminProfileStat> loadStatistics() throws ServiceUnavailableException {
        int from = 0;
        final int count = 10;
        List<AdminProfileStat> result = new ArrayList<>();
        while (true) {
            List<AdminProfileStat> batch = profileStatService.getProfileStatsByAccessType("UNLIMITED", new Paging(from, count));
            CollectionUtils.addOnlyNew(result, batch, p -> p.getProfile().getId());
            log.debug("Loaded {} profiles", result.size());
            if (batch.size() < count) {
                break;
            }
            from += batch.size();
        }
        return result;
    }
}
