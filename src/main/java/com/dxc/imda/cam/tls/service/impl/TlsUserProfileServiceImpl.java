package com.dxc.imda.cam.tls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dxc.imda.cam.common.model.UpdateInfo;
import com.dxc.imda.cam.common.model.UserInfo;
import com.dxc.imda.cam.tls.dao.TlsUserProfileDao;
import com.dxc.imda.cam.tls.mapper.TlsUserInfoMapper;
import com.dxc.imda.cam.tls.model.UserProfile;
import com.dxc.imda.cam.tls.service.TlsUserProfileService;

@Service
public class TlsUserProfileServiceImpl implements TlsUserProfileService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TlsUserProfileDao tlsUserProfileDao;

	@Autowired
	private TlsUserInfoMapper tlsUserInfoMapper;

	@Override
	public UserInfo findByUserId(String userId) {
		UserInfo userInfo = new UserInfo();
		try {
			UserProfile userProfile = tlsUserProfileDao.findByUserId(userId);
			if (userProfile != null) {
				userInfo = getUserInfo(userProfile);
			}
		} catch (Exception e){
			e.printStackTrace();
			userInfo = null;
		}
		return userInfo;
	}
	
	@Override
	public List<UserProfile> loadAllUserProfile() {
		return tlsUserProfileDao.loadAllUserProfile();
	}

	@Override
	public List<UserProfile> findByRoleName(String roleName) {
		return tlsUserProfileDao.findByRoleName(roleName);
	}
	

	@Override
	public Long countAll() {
		return tlsUserProfileDao.countAll();
	}

	@Override
	public Long countByRoleNameEquals(String roleName) {
		return tlsUserProfileDao.countByRoleNameEquals(roleName);
	}

	@Override
	public Long countByRoleNameContaining(String roleName) {
		return tlsUserProfileDao.countByRoleNameContaining(roleName);
	}

	@Override
	public Long countByRoleDescEquals(String roleDesc) {
		return tlsUserProfileDao.countByRoleDescEquals(roleDesc);
	}

	@Override
	public Long countByRoleDescContaining(String roleDesc) {
		return tlsUserProfileDao.countByRoleDescContaining(roleDesc);
	}

	@Override
	public Page<UserInfo> findAll(Pageable pageable) {
		List<UserInfo> userInfos = new ArrayList<>();
		try {
			Page<UserProfile> pagedUserProfiles = tlsUserProfileDao.findAll(pageable);
			//logger.info("findAll pagedUserProfiles: " + pagedUserProfiles);
			List<UserProfile> userProfiles = pagedUserProfiles.getContent();
			logger.info("findAll userProfiles.size(): " + userProfiles.size());
			userInfos = getUserInfos(userProfiles);
			logger.info("findAll userInfos.size(): " + userInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			userInfos = null;
		}
		return new PageImpl<UserInfo>(userInfos, pageable, userInfos.size());
	}

	@Override
	public Page<UserInfo> findByRoleNameEquals(String roleName, Pageable pageable) {
		List<UserInfo> userInfos = new ArrayList<>();
		try {
			Page<UserProfile> pagedUserProfiles = tlsUserProfileDao.findByRoleNameEquals(roleName, pageable);
			//logger.info("findByRoleNameEquals pagedUserProfiles: " + pagedUserProfiles);
			List<UserProfile> userProfiles = pagedUserProfiles.getContent();
			logger.info("findByRoleNameEquals userProfiles.size(): " + userProfiles.size());
			userInfos = getUserInfos(userProfiles);
			logger.info("findByRoleNameEquals userInfos.size(): " + userInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			userInfos = null;
		}
		return new PageImpl<UserInfo>(userInfos, pageable, userInfos.size());
	}

	@Override
	public Page<UserInfo> findByRoleNameContaining(String roleName, Pageable pageable) {
		List<UserInfo> userInfos = new ArrayList<>();
		try {
			Page<UserProfile> pagedUserProfiles = tlsUserProfileDao.findByRoleNameContaining(roleName, pageable);
			//logger.info("findByRoleNameContaining pagedUserProfiles: " + pagedUserProfiles);
			List<UserProfile> userProfiles = pagedUserProfiles.getContent();
			logger.info("findByRoleNameContaining userProfiles.size(): " + userProfiles.size());
			userInfos = getUserInfos(userProfiles);
			logger.info("findByRoleNameContaining userInfos.size(): " + userInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			userInfos = null;
		}
		return new PageImpl<UserInfo>(userInfos, pageable, userInfos.size());
	}

	@Override
	public Page<UserInfo> findByRoleDescEquals(String roleDesc, Pageable pageable) {
		List<UserInfo> userInfos = new ArrayList<>();
		try {
			Page<UserProfile> pagedUserProfiles = tlsUserProfileDao.findByRoleDescEquals(roleDesc, pageable);
			//logger.info("findByRoleDescEquals pagedUserProfiles: " + pagedUserProfiles);
			List<UserProfile> userProfiles = pagedUserProfiles.getContent();
			logger.info("findByRoleDescEquals userProfiles.size(): " + userProfiles.size());
			userInfos = getUserInfos(userProfiles);
			logger.info("findByRoleDescEquals userInfos.size(): " + userInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			userInfos = null;
		}
		return new PageImpl<UserInfo>(userInfos, pageable, userInfos.size());
	}

	@Override
	public Page<UserInfo> findByRoleDescContaining(String roleDesc, Pageable pageable) {
		List<UserInfo> userInfos = new ArrayList<>();
		try {
			Page<UserProfile> pagedUserProfiles = tlsUserProfileDao.findByRoleDescContaining(roleDesc, pageable);
			//logger.info("findByRoleDescContaining pagedUserProfiles: " + pagedUserProfiles);
			List<UserProfile> userProfiles = pagedUserProfiles.getContent();
			logger.info("findByRoleDescContaining userProfiles.size(): " + userProfiles.size());
			userInfos = getUserInfos(userProfiles);
			logger.info("findByRoleDescContaining userInfos.size(): " + userInfos.size());
		} catch (Exception e){
			e.printStackTrace();
			userInfos = null;
		}
		return new PageImpl<UserInfo>(userInfos, pageable, userInfos.size());
	}

	@Override
	public UserInfo updateUser(String userId, String status) {
		UserProfile userProfile = setUserProfile(userId, status);
		try {
			userProfile = tlsUserProfileDao.removeUser(userProfile);
		} catch (Exception e){
			e.printStackTrace();			
		}
		return getUserInfo(userProfile);
	}
	
	@Override
	public UserInfo updateUser(String userId, Boolean blnValue) {
		UserProfile userProfile = setUserProfile(userId, blnValue);
		try {
			if (blnValue) {
				if (userProfile.getStatus().equalsIgnoreCase("A")) {
					userProfile.setStatus("I");
				}else {
					userProfile.setStatus("A");
				}
				userProfile = tlsUserProfileDao.updateUser(userProfile);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return getUserInfo(userProfile);
	}

	@Override
	public UpdateInfo removeUser(String userId, String status) {
		UserProfile userProfile = setUserProfile(userId, status);
		try {
			userProfile = tlsUserProfileDao.removeUser(userProfile);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getUpdateInfo(userProfile); //TODO to check;
	}

	private UserProfile setUserProfile(String userId, Boolean blnValue) {
		return tlsUserProfileDao.findByUserId(userId);
	}
	
	private UserProfile setUserProfile(String userId, String status) {
		return tlsUserProfileDao.findByUserId(userId);
	}

	private List<UserInfo> getUserInfos(List<UserProfile> userProfiles){
		List<UserInfo> userInfos = new ArrayList<>();
		try {
			for (UserProfile userProfile: userProfiles) {
				UserInfo userInfo = getUserInfo(userProfile);
				userInfos.add(userInfo);
			}
		} catch (Exception e){
			e.printStackTrace();
			userInfos = null;
		}
		return userInfos;
	}

	private UserInfo getUserInfo(UserProfile userProfile){
		return tlsUserInfoMapper.convertUserToJSON(userProfile);
	}

	private UpdateInfo getUpdateInfo(UserProfile userProfile){
		return tlsUserInfoMapper.convertUpdateInfoToJSON(userProfile);
	}	
}
