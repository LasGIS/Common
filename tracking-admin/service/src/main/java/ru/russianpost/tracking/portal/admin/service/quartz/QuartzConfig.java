/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.quartz;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import ru.russianpost.tracking.portal.admin.service.statistics.ProfileReportService;
import ru.russianpost.tracking.portal.admin.service.statistics.ReloadStatisticsJob;

import java.util.HashMap;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 22, 2015)
 */
@Configuration
public class QuartzConfig {

    /**
     * @return job
     */
    @Bean
    public JobDetailFactoryBean jobDetail(
        @Value("${ru.russianpost.tracking.service.retry.interval.ms}") long retryInterval,
        @Value("${ru.russianpost.tracking.service.retry.tries}") int tries,
        ProfileReportService profileReportService
    ) {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        HashMap<String, Object> params = new HashMap<>(3);
        params.put(ReloadStatisticsJob.TARGET, profileReportService);
        params.put(ReloadStatisticsJob.RETRY_INTERVAL, Long.toString(retryInterval));
        params.put(ReloadStatisticsJob.TRIES, Integer.toString(tries));
        bean.setJobDataMap(new JobDataMap(params));
        bean.setJobClass(ReloadStatisticsJob.class);
        return bean;
    }

    /**
     * @return on start trigger
     */
    @Bean
    public SimpleTriggerFactoryBean startTrigger(JobDetailFactoryBean jobDetail) {
        final SimpleTriggerFactoryBean bean = new SimpleTriggerFactoryBean();
        bean.setJobDetail(jobDetail.getObject());
        bean.setStartDelay(10000);
        bean.setRepeatInterval(1);
        bean.setRepeatCount(0);
        return bean;
    }

    /**
     * @return on scheduled trigger
     */
    @Bean
    public CronTriggerFactoryBean scheduledTrigger(JobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(jobDetail.getObject());
        // every day, at midnight
        bean.setCronExpression("0 0 0 * * ? *");
        return bean;
    }

    /**
     *
     * @return scheduler
     */
    @Bean
    public SchedulerFactoryBean scheduler(SimpleTriggerFactoryBean startTrigger, CronTriggerFactoryBean scheduledTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(startTrigger.getObject(), scheduledTrigger.getObject());
        return bean;
    }
}

