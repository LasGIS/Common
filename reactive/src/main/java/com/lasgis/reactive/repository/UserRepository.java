/*
 *  @(#)UserRepository.java  last: 04.09.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.entity.UserEntity;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Repository
@NonNullApi
public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
    @Override
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
    Flux<UserEntity> findAll();

    @Override
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
    Mono<UserEntity> findById(@NonNull Long id);

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
    Mono<UserEntity> findByLogin(@NonNull String login);
}
