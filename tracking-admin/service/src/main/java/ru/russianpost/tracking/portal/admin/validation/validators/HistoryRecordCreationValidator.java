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

import org.springframework.beans.factory.annotation.Qualifier;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordCreation;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.AllowedToAddOperationsHolder;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidHistoryRecordCreation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;

/**
 * HistoryRecordIdValidator
 * @author kkiryakov
 */
public class HistoryRecordCreationValidator implements ConstraintValidator<ValidHistoryRecordCreation, HistoryRecordCreation> {

    private final Map<Integer, List<Integer>> allowedToAddOperationsMap;

    /**
     * Constructor
     * @param allowedToAddOperationsHolder instance of {@link AllowedToAddOperationsHolder}
     */
    public HistoryRecordCreationValidator(
        @Qualifier("allowedToAddOperationsHolder") final AllowedToAddOperationsHolder allowedToAddOperationsHolder
    ) {
        this.allowedToAddOperationsMap = allowedToAddOperationsHolder.getAllowedToAddOperations();
    }

    @Override
    public void initialize(ValidHistoryRecordCreation constraintAnnotation) {
        // NOP
    }

    @Override
    public boolean isValid(HistoryRecordCreation creation, ConstraintValidatorContext constraintValidatorContext) {
        return isOperationValid(creation.getOperType(), creation.getOperAttr(), constraintValidatorContext);
    }

    private boolean isOperationValid(Integer operType, Integer operAttr, ConstraintValidatorContext constraintValidatorContext) {
        if (operType == null) {
            addViolation(constraintValidatorContext, "Operation type can't be null");
            return false;
        }
        if (operAttr == null) {
            addViolation(constraintValidatorContext, "Operation attribute can't be null");
            return false;
        }
        final List<Integer> allowedAttrs = allowedToAddOperationsMap.get(operType);
        if (allowedAttrs == null) {
            addViolation(constraintValidatorContext, "Operation of type '" + operType + "' is not allowed");
            return false;
        }
        if ((allowedAttrs.isEmpty() && operAttr != -1) || (!allowedAttrs.isEmpty() && !allowedAttrs.contains(operAttr))) {
            addViolation(constraintValidatorContext, "Operation type '" + operType + "' doesn't allow operation attribute '" + operAttr + "'");
            return false;
        }
        return true;
    }

    private void addViolation(ConstraintValidatorContext constraintValidatorContext, String message) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
            .buildConstraintViolationWithTemplate(message)
            .addConstraintViolation();
    }
}
