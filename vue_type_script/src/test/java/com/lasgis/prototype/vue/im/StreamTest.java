/*
 *  @(#)StreemTest.java  last: 19.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.im;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Class StreemTest definition.
 *
 * @author VLaskin
 * @since 19.05.2023 : 12:51
 */
@Slf4j
public class StreamTest {
    @Data
    public static class Human {
        private final String name;
        private final int age;
    }

    @Test
    @Disabled
    void insert() {
        int[] count = {0};
        final Stream<String> stream = Stream.generate(() -> "Str" + count[0]++).parallel();
        stream.map(s -> s + "_int").forEach(s -> log.info("{}", s));
    }

    @Test
    void toStream() {
        final List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final Double average = list.stream().mapToDouble(Double::valueOf).average().orElse(0.0);
        final DoubleSummaryStatistics statistics = list.stream().mapToDouble(Double::valueOf).summaryStatistics();
        log.info("Average = {}, summaryStatistics = {}", average, statistics);
    }

    @Test
    public void
    givenComposition_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        List<Human> humans = List.of(
            new Human("Sarah", 12),
            new Human("Sarah", 10),
            new Human("Zack", 12)
        );
        List<Human> sortHumans = humans.stream().sorted(Comparator.comparing(Human::getName).thenComparing(Human::getAge)).toList();
        Assertions.assertThat(sortHumans.get(0)).isEqualTo(new Human("Sarah", 10));
    }

    @Test
    void toUpperCase() {
        List<String> list = Stream.of("a1", "a3", "a2", "a1")
            .map(s -> {
                System.out.print(s + " ");
                return s.toUpperCase();
            })
//            .sorted((o1, o2) -> 0)
//            .distinct(/*(o1, o2) -> 0*/)
//            .peek(s -> {
//                System.out.print("$=" + s + " ");
//            })
            .dropWhile(s -> {
                System.out.print(":=" + s + " ");
                return !s.equalsIgnoreCase("a3");
            }).collect(Collectors.toList());
        System.out.println();
        log.info("Итого - {}", list);
    }

    private static Integer counter = 0;

    @Test
    void sampleThread() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
//            counter = 0;
            Thread t1 = new Thread(() -> counter++);
            Thread t2 = new Thread(() -> counter++);
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println(counter);
        }
    }
}
