/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.validation.validators;

import ru.russianpost.tracking.portal.admin.validation.constraints.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

import static ru.russianpost.tracking.portal.admin.validation.validators.Regexp.EMAIL;


/**
 * Checks that a given character sequence (e.g. string) is a well-formed email address.
 *
 * @author VLaskin
 * @since <pre>30.07.2020</pre>
 */
public class EmailValidator implements ConstraintValidator<Email, CharSequence> {

    private static final Pattern EMAIL_REGEXP = Pattern.compile(EMAIL);

    @Override
    public void initialize(Email annotation) {
        // NOP
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return Objects.nonNull(value) && EMAIL_REGEXP.matcher(value).matches();
    }
}
