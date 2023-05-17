/*
 *  @(#)UmUserRole.java  last: 17.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * The Class UmUserRole definition.
 *
 * @author VLaskin
 * @since 17.05.2023 : 15:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmUserRole {
    @Id()
    private Long umurlUserRoleId;
    private Long umusrUserId;
    private String umrleRoleId;
}
