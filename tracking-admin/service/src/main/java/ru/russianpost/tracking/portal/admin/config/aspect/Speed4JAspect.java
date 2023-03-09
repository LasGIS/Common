/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.config.aspect;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Amosov Maxim
 * @since 02.09.2021 : 21:45
 */
@Aspect
@Component
@RequiredArgsConstructor
public class Speed4JAspect {
    private final StopWatchFactory stopWatchFactory;

    /**
     * Wraps method execution with speed4J
     *
     * @param joinPoint joinPoint
     * @return proceed result
     * @throws Throwable when error happened during proceed
     */
    @Around("@annotation(Speed4J)")
    public Object speed4J(final ProceedingJoinPoint joinPoint) throws Throwable {
        final String tag = joinPoint.getSignature().toShortString();
        final StopWatch stopWatch = stopWatchFactory.getStopWatch(tag);
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            stopWatch.stop(tag + ".fail");
            throw ex;
        } finally {
            stopWatch.stop();
        }
    }
}
