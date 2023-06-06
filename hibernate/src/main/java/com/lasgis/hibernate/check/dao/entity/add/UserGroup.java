/*
 *  @(#)UserGroup.java  last: 06.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity.add;

import com.lasgis.hibernate.check.dao.entity.UserEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * The Class UserGroup definition.
 *
 * @author VLaskin
 * @since 06.06.2023 : 16:56
 */
@Entity
@Table(name = "users_groups", schema = "hiber")
public class UserGroup {
    private long id;
    private UserEntity user;
    private Group group;

    // additional fields
    private boolean activated;
    private Date registeredDate;

    @Id
    @GeneratedValue
    @Column(name = "user_group_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    public Group getGroup() {
        return this.group;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public void setActivated(final boolean activated) {
        this.activated = activated;
    }


    @Column(name = "registered_date")
    @Temporal(TemporalType.DATE)
    public Date getRegisteredDate() {
        return this.registeredDate;
    }

    public void setRegisteredDate(final Date registeredDate) {
        this.registeredDate = registeredDate;
    }
}
