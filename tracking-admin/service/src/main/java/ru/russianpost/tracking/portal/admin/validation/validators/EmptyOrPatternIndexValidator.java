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

import ru.russianpost.tracking.portal.admin.validation.constraints.EmptyOrPatternIndex;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * HistoryRecordIdValidator
 *
 * @author aalekseenko
 */
public class EmptyOrPatternIndexValidator implements ConstraintValidator<EmptyOrPatternIndex, String> {

    private static final Pattern REGEXP = Pattern.compile(Regexp.INDEX);

    @Override
    public void initialize(final EmptyOrPatternIndex constraintAnnotation) {
        // NOP
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return validateIndexOper(value);
    }

    private boolean validateIndexOper(final String v) {
        return v == null || v.isEmpty() || REGEXP.matcher(v).matches();
    }

}
