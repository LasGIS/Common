/*
 *  @(#)MathUtils.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.springdoc.util;

public class MathUtils {
    public static long random(long from, long to) {
        return from + (long) (Math.random() * (to - from));
    }
}
