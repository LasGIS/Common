/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.validation.validators;

import ru.russianpost.tracking.portal.admin.security.Role;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidRoles;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator for list of roles.
 *
 * @author KKiryakov
 */
public class RolesValidator implements ConstraintValidator<ValidRoles, String[]> {

    private Set<String> knownRoles;

    @Override
    public void initialize(ValidRoles validRoles) {
        knownRoles = Arrays.stream(Role.values()).filter(r -> r != Role.UNKNOWN).map(Role::name).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String[] roles, ConstraintValidatorContext context) {
        for (String role : roles) {
            if (!knownRoles.contains(role)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Unknown role: " + role).addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
