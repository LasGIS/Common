/*
 *  @(#)UserRepository.java  last: 07.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.prototype.hibernate.repository;

import com.lasgis.prototype.hibernate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository для объекта пользователь
 *
 * @author VLaskin
 * @since <pre>4/5/19</pre>
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}
