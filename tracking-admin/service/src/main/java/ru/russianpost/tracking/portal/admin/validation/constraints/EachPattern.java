/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.validation.constraints;

import ru.russianpost.tracking.portal.admin.validation.validators.EachPatternValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element has to be a collection of strings that matches specified pattern.
 *
 * @author KKiryakov
 */
@Constraint(validatedBy = {EachPatternValidator.class})
@Target(FIELD)
@Retention(RUNTIME)
@SuppressWarnings("checkstyle:JavadocMethod")
public @interface EachPattern {

    /**
     * Value.
     */
    String value();

    /**
     * Error message.
     */
    String message() default "ru.russianpost.tracking.portal.admin.validation.constraints.EachPattern.message";

    /**
     * Groups.
     */
    Class<?>[] groups() default { };

    /**
     * Payload.
     */
    Class<? extends Payload>[] payload() default { };

}
