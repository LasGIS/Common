/*
 *  @(#)RunTransactionalBean.java  last: 24.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The Class RunTransactionalBean definition.
 *
 * @author VLaskin
 * @since 24.05.2023 : 14:25
 */
@Slf4j
@Component
public class RunTransactionalBean {
    private final TransactionalBean tran;

    public RunTransactionalBean(TransactionalBean tran) {
        this.tran = tran;
    }

    @PostConstruct
    void run() {
        try {
            tran.method();
            log.info("tran.getCount() = {}", tran.getCount());
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            log.info("tran.getCount() = {}", tran.getCount());
        }
    }
}
