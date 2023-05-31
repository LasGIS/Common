/*
 *  @(#)MyCallable.java  last: 26.05.2023
 *
 * Title: LG prototype for kafka + akka (simple or spring)
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * The Class MyCallable definition.
 *
 * @author VLaskin
 * @since 26.05.2023 : 12:15
 */
@Slf4j
public class MyCallable implements Callable<Integer> {
    private final List<Integer> list;

    public MyCallable(final List<Integer> list) {
        this.list = list;
    }

    @Override
    public Integer call() throws Exception {
        Integer acc = 0;
        for (Integer integer : list) {
            log.info("before {} ", integer);
            Thread.sleep(100);
            acc += integer;
            log.info("after {} ", integer);
        }
        return acc;
    }
}


