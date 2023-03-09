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

import ru.russianpost.tracking.portal.admin.model.operation.HistoryRecordId;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidHistoryRecordId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * HistoryRecordIdValidator
 *
 * @author aalekseenko
 */
public class HistoryRecordIdValidator implements ConstraintValidator<ValidHistoryRecordId, HistoryRecordId> {

    private static final Pattern INDEX_OPER_VALIDATION_REGEXP = Pattern.compile("[0-9]*");

    @Override
    public void initialize(ValidHistoryRecordId constraintAnnotation) {
        // NOP
    }

    @Override
    public boolean isValid(HistoryRecordId value, ConstraintValidatorContext context) {
        return validateIndexOper(value.getIndexOper());
    }

    /**
     * Allow indexOper to be empty string or a numeric value.
     * <p>Note: We doesn't use strict validation to give possibility for correcting operations with invalid indexOper.</p>
     */
    private boolean validateIndexOper(String indexOper) {
        return indexOper != null && INDEX_OPER_VALIDATION_REGEXP.matcher(indexOper).matches();
    }

}
