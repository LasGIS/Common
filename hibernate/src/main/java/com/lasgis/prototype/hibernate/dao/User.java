/*
 *  @(#)User.java  last: 07.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.hibernate.dao;

import com.lasgis.prototype.hibernate.entity.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Таблица пользователей
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long userId;
    private String login;
    private String name;
    private String password;
    private Boolean archived;
    private List<UserRole> roles;
}
