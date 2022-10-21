package com.dxc.imda.cam.tls.model;

/*
 * User Roles Access / GROUP
 */
/** SC_FUNCTION_ACCESS Table **/
public class UserRoleAccess {

	private String roleName;
	private String userRoleAccessName;
	private String userRoleAccessDesc;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserRoleAccessName() {
		return userRoleAccessName;
	}
	public void setUserRoleAccessName(String userRoleAccessName) {
		this.userRoleAccessName = userRoleAccessName;
	}
	public String getUserRoleAccessDesc() {
		return userRoleAccessDesc;
	}
	public void setUserRoleAccessDesc(String userRoleAccessDesc) {
		this.userRoleAccessDesc = userRoleAccessDesc;
	}
	
	@Override
	public String toString() {
		return "UserRole [roleName=" + roleName + ", userRoleAccessName=" + userRoleAccessName + ", userRoleAccessDesc="
				+ userRoleAccessDesc + "]";
	}	
}
