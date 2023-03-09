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

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 22, 2015)
 */
@DisallowConcurrentExecution
public class ReloadStatisticsJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(ReloadStatisticsJob.class);
    /**
     * Target service
     */
    public static final String TARGET = "target";
    /**
     * Retry interval, milliseconds
     */
    public static final String RETRY_INTERVAL = "retryInterval";
    /**
     * Max tries allowed
     */
    public static final String TRIES = "tries";
    private static final String TRIES_FAILED = "triesFailed";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        try {
            ProfileReportService profileReportService = (ProfileReportService) dataMap.get(TARGET);
            profileReportService.reload();
        } catch (Exception e) {
            Integer tries = Optional.ofNullable(dataMap.getIntegerFromString(TRIES)).orElse(0);
            Integer triesFailed = dataMap.containsKey(TRIES_FAILED) ? dataMap.getIntegerFromString(TRIES_FAILED) : 0;

            if (triesFailed >= tries) {
                throw new JobExecutionException(e);
            }
            triesFailed++;
            long retryInterval = Optional.ofNullable(dataMap.getLongFromString(RETRY_INTERVAL)).orElse(0L);
            LOG.info("Job execution failed. Retrying {} of {}... in {} sec", triesFailed, tries, retryInterval / 1000);

            dataMap.putAsString(TRIES_FAILED, triesFailed);
            //sleep for 10 seconds or more is it's set
            try {
                Thread.sleep(Math.max(10000, retryInterval));
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
            }

            //fire it again
            JobExecutionException e2 = new JobExecutionException(e);
            e2.setRefireImmediately(true);
            throw e2;
        }
    }
}
