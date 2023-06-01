/*
 *  @(#)UserRepository.java  last: 01.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.hibernate.check.dao.repository;

import com.lasgis.hibernate.check.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *  DAO для объекта роль
 *
 * @author eugene bulanov
 * @since <pre>3/18/19</pre>
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}
