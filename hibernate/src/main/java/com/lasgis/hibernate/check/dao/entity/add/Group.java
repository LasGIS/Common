/*
 *  @(#)Group.java  last: 06.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity.add;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class Group definition.
 *
 * @author VLaskin
 * @since 06.06.2023 : 16:52
 */
@Entity
@Table(name = "groups")
public class Group {
    private long id;
    private String name;
    private Set<UserGroup> userGroups = new HashSet<UserGroup>();

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    public long getId() {
        return this.id;
    }
    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "group")
    public Set<UserGroup> getUserGroups() {
        return this.userGroups;
    }
    public void setUserGroups(final Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
    public void addUserGroup(final UserGroup userGroup) {
        this.userGroups.add(userGroup);
    }
}