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

import java.util.List;

/**
 * Interface of Barcode Generator
 *
 * @author Vladimir Laskin
 * @since <pre>25.11.2020</pre>
 */
public interface BarcodeGenerator {

    /**
     * Generate of list S10 barcodes by given range.
     *
     * @param prefix  container of S10 barcodes
     * @param start   range start
     * @param end     range end
     * @param postfix postfix (for russia post == "RU")
     * @return list of S10 barcodes
     */
    List<String> generateS10Barcodes(String prefix, int start, int end, String postfix);
}
