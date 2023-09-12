/*
 *  @(#)PersonRelationRepository.java  last: 12.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.model.entity.PersonRelationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRelationRepository extends ReactiveCrudRepository<PersonRelationEntity, Void> {
    @Query("DELETE FROM person_relation WHERE person_id = :personId OR person_to_id = :personId")
    Mono<Void> deleteAllForPersonId(final Long personId);
}
