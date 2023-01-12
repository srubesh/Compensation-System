package com.compensationplan.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_roles")
public class Role {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(columnDefinition = "BIGINT(20) NOT NULL UNIQUE KEY auto_increment")
	private String roleId;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
	
	private String description;

	public Role() {

	}

	public Role(ERole name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [id=" + roleId + ", name=" + name + ", desription=" + description + "]";
	}
	
}