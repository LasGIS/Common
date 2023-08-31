/*
 *  @(#)UserDtoRepository.java  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.model.UserDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserDtoRepository extends ReactiveCrudRepository<UserDto, Long> {
    @Query(value = """
        SELECT
            usr.user_id,
            usr.login,
            usr.name,
            usr.password,
            usr.archived,
            ARRAY_AGG(rol.role_id) AS roles
          FROM "user" usr
             INNER JOIN user_role rol ON usr.user_id = rol.user_id
         GROUP BY usr.user_id, login, name, password, archived
         ORDER BY user_id""")
    Flux<UserDto> getAllUserDto();

    @Query(value = """
        SELECT
            usr.user_id,
            usr.login,
            usr.name,
            usr.password,
            usr.archived,
            ARRAY_AGG(rol.role_id) AS roles
          FROM "user" usr
             INNER JOIN user_role rol ON usr.user_id = rol.user_id
         WHERE usr.user_id = :id
         GROUP BY usr.user_id, login, name, password, archived""")
    Mono<UserDto> getUserDtoById(Long id);

    @Query(value = """
        SELECT
             usr.user_id,
             usr.login,
             usr.name,
             usr.password,
             usr.archived,
             ARRAY_AGG(rol.role_id) AS roles
           FROM "user" usr
              INNER JOIN user_role rol ON usr.user_id = rol.user_id
          WHERE usr.login = :login
          GROUP BY usr.user_id, login, name, password, archived""")
    Mono<UserDto> getUserDtoByLogin(String login);
}
