/*
 *  @(#)UserDto.java  last: 01.05.2023
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
 * The Class User definition.
 *
 * @author Vladimir Laskin
 * @since 30.04.2023 : 21:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmUser {
    @Id()
    private Long umusrUserId;
    private String umusrLogin;
    private String umusrName;
    private String umusrPassword;
    /*
        @Reference(to = UmRole.class)
        private UmRole UmRole;
    */
    private Boolean umusrArchived;
}
