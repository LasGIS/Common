/*
 *  @(#)UtilsTest.java  last: 13.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.utils;

import com.lasgis.reactive.model.PersonRelation;
import com.lasgis.reactive.model.entity.PersonRelationType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.lasgis.reactive.service.utils.Utils.NORMALIZED_PERSON_RELATION_LIST;
import static org.junit.jupiter.params.provider.Arguments.of;

/**
 * The Class UtilsTest definition.
 *
 * @author VLaskin
 * @since 13.09.2023 : 15:52
 */
@Slf4j
class UtilsTest {

    public static List<Arguments> getUserByIdSource() {
        return List.of(
            of(List.of(
                PersonRelation.of(1L, 2L, null, PersonRelationType.CHILD),
                PersonRelation.of(3L, 2L, null, PersonRelationType.SPOUSE)
            ), List.of(
                PersonRelation.of(1L, 2L, null, PersonRelationType.CHILD),
                PersonRelation.of(2L, 1L, null, PersonRelationType.PARENT),
                PersonRelation.of(3L, 2L, null, PersonRelationType.SPOUSE),
                PersonRelation.of(2L, 3L, null, PersonRelationType.SPOUSE)
            )),
            of(List.of(
                PersonRelation.of(1L, 2L, null, PersonRelationType.COLLEAGUE),
                PersonRelation.of(2L, 3L, null, PersonRelationType.SIBLING),
                PersonRelation.of(3L, 4L, null, PersonRelationType.SPOUSE)
            ), List.of(
                PersonRelation.of(1L, 2L, null, PersonRelationType.COLLEAGUE),
                PersonRelation.of(3L, 2L, null, PersonRelationType.SIBLING),
                PersonRelation.of(2L, 1L, null, PersonRelationType.COLLEAGUE),
                PersonRelation.of(2L, 3L, null, PersonRelationType.SIBLING),
                PersonRelation.of(3L, 4L, null, PersonRelationType.SPOUSE),
                PersonRelation.of(4L, 3L, null, PersonRelationType.SPOUSE)
            ))
        );
    }

    @ParameterizedTest
    @MethodSource("getUserByIdSource")
    void checkBasicAuthenticationHeader(final List<PersonRelation> input, final List<PersonRelation> expected) {
        final List<PersonRelation> actual = NORMALIZED_PERSON_RELATION_LIST.apply(input);
        assertEquals(expected, actual);
    }

    private void assertEquals(final List<PersonRelation> expectedList, final List<PersonRelation> actualList) {
        Assertions.assertEquals(expectedList.size(), actualList.size());
        expectedList.forEach(expected -> Assertions.assertTrue(
            actualList.contains(expected),
            () -> String.format("Not Contains expected %s", expected)
        ));
        actualList.forEach(actual -> Assertions.assertTrue(
            expectedList.contains(actual),
            () -> String.format("Not Contains actual %s", actual)
        ));
    }
}