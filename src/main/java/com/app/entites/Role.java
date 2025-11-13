package com.app.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Data
@Table(name = "roles")
//@NoArgsConstructor
//@AllArgsConstructor
public class Role {

	@Id
	private Long roleId;
	private String roleName;
	public String getRoleName() {
		// TODO Auto-generated method stub
		return this.roleName;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	
}
