package com.dxc.imda.cam.tls.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.imda.cam.tls.model.UserProfile;

public interface TlsUserProfileDao {
	
	public UserProfile findByUserId(String userId);
	
	/** List **/
	
	public Long countAll();
	
	public Long countByRoleNameEquals(String roleName);
	
	public Long countByRoleNameContaining(String roleName);	
	
	public Long countByRoleDescEquals(String roleDesc);
	
	public Long countByRoleDescContaining(String roleDesc);	
		
	/** List **/
	
	public List<UserProfile> loadAllUserProfile();
	
	public List<UserProfile> findByRoleName(String roleName);
	
	/** Page **/
	
	public Page<UserProfile> findAll(Pageable pageable);
	
	public Page<UserProfile> findByRoleNameEquals(String roleName, Pageable pageable);

	public Page<UserProfile> findByRoleNameContaining(String roleName, Pageable pageable);
	
	public Page<UserProfile> findByRoleDescEquals(String roleDesc, Pageable pageable);
	
	public Page<UserProfile> findByRoleDescContaining(String roleDesc, Pageable pageable);
	
	/** Update **/
	
	public UserProfile updateUser(UserProfile userProfile);
	public UserProfile removeUser(UserProfile userProfile);
}
