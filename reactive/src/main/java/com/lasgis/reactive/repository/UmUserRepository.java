package com.lasgis.reactive.repository;

import com.lasgis.reactive.entity.UmUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UmUserRepository extends ReactiveCrudRepository<UmUser, Long> {
}
