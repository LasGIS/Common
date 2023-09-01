/*
 *  @(#)RoleRepository.java  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.entity.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<RoleEntity, Long> {
}