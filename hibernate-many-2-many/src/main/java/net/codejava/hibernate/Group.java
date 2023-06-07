/*
 *  @(#)Group.java  last: 07.06.2023
 *
 * Title: LG prototype for hibernate ManyToMany
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package net.codejava.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

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
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "group")
	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> groups) {
		this.userGroups = groups;
	}
	
	public void addUserGroup(UserGroup userGroup) {
		this.userGroups.add(userGroup);
	}

}