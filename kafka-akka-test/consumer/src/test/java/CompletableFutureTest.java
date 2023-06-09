/*
 *  @(#)CompletableFutureTest.java  last: 09.06.2023
 *
 * Title: LG prototype for kafka + akka (simple or spring)
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Class CompletableFutureTest definition.
 *
 * @author VLaskin
 * @since 23.05.2023 : 18:47
 */
@Slf4j
public class CompletableFutureTest {
    private static final List<List<Integer>> SOURCE_LISTS = List.of(
        List.of(1, 3, 5, 7, 9),
        List.of(2, 4, 6, 8, 10),
        List.of(3, 22, 33, 44, 55),
        List.of(4, 2, 6, 1, 9, 10, 45)
    );
    private static final int NUM_THREADS = SOURCE_LISTS.size();
    private static final Integer REAL_SUM = Long.valueOf(SOURCE_LISTS.stream().flatMap(Collection::stream)
        .mapToInt(value -> value).summaryStatistics().getSum()).intValue();

    @Test
    public void hashCodeCollision() {
        final ExecutorService pool = Executors.newFixedThreadPool(200);
        //CompletableFuture.supplyAsync()
    }


    @Test
    void callableTest() {
        final ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        final Set<Future<Integer>> futures = new HashSet<>(NUM_THREADS);
        for (List<Integer> list : SOURCE_LISTS) {
            final Future<Integer> future = pool.submit(new MyCallable(list));
            futures.add(future);
        }
        final CompletableFuture<Integer> cFuture = CompletableFuture.supplyAsync(() -> {
            Integer sum = 0;
            boolean isDone;
            try {
                do {
                    isDone = true;
                    for (Future<Integer> future : futures) {
                        isDone = isDone && future.isDone();
                    }
                    Thread.sleep(10);
                    log.info("isDone = {}", isDone);
                } while (!isDone);
                for (Future<Integer> future : futures) {
                    sum += future.get();
                }
            } catch (InterruptedException | ExecutionException ex) {
                log.error(ex.getMessage(), ex);
                throw new RuntimeException(ex);
            }
            return sum;
        }).thenApplyAsync(sum -> {
            log.info("sum = {}, REAL SUM = {}", sum, REAL_SUM);
            Assertions.assertEquals(REAL_SUM, sum);
            return sum;
        });
        cFuture.join();
    }
}
