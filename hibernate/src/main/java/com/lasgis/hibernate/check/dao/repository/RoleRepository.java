/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package com.lasgis.hibernate.check.dao.repository;

import com.lasgis.hibernate.check.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  DAO для объекта роль
 *
 * @author eugene bulanov
 * @since <pre>3/18/19</pre>
 */
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    /**
     * Список ролей
     * @return List<RoleEntity>
     */
    List<RoleEntity> findAll();
}
