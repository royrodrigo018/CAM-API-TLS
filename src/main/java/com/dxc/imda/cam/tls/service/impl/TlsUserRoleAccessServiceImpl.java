package com.dxc.imda.cam.tls.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.imda.cam.tls.dao.TlsUserRoleAccessDao;
import com.dxc.imda.cam.tls.model.UserRoleAccess;
import com.dxc.imda.cam.tls.service.TlsUserRoleAccessService;

@Service
public class TlsUserRoleAccessServiceImpl implements TlsUserRoleAccessService{
	
	@Autowired
	private TlsUserRoleAccessDao tlsUserRoleAccessDao;

	@Override
	public Set<UserRoleAccess> getUserRoleAccess(String roleName) {
		return tlsUserRoleAccessDao.getUserRoleAccess(roleName);
	}

	@Override
	public Set<UserRoleAccess> loadUserRoleAccesses() {
		return tlsUserRoleAccessDao.loadUserRoleAccesses();
	}
}
