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

import ru.russianpost.tracking.portal.admin.validation.validators.ContainerConfigurationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates class that should be validated with {@link ContainerConfigurationValidator}.
 *
 * @author KKiryakov
 */
@NotNull
@Documented
@Constraint(validatedBy = ContainerConfigurationValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
@SuppressWarnings("checkstyle:JavadocMethod")
public @interface ValidContainerConfiguration {

    /** Error message. */
    String message() default "{ru.russianpost.tracking.input.web.validation.constraints.ValidContainerConfiguration.message}";

    /** Groups. */
    Class<?>[] groups() default { };

    /** Payload. */
    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@link ValidContainerConfiguration} annotations on the same element.
     *
     * @see ValidContainerConfiguration
     */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {

        ValidContainerConfiguration[] value();

    }
}
