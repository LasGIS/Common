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

import ru.russianpost.tracking.portal.admin.validation.validators.NotBlankRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element has to be in the appropriate range. Apply on string representation of the numeric value.<br>
 * Null and empty strings are considered valid.
 *
 * @author sslautin
 * @version 1.0 30.09.2015
 */
@Documented
@Constraint(validatedBy = NotBlankRangeValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@SuppressWarnings("checkstyle:JavadocMethod")
public @interface NotBlankRange {

    /** Min value. */
    long min() default 0;

    /** Max value. */
    long max() default Long.MAX_VALUE;

    /** Error message. */
    String message() default "{ru.russianpost.tracking.input.web.validation.constraints.NotBlankRange.message}";

    /** Groups. */
    Class<?>[] groups() default {};

    /** Payload. */
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @NotBlankRange} annotations on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    public @interface List {

        /** Value. */
        NotBlankRange[] value();
    }
}
