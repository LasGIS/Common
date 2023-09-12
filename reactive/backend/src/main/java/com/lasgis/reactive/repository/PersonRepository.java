/*
 *  @(#)PersonRepository.java  last: 12.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.model.entity.PersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Long> {
}
