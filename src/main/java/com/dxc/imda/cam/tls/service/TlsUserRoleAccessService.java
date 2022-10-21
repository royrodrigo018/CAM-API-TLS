package com.dxc.imda.cam.tls.service;

import java.util.Set;

import com.dxc.imda.cam.tls.model.UserRoleAccess;

public interface TlsUserRoleAccessService {
	
	public Set<UserRoleAccess> getUserRoleAccess(String roleName);
	
	public Set<UserRoleAccess> loadUserRoleAccesses();
}
