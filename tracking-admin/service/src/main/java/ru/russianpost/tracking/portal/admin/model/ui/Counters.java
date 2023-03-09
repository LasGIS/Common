/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.ui;

import ru.russianpost.tracking.web.model.admin.AdminPageProfileCounters;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Dec 18, 2015)
 */
public class Counters {

    private final Counter rtm34;
    private final Counter fc;

    /**
     * Counters
     * @param rtm34 for RTM34
     * @param fc for FC
     */
    public Counters(Counter rtm34, Counter fc) {
        this.rtm34 = rtm34;
        this.fc = fc;
    }

    /**
     * Creates controller object from service object
     * @param counters service object
     * @return controller object
     */
    public static Counters of(AdminPageProfileCounters counters) {
        return new Counters(
            new Counter(counters.getRtm34().getCount(), counters.getRtm34().getLimit()),
            new Counter(counters.getFc().getCount(), counters.getFc().getLimit())
        );
    }

    public Counter getRtm34() {
        return rtm34;
    }

    public Counter getFc() {
        return fc;
    }
}
