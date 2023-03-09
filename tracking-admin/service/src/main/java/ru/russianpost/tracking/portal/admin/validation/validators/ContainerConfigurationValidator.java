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

import ru.russianpost.tracking.portal.admin.model.barcode.provider.ContainerConfiguration;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidContainerConfiguration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for containers configuration.
 *
 * @author KKiryakov
 */
public class ContainerConfigurationValidator implements ConstraintValidator<ValidContainerConfiguration, ContainerConfiguration> {

    @Override
    public void initialize(ValidContainerConfiguration validContainerConfiguration) {
        // NOP
    }

    @Override
    public boolean isValid(ContainerConfiguration configuration, ConstraintValidatorContext context) {
        final Integer min = configuration.getMin();
        final Integer max = configuration.getMax();
        final Integer allocationSize = configuration.getAllocationSize();
        if (max != null && min != null) {
            int range = max - min;
            if (range <= 0) {
                buildViolation(context, "Max value should be greater than min value");
                return false;
            }
            if (allocationSize != null && allocationSize >= range) {
                buildViolation(context, "Allocation size should be lower than container's range");
                return false;
            }
        }
        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
