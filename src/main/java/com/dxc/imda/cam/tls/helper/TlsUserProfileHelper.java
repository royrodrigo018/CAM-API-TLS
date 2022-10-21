package com.dxc.imda.cam.tls.helper;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dxc.imda.cam.tls.model.UserProfile;

@Component
public class TlsUserProfileHelper {
	
	public UserProfile getUserProfile(Map<String, Object> mapRow) {		
		UserProfile userProfile = new UserProfile();
		if (mapRow.get("USER_ID") != null) {
	    	userProfile.setUserId((String) mapRow.get("USER_ID"));
	    	userProfile.setUserName((String) mapRow.get("USER_NAME"));
	    	userProfile.setEmail((String) mapRow.get("USER_TYPE"));
	    	userProfile.setEmail((String) mapRow.get("EMAIL"));
	    	userProfile.setStatus((String) mapRow.get("STATUS"));
	    	
	    	userProfile.setRoleName((String) mapRow.get("USER_GP"));
	    	userProfile.setRoleDesc((String) mapRow.get("USERGP_NAME"));
	    	userProfile.setLastAccessDate((Date) mapRow.get("DATE_LAST_ACCESS"));

	    	userProfile.setCreatedBy(String.valueOf(mapRow.get("CREATED_BY")));
	    	userProfile.setCreatedDate((Date) mapRow.get("DATE_CREATED"));
	    	userProfile.setLastUpdBy(String.valueOf(mapRow.get("UPDATED_BY")));
	    	userProfile.setLastUpdDate((Date) mapRow.get("UPDATED_DATE")); 
		}
    	return userProfile;
	}
}
