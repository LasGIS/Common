/*
 *  @(#)HashMapTest.java  last: 31.05.2023
 *
 * Title: LG prototype for kafka + akka (simple or spring)
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class HashMapTest definition.
 *
 * @author VLaskin
 * @since 21.05.2023 : 1:14
 */
@Slf4j
public class HashMapTest {
    final static String[] UNIQUE_KEY = {
        "LongKey0123456789012345678901234567890123456789DB",
        "LongKey0123456789012345678901234567890123456789Ca",
        "I have a common prefixDB",
        "I have a common prefixCa"
    };

    @Test
    public void hashCodeCollision() {
        int hash0 = UNIQUE_KEY[2].hashCode();
        int hash1 = UNIQUE_KEY[3].hashCode();
        Assertions.assertEquals(hash0, hash1);
        Assertions.assertEquals(UNIQUE_KEY[0].hashCode(), UNIQUE_KEY[1].hashCode());
    }

    @Test
    public void addHashMap() {
        final Map<String, String> map = new HashMap<>();
        String val;
        val = map.put(UNIQUE_KEY[0], "val");
        log.info("map.put(UNIQUE_KEY[0], \"val\") => {}", val);
        val = map.put(UNIQUE_KEY[0], "val2");
        log.info("map.put(UNIQUE_KEY[0], \"val2\") => {}", val);
        val = map.put(UNIQUE_KEY[1], "val3");
        log.info("map.put(UNIQUE_KEY[1], \"val3\") => {}", val);
        val = map.put(UNIQUE_KEY[1], "val4");
        log.info("map.put(UNIQUE_KEY[1], \"val4\") => {}", val);
        val = map.remove(UNIQUE_KEY[0]);
    }

    @Test
    public void removeList() {
        final List<String> list = Collections.synchronizedList(new ArrayList<>());
        list.add(UNIQUE_KEY[0]);
        boolean bol = list.remove(UNIQUE_KEY[1]);
    }
}
