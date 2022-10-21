package com.dxc.imda.cam.tls.model;

import java.util.Date;

/** SC_USER_PROFILE Table **/
public class UserProfile {

	private String userId;
	private String userName;
	//private String userType;
	private String email;
	private String status;
	
	private String roleName;
	private String roleDesc;
	
	private Date lastAccessDate;
	private String createdBy;
	private Date createdDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	private Date reactivateDate;
	private Date deactivateDate;

	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



//	public String getUserType() {
//		return userType;
//	}
//
//
//
//	public void setUserType(String userType) {
//		this.userType = userType;
//	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



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



	public Date getLastAccessDate() {
		return lastAccessDate;
	}



	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
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



	public Date getReactivateDate() {
		return reactivateDate;
	}



	public void setReactivateDate(Date reactivateDate) {
		this.reactivateDate = reactivateDate;
	}



	public Date getDeactivateDate() {
		return deactivateDate;
	}



	public void setDeactivateDate(Date deactivateDate) {
		this.deactivateDate = deactivateDate;
	}

	public UserProfile() {
		super();
	}

	public UserProfile(UserProfile userProfile) {
		super();
		this.userId = userProfile.userId;
		this.userName = userProfile.userName;
		//this.userType = userProfile.userType;
		this.email = userProfile.email;
		this.status = userProfile.status;
		this.roleName = userProfile.roleName;
		this.lastAccessDate = userProfile.lastAccessDate;
		this.createdBy = userProfile.createdBy;
		this.createdDate = userProfile.createdDate;
		this.lastUpdBy = userProfile.lastUpdBy;
		this.lastUpdDate = userProfile.lastUpdDate;
	}

	@Override
	public String toString() {
		return "UserProfile [userId=" + userId + ", userName=" + userName + ", email="
				+ email + ", status=" + status + ", roleName=" + roleName + ", roleDesc=" + roleDesc
				+ ", lastAccessDate=" + lastAccessDate + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", lastUpdBy=" + lastUpdBy + ", lastUpdDate=" + lastUpdDate + ", reactivateDate=" + reactivateDate
				+ ", deactivateDate=" + deactivateDate + "]";
	}	
}
