/*
 *  @(#)MyCode.java  last: 09.06.2023
 *
 * Title: LG prototype for kafka + akka (simple or spring)
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package im;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

// package whatever; // don't place package name!
/*
  Разрабатываем публичную библиотеку. API  может содержать 1 или более методов.
  Целевая версия Java 1.7.
  Необходимо написать функцию, которая на вход принимает набор однотипных данных и возвращает строку,
  состоящую из строкового представления полученных элементов, которые могут быть разделены опциональным
  набором символов (разделителем). Порядок элементов в строке соотвествует порядку во входном наборе.

(1, 2, 3) -> “1<>2<>3”
(“Abc”, “efg”) -> “Abc/efg”
(1,2) -> “12”
*/
@Slf4j
class MyCode {
    public interface Converter<T> {
        String toString(T args);
    }

    public static class ConverterInteger implements Converter<Integer> {
        public String toString(Integer value) {
            return Integer.toString(value);
        }
    }

    public <T> String getIntegerArray2String(final String del, final Converter<T> converter, final Iterator<T> args) {
        final StringBuilder sb = new StringBuilder();
        boolean isNext = false;
        while (args.hasNext()) {
            final T arg = args.next();
            if (arg != null) {
                if (isNext && del != null) {
                    sb.append(del);
                }
                sb.append(converter.toString(arg));
                isNext = true;
            }
        }
        return sb.toString();
    }

    public String getIntegerArray2String(final String del, final Object ... args) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            final Object arg = args[i];
            if (arg != null) {
                if (i > 0 && del != null) {
                    sb.append(del);
                }
                sb.append(arg);
            }
        }
        return sb.toString();
    }

    public static void main (String[] args) {
        final MyCode myCode = new MyCode();
        final List<Integer> list = Arrays.asList(1, 2, null, 4);
        log.info(myCode.getIntegerArray2String("<->", new ConverterInteger(), list.iterator()));
        log.info(myCode.getIntegerArray2String("<>", 1, null, 3));
        log.info(myCode.getIntegerArray2String("/", "Abc", "efg"));
        log.info(myCode.getIntegerArray2String(null, 1, 2));
        log.info(myCode.getIntegerArray2String(null));
    }
}
