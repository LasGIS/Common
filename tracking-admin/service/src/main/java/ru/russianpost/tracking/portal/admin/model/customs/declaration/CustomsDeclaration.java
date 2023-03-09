/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.customs.declaration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customs Declaration DTO
 *
 * @author vlaskin
 * @since <pre>16.09.2021</pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CustomsDeclaration {
    private String shipmentId;
    private long creationTimestamp;
    private int attributesVersionNumber;
    private long insertTimestamp;
    private long itmattAcceptanceTimestamp;
    private long itmattReleasedByCustomsTimestamp;
    private long kafkaTimestamp;
    private String rtm30ContainerName;
    private String declaration;
}
