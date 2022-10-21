package com.dxc.imda.cam.tls.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.imda.cam.tls.entity.TlsCamApiAudit;


@Repository
public interface TlsCamApiAuditDao extends JpaRepository<TlsCamApiAudit, Long> {
	
}
