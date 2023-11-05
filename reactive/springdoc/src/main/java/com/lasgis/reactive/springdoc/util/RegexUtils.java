/*
 *  @(#)RegexUtils.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.springdoc.util;

import static com.lasgis.reactive.springdoc.util.LangUtil.ifNotNull;

public final class RegexUtils {
    private RegexUtils() {
    }

    public static String camelToSnake(String camelStr) {
        return ifNotNull(camelStr, s -> s.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase());
    }
}
