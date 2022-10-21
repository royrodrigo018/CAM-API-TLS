package com.dxc.imda.cam.tls.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dxc.imda.cam.common.model.Email;
import com.dxc.imda.cam.common.model.EnterpriseUser;
import com.dxc.imda.cam.common.model.ExtensionCamUser;
import com.dxc.imda.cam.common.model.Group;
import com.dxc.imda.cam.common.model.Manager;
import com.dxc.imda.cam.common.model.Meta;
import com.dxc.imda.cam.common.model.Name;
import com.dxc.imda.cam.common.model.UpdateInfo;
import com.dxc.imda.cam.common.model.UserInfo;
import com.dxc.imda.cam.common.util.DateUtil;
import com.dxc.imda.cam.tls.model.UserProfile;

@Component
public class TlsUserInfoMapper {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String PRIVILEGED_USER = "ADMIN";
	
	public UserInfo convertUserToJSON(UserProfile userProfile) {
		UserInfo userInfo = new UserInfo();
		List<String> schemas = getUserSchemas();		
		try {
			userInfo.setId(userProfile.getUserId());
			userInfo.setExternalId(userProfile.getUserId());		
			userInfo.setUserName(userProfile.getUserName());
			userInfo.setDisplayName(userProfile.getUserName());	
			
			if ("A".equalsIgnoreCase(userProfile.getStatus())){
				userInfo.setActive(true);
			}else {
				userInfo.setActive(false);
			}
					
			userInfo.setProfileUrl("");
			userInfo.setUserType("");
			
			Meta meta = getMeta(userProfile);
			Name name = getName(userProfile);		
			List<Email> emails = getEmails(userProfile);		
			List<Group> groups = getGroups(userProfile);			
			EnterpriseUser enterpriseUser = getEnterpriseUser(userProfile);			
			ExtensionCamUser extensionCamUser = getExtensionCamUser(userProfile);
			
			userInfo.setTitle("");
	
			userInfo.setExtensionCamUser(extensionCamUser);
			userInfo.setEnterpriseUser(enterpriseUser);		
			userInfo.setGroups(groups);
			userInfo.setEmails(emails);
			userInfo.setName(name);
			userInfo.setMeta(meta);		
			userInfo.setSchemas(schemas);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			userInfo = null;
		}
		return userInfo;		
	}
	
	public UpdateInfo convertUpdateInfoToJSON(UserProfile userProfile) {
		UpdateInfo updateInfo = new UpdateInfo();
		try {
			updateInfo.setUserId(userProfile.getUserId());
			updateInfo.setGroupId(userProfile.getRoleName());				
			updateInfo.setStatus(userProfile.getStatus());
			updateInfo.setLastUpdBy(userProfile.getLastUpdBy()); //TODO
			DateUtil dateUtil = new DateUtil();
			if (userProfile.getLastUpdDate() != null) {
				updateInfo.setLastUpdDate(dateUtil.convertDateToUTC(userProfile.getLastUpdDate()));
			}
			updateInfo.setBlnSuccess(true);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			updateInfo = null;
		}
		return updateInfo;		
	}
	
	private List<String> getUserSchemas(){
		List<String> schemas = new ArrayList<String>();
		schemas.add("urn:ietf:params:scim:schemas:core:2.0:User");
		schemas.add("urn:ietf:params:scim:schemas:extension:enterprise:2.0:User");
		schemas.add("urn:ietf:params:scim:schemas:extension:cam:2.0:User");
		return schemas;
	}
	
	private Meta getMeta(UserProfile userProfile) {
		Meta meta = new Meta();
		meta.setResourceType("User");
		meta.setCreated("");
		meta.setLastModified("");
		DateUtil dateUtil = new DateUtil();
		if (userProfile.getCreatedDate() != null) {
			meta.setCreated(dateUtil.convertDateToUTC(userProfile.getCreatedDate()));
		}
		if (userProfile.getLastUpdDate() != null) {
			meta.setLastModified(dateUtil.convertDateToUTC(userProfile.getLastUpdDate()));
		}	
		return meta;
	}
	
	private Name getName(UserProfile userProfile) {
		Name name = new Name();
		name.setFormatted("");
		name.setFamilyName("");
		name.setGivenName("");
		return name;
	}
	
	// TODO: need to check how many emails available
	private List<Email> getEmails(UserProfile user){
		List<Email> emails = new ArrayList<Email>();
		Email email = new Email();
		email.setValue("");
		email.setType("");
		email.setPrimary(false); // TODO: set to true or false
		if (user.getEmail() != null) {
			email.setValue(user.getEmail());
			email.setType("work");
			email.setPrimary(true); // TODO: set to true or false
			emails.add(email);
		}		
		return emails;
	}
	
	private List<Group> getGroups(UserProfile userProfile){
		List<Group> groups = new ArrayList<Group>();		
		Group group = new Group();
		group.setValue(userProfile.getRoleName());
		group.set$ref("");
		group.setDisplay(userProfile.getRoleDesc());		
		groups.add(group);			
		return groups;
	}
	
	private Manager getManager(UserProfile userProfile) {
		Manager manager = new Manager();
		manager.setValue("");
		manager.set$ref("");
		manager.setDisplayName("");
		return manager;		
	}
	
	private EnterpriseUser getEnterpriseUser(UserProfile userProfile) {
		Manager manager = getManager(userProfile);
		EnterpriseUser enterpriseUser = new EnterpriseUser();
		enterpriseUser.setOrganization("");
		enterpriseUser.setDivision("");
		enterpriseUser.setDepartment("");
		enterpriseUser.setManager(manager);
		return enterpriseUser;		
	}
	
	private ExtensionCamUser getExtensionCamUser(UserProfile userProfile) {
		ExtensionCamUser extensionCamUser = new ExtensionCamUser();
		extensionCamUser.setLastLogin("");
		extensionCamUser.setLastPasswordChanged("");
		DateUtil dateUtil = new DateUtil();
		if (userProfile.getLastAccessDate() != null) {
			extensionCamUser.setLastLogin(dateUtil.convertDateToUTC(userProfile.getLastAccessDate()));
		}
		if (isPrivileged(userProfile)) {
			extensionCamUser.setIsPrivileged(true); // TODO: set to true or false
		}else {
			extensionCamUser.setIsPrivileged(false); // TODO: set to true or false
		}		
		return extensionCamUser;		
	}
	
	private boolean isPrivileged(UserProfile userProfile) {
		boolean isPrivileged = false;
		if (userProfile.getRoleName() != null && 
				userProfile.getRoleName().equalsIgnoreCase(PRIVILEGED_USER)) {
			isPrivileged = true;
		}
		return isPrivileged;		
	}
}
