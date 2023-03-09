/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.validation.constraints;

import ru.russianpost.tracking.portal.admin.validation.validators.HistoryRecordIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ValidHistoryRecordId
 *
 * @author aalekseenko
 */
@NotNull
@Documented
@Constraint(validatedBy = HistoryRecordIdValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
@SuppressWarnings("checkstyle:JavadocMethod")
public @interface ValidHistoryRecordId {

    /** Error message. */
    String message() default "Редактирование данной операции недоступно!";

    /** Groups. */
    Class<?>[] groups() default { };

    /** Payload. */
    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@link ValidHistoryRecordId} annotations on the same element.
     *
     * @see ValidHistoryRecordId
     */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {

        ValidHistoryRecordId[] value();

    }
}
