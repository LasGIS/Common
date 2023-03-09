/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The string has to be a well-formed email address.
 *
 * @author VLaskin
 * @since <pre>30.07.2020</pre>
 */
@NotNull
@Documented
@Constraint(validatedBy = {})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@SuppressWarnings("checkstyle:JavadocMethod")
public @interface Email {

    /** Error message. */
    String message() default "{org.hibernate.validator.constraints.Email.message}";

    /** Groups. */
    Class<?>[] groups() default {};

    /** Payload. */
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @Email} annotations on the same element.
     */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Email[] value();
    }
}
