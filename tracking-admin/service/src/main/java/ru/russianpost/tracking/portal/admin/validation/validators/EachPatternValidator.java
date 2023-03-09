/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.validation.validators;

import ru.russianpost.tracking.portal.admin.validation.constraints.EachPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * Validator by pattern for collection of strings.
 *
 * @author KKiryakov
 */
public class EachPatternValidator implements ConstraintValidator<EachPattern, Collection<String>> {

    private String regex;

    @Override
    public void initialize(final EachPattern constraintAnnotation) {
        regex = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Collection<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        for (String string : strings) {
            if (!string.matches(regex)) {
                return false;
            }
        }
        return true;
    }
}
