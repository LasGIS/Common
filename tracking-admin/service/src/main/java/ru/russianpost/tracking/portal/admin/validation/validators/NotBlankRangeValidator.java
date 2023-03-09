/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.validation.validators;

import ru.russianpost.tracking.portal.admin.validation.constraints.NotBlankRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Validator for {@link NotBlankRange}.
 *
 * @author sslautin
 * @version 1.0 30.09.2015
 */
public class NotBlankRangeValidator implements ConstraintValidator<NotBlankRange, String> {

    private long min;
    private long max;

    @Override
    public void initialize(final NotBlankRange constraintAnnotation) {

        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {

        boolean result = true;

        if (value != null && !value.isEmpty()) {
            try {
                final BigDecimal dv = new BigDecimal(value);
                result = dv.compareTo(BigDecimal.valueOf(min)) >= 0 && dv.compareTo(BigDecimal.valueOf(max)) <= 0;
            } catch (Exception e) {
                result = false;
            }
        }

        return result;
    }
}
