/*
 *  @(#)UserRoleRepository.java  last: 11.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.model.entity.UserRoleEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRoleRepository extends ReactiveCrudRepository<UserRoleEntity, Long> {
    @Query("DELETE FROM user_role WHERE user_id = :userId")
    Mono<Void> deleteByUserId(Long userId);
}
