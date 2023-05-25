/*
 *  @(#)TransactionalBean.java  last: 24.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.bean;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class TransactionalBean definition.
 *
 * @author VLaskin
 * @since 24.05.2023 : 14:19
 */
@Component
public class TransactionalBean {
    @Getter
    private int count = 0;

    @Transactional
    public void method() {
        count++;
        // что-то делаем
        throw new RuntimeException("получаем exception");
    }

}
