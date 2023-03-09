/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информация о возврате посылки
 *
 * @author Vladimir Laskin
 * @since <pre>21.11.2022</pre>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ReturnParcelInfo {
    /** Оригинальный ШПИ */
    private String originalBarcode;
    /** Возвратный ШПИ */
    private String returnedBarcode;
}
