package com.dxc.imda.cam.tls.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dxc.imda.cam.common.model.ExtensionCamGroup;
import com.dxc.imda.cam.common.model.GroupInfo;
import com.dxc.imda.cam.common.model.Member;
import com.dxc.imda.cam.common.model.Meta;
import com.dxc.imda.cam.common.model.UpdateInfo;
import com.dxc.imda.cam.common.util.DateUtil;
import com.dxc.imda.cam.tls.model.UserProfile;
import com.dxc.imda.cam.tls.model.UserRole;
import com.dxc.imda.cam.tls.model.UserRoleAccess;

@Component
public class TlsGroupInfoMapper {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public GroupInfo convertUserRoleToJSON(UserRole userRole) {
		GroupInfo groupInfo = new GroupInfo();
		List<String> schemas = getGroupSchemas();
		try {
			groupInfo.setId(userRole.getRoleName());
			groupInfo.setExternalId(userRole.getRoleName());
			groupInfo.setDisplayName(userRole.getRoleName());

			Meta meta = getMeta(userRole);
			List<Member> members = getMemberList(userRole);
			ExtensionCamGroup extensionCamGroup = getExtensionCamGroup(userRole);

			groupInfo.setExtensionCamGroup(extensionCamGroup);
			groupInfo.setMembers(members);
			groupInfo.setMeta(meta);
			groupInfo.setSchemas(schemas); //TODO: schemas
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			groupInfo = null;
		}
		return groupInfo;
	}

	public UpdateInfo convertUpdateInfoToJSON(UserRole userRole) {
		UpdateInfo updateInfo = new UpdateInfo();
		try {
			updateInfo.setGroupId(userRole.getRoleName());
			updateInfo.setStatus(userRole.getStatus());
			updateInfo.setLastUpdBy(userRole.getLastUpdBy()+"");
			updateInfo.setLastUpdDate(userRole.getLastUpdDate().toString());
			updateInfo.setBlnSuccess(true);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			updateInfo = null;
		}
		return updateInfo;
	}

	private List<String> getGroupSchemas(){
		List<String> schemas = new ArrayList<String>();
		schemas.add("urn:ietf:params:scim:schemas:core:2.0:Group");
		schemas.add("urn:ietf:params:scim:schemas:extension:cam:2.0:Group");
		return schemas;
	}

	private Meta getMeta(UserRole userRole) {
		Meta meta = new Meta();
		meta.setResourceType("Group");
		meta.setCreated("");
		meta.setLastModified("");
		DateUtil dateUtil = new DateUtil();
		if (userRole.getCreatedDate() != null) {
			meta.setCreated(dateUtil.convertDateToUTC(userRole.getCreatedDate()));
		}
		if (userRole.getLastUpdDate() != null) {
			meta.setLastModified(dateUtil.convertDateToUTC(userRole.getLastUpdDate()));
		}
		return meta;
	}

	private List<Member> getMemberList(UserRole userRole){
		List<Member> members = new ArrayList<>();
		for (UserProfile userProfile:  userRole.getUserProfiles()) {
			if ("A".equalsIgnoreCase(userProfile.getStatus())){	
				Member member = new Member();
				member.setValue(userProfile.getUserId());
				member.set$ref("");
				member.setType("User");
				members.add(member);
			}
		}		
		return members;
	}

	private ExtensionCamGroup getExtensionCamGroup(UserRole userRole) {
		StringBuilder sb = new StringBuilder();
		sb.append(userRole.getRoleName()+" "+ "Role");
		sb.append("=");
		String groupAccessRightInfo = "";		
		logger.info("userRole.getUserRoleAccesses(): " + userRole.getUserRoleAccessSet());
		if (userRole.getUserRoleAccessSet() != null) {
			for (UserRoleAccess userRoleAccess: userRole.getUserRoleAccessSet()) {
				sb.append(userRoleAccess.getUserRoleAccessDesc());
				sb.append(", ");
			}
			//logger.info("sb.toString(): " + sb.toString());
			//logger.info("sb.toString().endsWith(\",\"): " + sb.toString().trim().endsWith(","));
			if (sb.toString().trim().endsWith(",")) {
				groupAccessRightInfo = sb.toString().trim().substring(0, sb.toString().trim().length() - 1);
				//logger.info("groupAccessRightInfo: " + groupAccessRightInfo);
			}
		}
		//TODO: remove last character (,)
		ExtensionCamGroup extensionCamGroup = new ExtensionCamGroup();
		extensionCamGroup.setGroupAccessRightInfo(groupAccessRightInfo);
		return extensionCamGroup;
	}
}
