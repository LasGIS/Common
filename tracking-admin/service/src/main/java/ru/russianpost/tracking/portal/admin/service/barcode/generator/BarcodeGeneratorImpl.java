/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.barcode.generator;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Barcode Generator
 *
 * @author Vladimir Laskin
 * @since <pre>25.11.2020</pre>
 */
@Service
public class BarcodeGeneratorImpl implements BarcodeGenerator {

    private final int[] s10InternationalBarcodeChecksumWeights = {8, 6, 4, 2, 3, 5, 9, 7};

    @Override
    public List<String> generateS10Barcodes(String prefix, int start, int end, String postfix) {
        return IntStream.range(start, end + 1).mapToObj(value -> generateS10Barcode(prefix, value, postfix))
                .collect(Collectors.toList());
    }

    private String generateS10Barcode(final String prefix, final int value, final String postfix) {
        final StringBuilder sb = new StringBuilder();
        final String zeroPaddedBarcodeId = String.format("%08d", value);
        sb.append(prefix).append(zeroPaddedBarcodeId);

        int s = 0;
        for (int i = 0; i < zeroPaddedBarcodeId.length(); i++) {
            s += Character.digit(zeroPaddedBarcodeId.charAt(i), 10) * s10InternationalBarcodeChecksumWeights[i];
        }

        int c = 11 - (s % 11);

        if (c == 10) {
            c = 0;
        } else if (c == 11) {
            c = 5;
        }

        return sb.append(c).append(postfix).toString();
    }
}
