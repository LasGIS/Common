/*
 *  @(#)UserDtoRepository.java  last: 15.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.model.UserDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserDtoRepository extends org.springframework.data.repository.Repository<UserDto, Long> {
    @Query(value = "SELECT"
        + "     usr.umusr_user_id        AS user_id,"
        + "     usr.umusr_login          AS login,"
        + "     usr.umusr_name           AS name,"
        + "     usr.umusr_password       AS password,"
        + "     usr.umusr_archived       AS archived,"
        + " ARRAY_AGG(rol.umrle_role_id) AS roles"
        + "   FROM um_user usr"
        + "      INNER JOIN um_user_role rol ON usr.umusr_user_id = rol.umusr_user_id"
        + "  GROUP BY user_id, login, name, password, archived"
        + "  ORDER BY user_id")
    Flux<UserDto> getAllUserDto();
}