package com.dxc.imda.cam.tls.helper;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dxc.imda.cam.tls.model.UserRole;
import com.dxc.imda.cam.tls.model.UserRoleAccess;

@Component
public class TlsUserRoleHelper {

	public UserRole getUserRole(Map<String, Object> mapRow) {
		UserRole userRole = new UserRole();
		if (mapRow.get("USER_GP") != null) {
			userRole.setRoleName((String) mapRow.get("USER_GP"));
			userRole.setRoleDesc((String) mapRow.get("USERGP_NAME"));
			userRole.setStatus((String) mapRow.get("STATUS"));
//			userRole.setCreatedBy(((String) mapRow.get("CREATED_BY")));
//			userRole.setCreatedDate((Date) mapRow.get("DATE_CREATED"));
			userRole.setLastUpdBy(((String) mapRow.get("UPDATED_BY")));
			userRole.setLastUpdDate((Date) mapRow.get("DATE_UPDATED"));
		}
		return userRole;
	}
	
	public UserRoleAccess getUserRoleAccess(Map<String, Object> mapRow) {
		UserRoleAccess userRoleAccess = new UserRoleAccess();
		if (mapRow.get("USER_GP") != null) {
			userRoleAccess.setRoleName((String) mapRow.get("USER_GP"));
			userRoleAccess.setUserRoleAccessName((String) mapRow.get("FUNCTION_ID"));
			userRoleAccess.setUserRoleAccessDesc((String) mapRow.get("FUNCTION_DESC"));			
		}
		return userRoleAccess;
	}
}
