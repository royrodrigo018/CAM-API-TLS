package com.dxc.imda.cam.tls.dao;

import java.util.Set;

import com.dxc.imda.cam.tls.model.UserRoleAccess;

public interface TlsUserRoleAccessDao {
	
	public Set<UserRoleAccess> getUserRoleAccess(String roleName);
	
	public Set<UserRoleAccess> loadUserRoleAccesses();
}
