/*
 *  @(#)PersonRepository.java  last: 05.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.hibernate.check.dao.repository;

import com.lasgis.hibernate.check.dao.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository для объекта персона
 *
 * @author VLaskin
 * @since 05.06.2023 : 14:11
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}
