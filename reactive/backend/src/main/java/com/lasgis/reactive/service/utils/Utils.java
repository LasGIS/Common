/*
 *  @(#)Utils.java  last: 13.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.utils;

import com.lasgis.reactive.model.PersonRelation;
import com.lasgis.reactive.model.entity.PersonRelationType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

/**
 * The Class Utils definition.
 *
 * @author VLaskin
 * @since 13.09.2023 : 15:22
 */
public class Utils {

    private static final Map<PersonRelationType, PersonRelationType> OPPOSITE_PERSON_RELATION_MAP = Map.ofEntries(
        entry(PersonRelationType.CHILD, PersonRelationType.PARENT),
        entry(PersonRelationType.PARENT, PersonRelationType.CHILD),
        entry(PersonRelationType.SPOUSE, PersonRelationType.SPOUSE),
        entry(PersonRelationType.SIBLING, PersonRelationType.SIBLING),
        entry(PersonRelationType.RELATIVE, PersonRelationType.RELATIVE),
        entry(PersonRelationType.COLLEAGUE, PersonRelationType.COLLEAGUE)
    );

    public static String getPersonRelationKey(PersonRelation relation) {
        return "key_" + relation.getPersonId() + "_" + relation.getPersonToId() + "_" + relation.getType();
    }

    public static final Function<List<PersonRelation>, List<PersonRelation>> NORMALIZED_PERSON_RELATION_LIST =
        (inList) -> {
            final Map<String, PersonRelation> map = new HashMap<>();
            inList.forEach(relation -> {
                final PersonRelation opposite = PersonRelation.builder()
                    .personId(relation.getPersonToId())
                    .personToId(relation.getPersonId())
                    .type(OPPOSITE_PERSON_RELATION_MAP.get(relation.getType()))
                    .build();
                final String key = getPersonRelationKey(relation);
                final String oppositeKey = getPersonRelationKey(opposite);
                if (!map.containsKey(key)) {
                    map.put(key, relation);
                }
                if (!map.containsKey(oppositeKey)) {
                    map.put(oppositeKey, opposite);
                }
            });
            return map.values().stream().toList();
        };

}
