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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordCreation;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.AllowedToAddOperationsHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Test for {@link HistoryRecordCreationValidator}.
 *
 * @author KKiryakov
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoryRecordCreationValidatorTest implements ConstraintValidatorFactory {

    private static final Consumer<Collection<?>> SUCCESS = collection -> assertThat(collection, empty());
    private static final Consumer<Collection<?>> FAILURE = collection -> assertThat(collection, not(empty()));

    @Mock
    AllowedToAddOperationsHolder allowedToAddOperationsHolder;

    private Validator validator;

    @Before
    public void before() {
        final javax.validation.Configuration<?> config = Validation.byDefaultProvider().configure();
        config.constraintValidatorFactory(this);
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateSuccess() throws Exception {
        Map<Integer, List<Integer>> allowedOpers = new HashMap<>();
        allowedOpers.put(14, Collections.singletonList(3));
        when(allowedToAddOperationsHolder.getAllowedToAddOperations()).thenReturn(allowedOpers);
        HistoryRecordCreation creation = buildHistoryRecordCreation(14, 3);
        validateHistoryRecordCreation(creation, SUCCESS);
    }

    @Test
    public void validateSuccessWithoutAttr() throws Exception {
        Map<Integer, List<Integer>> allowedOpers = new HashMap<>();
        allowedOpers.put(11, Collections.emptyList());
        when(allowedToAddOperationsHolder.getAllowedToAddOperations()).thenReturn(allowedOpers);
        HistoryRecordCreation creation = buildHistoryRecordCreation(11, -1);
        validateHistoryRecordCreation(creation, SUCCESS);
    }

    @Test
    public void validateDisallowedType() throws Exception {
        Map<Integer, List<Integer>> allowedOpers = new HashMap<>();
        allowedOpers.put(14, Collections.singletonList(3));
        when(allowedToAddOperationsHolder.getAllowedToAddOperations()).thenReturn(allowedOpers);
        HistoryRecordCreation creation = buildHistoryRecordCreation(1, 1);
        final Set<ConstraintViolation<HistoryRecordCreation>> violations = validator.validate(creation);
        FAILURE.accept(violations);
        assertContainsViolationMessage(violations, "Operation of type '1' is not allowed");
    }

    private void assertContainsViolationMessage(Set<ConstraintViolation<HistoryRecordCreation>> violations, String expected) {
        final long count = violations.stream().filter(v -> expected.equals(v.getMessage())).count();
        assertTrue("Expected violation message not found", count > 0);
    }

    @Test
    public void validateDisallowedAttr() throws Exception {
        Map<Integer, List<Integer>> allowedOpers = new HashMap<>();
        allowedOpers.put(14, Collections.singletonList(3));
        when(allowedToAddOperationsHolder.getAllowedToAddOperations()).thenReturn(allowedOpers);
        HistoryRecordCreation creation = buildHistoryRecordCreation(14, 2);
        final Set<ConstraintViolation<HistoryRecordCreation>> violations = validator.validate(creation);
        FAILURE.accept(violations);
        assertContainsViolationMessage(violations, "Operation type '14' doesn't allow operation attribute '2'");
    }

    @Test
    public void validateNullType() throws Exception {
        Map<Integer, List<Integer>> allowedOpers = new HashMap<>();
        allowedOpers.put(14, Collections.singletonList(3));
        when(allowedToAddOperationsHolder.getAllowedToAddOperations()).thenReturn(allowedOpers);
        HistoryRecordCreation creation = buildHistoryRecordCreation(null, 3);
        final Set<ConstraintViolation<HistoryRecordCreation>> violations = validator.validate(creation);
        FAILURE.accept(violations);
        assertContainsViolationMessage(violations, "Operation type can't be null");
    }

    @Test
    public void validateNullAttr() throws Exception {
        Map<Integer, List<Integer>> allowedOpers = new HashMap<>();
        allowedOpers.put(14, Collections.singletonList(3));
        when(allowedToAddOperationsHolder.getAllowedToAddOperations()).thenReturn(allowedOpers);
        HistoryRecordCreation creation = buildHistoryRecordCreation(14, null);
        final Set<ConstraintViolation<HistoryRecordCreation>> violations = validator.validate(creation);
        FAILURE.accept(violations);
        assertContainsViolationMessage(violations, "Operation attribute can't be null");
    }

    private HistoryRecordCreation buildHistoryRecordCreation(Integer operType, Integer operAttr) {
        return new HistoryRecordCreation("45091203432890", "07.11.2016", "16:00", operType, operAttr, "644700", 1000, "УФПС Омской области", "Test");
    }

    private <T> void validateHistoryRecordCreation(final T historyRecordCreation, final Consumer<Collection<?>> assertConsumer) {
        assertConsumer.accept(validator.validate(historyRecordCreation));
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> aClass) {
        if (aClass == HistoryRecordCreationValidator.class) {
            return (T) new HistoryRecordCreationValidator(allowedToAddOperationsHolder);
        } else {
            try {
                return aClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> constraintValidator) {}
}
