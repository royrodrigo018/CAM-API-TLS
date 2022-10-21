package com.dxc.imda.cam.tls.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dxc.imda.cam.common.model.GroupInfo;
import com.dxc.imda.cam.tls.dao.TlsUserRoleDao;
import com.dxc.imda.cam.tls.mapper.TlsGroupInfoMapper;
import com.dxc.imda.cam.tls.model.UserProfile;
import com.dxc.imda.cam.tls.model.UserRole;
import com.dxc.imda.cam.tls.model.UserRoleAccess;
import com.dxc.imda.cam.tls.service.TlsUserProfileService;
import com.dxc.imda.cam.tls.service.TlsUserRoleAccessService;
import com.dxc.imda.cam.tls.service.TlsUserRoleService;

@Service
public class TlsUserRoleServiceImpl implements TlsUserRoleService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TlsUserRoleDao tlsUserRoleDao;
	
	@Autowired
	private TlsUserProfileService tlsUserProfileService;
	
	@Autowired
	private TlsUserRoleAccessService tlsUserRoleAccessService;

	@Autowired
	private TlsGroupInfoMapper tlsGroupInfoMapper;

	@Override
	public GroupInfo findByRoleName(String roleName) {
		GroupInfo groupInfo = new GroupInfo();
		try {
			UserRole userRole = tlsUserRoleDao.findByRoleName(roleName);
			logger.info("findByRoleName userRole: " + userRole);
			if (userRole != null) {
				List<UserProfile> userProfiles = tlsUserProfileService.findByRoleName(userRole.getRoleName());
				logger.info("findByRoleName userProfiles.size(): " + userProfiles.size());
				userRole.setUserProfiles(userProfiles);	
				
				Set<UserRoleAccess> userRoleAccessSet = tlsUserRoleAccessService.getUserRoleAccess(userRole.getRoleName());
				logger.info("findByRoleName userRoleAccessSet.size(): " + userRoleAccessSet.size());
				userRole.setUserRoleAccessSet(userRoleAccessSet);
				
				groupInfo = getGroupInfo(userRole);
			}				
		} catch (Exception e){
			e.printStackTrace();
			groupInfo = null;
		}
		return groupInfo;
	}

	@Override
	public Long countAll() {
		return tlsUserRoleDao.countAll();
	}

	@Override
	public Long countByRoleNameEquals(String roleName) {
		return tlsUserRoleDao.countByRoleNameEquals(roleName);
	}

	@Override
	public Long countByRoleNameContaining(String roleName) {
		return tlsUserRoleDao.countByRoleNameContaining(roleName);
	}

	@Override
	public Long countByRoleDescEquals(String roleDesc) {
		return tlsUserRoleDao.countByRoleDescEquals(roleDesc);
	}

	@Override
	public Long countByRoleDescContaining(String roleDesc) {
		return tlsUserRoleDao.countByRoleDescContaining(roleDesc);
	}

	@Override
	public Page<GroupInfo> findAll(Pageable pageable) {
		List<GroupInfo> groupInfos = new ArrayList<>();
		try {
			Page<UserRole> pagedUserRoles = tlsUserRoleDao.findAll(pageable);
			//logger.info("findAll pagedUserRoles: " + pagedUserRoles);
			List<UserRole> userRoles = pagedUserRoles.getContent();
			logger.info("findAll userRoles.size(): " + userRoles.size());
			groupInfos = getGroupInfos(userRoles);
			logger.info("findAll groupInfos.size(): " + groupInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			groupInfos = null;
		}
		return new PageImpl<GroupInfo>(groupInfos, pageable, groupInfos.size());
	}

	@Override
	public Page<GroupInfo> findByRoleNameEquals(String roleName, Pageable pageable){
		List<GroupInfo> groupInfos = new ArrayList<>();
		try {
			Page<UserRole> pagedUserRoles = tlsUserRoleDao.findByRoleNameEquals(roleName, pageable);
			//logger.info("findByRoleNameEquals pagedUserRoles: " + pagedUserRoles);
			List<UserRole> userRoles = pagedUserRoles.getContent();
			logger.info("findByRoleNameEquals userRoles.size(): " + userRoles.size());
			groupInfos = getGroupInfos(userRoles);
			logger.info("findByRoleNameEquals groupInfos.size(): " + groupInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			groupInfos = null;
		}
		return new PageImpl<GroupInfo>(groupInfos, pageable, groupInfos.size());
	}

	@Override
	public Page<GroupInfo> findByRoleNameContaining(String roleName, Pageable pageable){
		List<GroupInfo> groupInfos = new ArrayList<>();
		try {
			Page<UserRole> pagedUserRoles = tlsUserRoleDao.findByRoleNameContaining(roleName, pageable);
			//logger.info("findByRoleNameContaining pagedUserRoles: " + pagedUserRoles);
			List<UserRole> userRoles = pagedUserRoles.getContent();
			logger.info("findByRoleNameContaining userRoles.size(): " + userRoles.size());
			groupInfos = getGroupInfos(userRoles);
			logger.info("findByRoleNameContaining groupInfos.size(): " + groupInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			groupInfos = null;
		}
		return new PageImpl<GroupInfo>(groupInfos, pageable, groupInfos.size());
	}

	@Override
	public Page<GroupInfo> findByRoleDescEquals(String roleDesc, Pageable pageable){
		List<GroupInfo> groupInfos = new ArrayList<>();
		try {
			Page<UserRole> pagedUserRoles = tlsUserRoleDao.findByRoleDescEquals(roleDesc, pageable);
			//logger.info("findByRoleDescEquals pagedUserRoles: " + pagedUserRoles);
			List<UserRole> userRoles = pagedUserRoles.getContent();
			logger.info("findByRoleDescEquals userRoles.size(): " + userRoles.size());
			groupInfos = getGroupInfos(userRoles);
			logger.info("findByRoleDescEquals groupInfos.size(): " + groupInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			groupInfos = null;
		}
		return new PageImpl<GroupInfo>(groupInfos, pageable, groupInfos.size());
	}

	@Override
	public Page<GroupInfo> findByRoleDescContaining(String roleDesc, Pageable pageable){
		List<GroupInfo> groupInfos = new ArrayList<>();
		try {
			Page<UserRole> pagedUserRoles = tlsUserRoleDao.findByRoleDescContaining(roleDesc, pageable);
			//ogger.info("findByRoleDescContaining pagedUserRoles: " + pagedUserRoles);
			List<UserRole> userRoles = pagedUserRoles.getContent();
			logger.info("findByRoleDescContaining userRoles.size(): " + userRoles.size());
			groupInfos = getGroupInfos(userRoles);
			logger.info("findByRoleDescContaining groupInfos.size(): " + groupInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			groupInfos = null;
		}
		return new PageImpl<GroupInfo>(groupInfos, pageable, groupInfos.size());
	}

	private List<GroupInfo> getGroupInfos(List<UserRole> userRoles){
		List<GroupInfo> groupInfos = new ArrayList<>();
		try {
			List<UserProfile> userProfiles = new ArrayList<>();
			List<UserProfile> userProfileAllList = tlsUserProfileService.loadAllUserProfile();
			Map<String, UserProfile> userProfileHashMap = new HashMap<>();
			
			Set<UserRoleAccess> userRoleAccessSet = new HashSet<>();
			Set<UserRoleAccess> userRoleAccessAllSet = tlsUserRoleAccessService.loadUserRoleAccesses();
			Map<String, UserRoleAccess> userRoleAccessHashMap = new HashMap<>();
			
			for (UserRole userRole: userRoles) {
				
				for (UserProfile tempUserProfile: userProfileAllList) {
					if (userRole.getRoleName().equalsIgnoreCase(tempUserProfile.getRoleName())) {
						userProfileHashMap.put(userRole.getRoleName(), tempUserProfile);
						userProfiles.add(userProfileHashMap.get(userRole.getRoleName()));
					}
				}
				userRole.setUserProfiles(userProfiles);
				
				for (UserRoleAccess tempUserRoleAccess: userRoleAccessAllSet) {
					if (userRole.getRoleName().equalsIgnoreCase(tempUserRoleAccess.getRoleName())) {
						userRoleAccessHashMap.put(userRole.getRoleName(), tempUserRoleAccess);
						userRoleAccessSet.add(userRoleAccessHashMap.get(userRole.getRoleName()));
					}
				}
				userRole.setUserRoleAccessSet(userRoleAccessSet);
				
				GroupInfo groupInfo = getGroupInfo(userRole);
				groupInfos.add(groupInfo);
			}
		} catch (Exception e){
			e.printStackTrace();
			groupInfos = null;
		}
		return groupInfos;
	}

	private GroupInfo getGroupInfo(UserRole userRole){
		return tlsGroupInfoMapper.convertUserRoleToJSON(userRole);
	}
}
