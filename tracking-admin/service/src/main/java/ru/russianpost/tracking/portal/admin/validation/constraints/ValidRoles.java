/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.validation.constraints;

import ru.russianpost.tracking.portal.admin.validation.validators.RolesValidator;

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
 * Indicates class that should be validated with {@link RolesValidator}.
 *
 * @author KKiryakov
 */
@NotNull
@Documented
@Constraint(validatedBy = RolesValidator.class)
@java.lang.annotation.Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@SuppressWarnings("checkstyle:JavadocMethod")
public @interface ValidRoles {

    /** Error message. */
    String message() default "{ru.russianpost.tracking.portal.admin.validation.constraints.ValidRoles.message}";

    /** Groups. */
    Class<?>[] groups() default { };

    /** Payload. */
    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@link ValidRoles} annotations on the same element.
     *
     * @see ValidRoles
     */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {

        ValidRoles[] value();

    }

}
