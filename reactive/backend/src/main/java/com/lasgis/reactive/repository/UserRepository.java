/*
 *  @(#)UserRepository.java  last: 11.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.model.entity.UserEntity;
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
            ARRAY_AGG(rol.role ORDER BY rol.role) AS roles
          FROM "user" usr
             LEFT JOIN user_role rol ON usr.user_id = rol.user_id
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
            ARRAY_AGG(rol.role ORDER BY rol.role) AS roles
          FROM "user" usr
             LEFT JOIN user_role rol ON usr.user_id = rol.user_id
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
             ARRAY_AGG(rol.role ORDER BY rol.role) AS roles
           FROM "user" usr
              LEFT JOIN user_role rol ON usr.user_id = rol.user_id
          WHERE usr.login = :login
          GROUP BY usr.user_id, login, name, password, archived""")
    Mono<UserEntity> findByLogin(@NonNull String login);
}
