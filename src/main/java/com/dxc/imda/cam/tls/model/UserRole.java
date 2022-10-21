package com.dxc.imda.cam.tls.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/*
 * Main User Roles / GROUP
 */
/** SC_USER_GP Table **/
public class UserRole {

	private String roleName;
	private String roleDesc;
	private String status;
	private String createdBy;
	private Date createdDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	private List<UserProfile> userProfiles;

	private Set<UserRoleAccess> userRoleAccessSet;
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public List<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(List<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}
	
	public Set<UserRoleAccess> getUserRoleAccessSet() {
		return userRoleAccessSet;
	}

	public void setUserRoleAccessSet(Set<UserRoleAccess> userRoleAccessSet) {
		this.userRoleAccessSet = userRoleAccessSet;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRole other = (UserRole) obj;
		return Objects.equals(roleName, other.roleName);
	}

	@Override
	public String toString() {
		return "UserRole [roleName=" + roleName + ", roleDesc=" + roleDesc + ", status=" + status + ", lastUpdBy="
				+ lastUpdBy + ", lastUpdDate=" + lastUpdDate + "]";
	}	
}
