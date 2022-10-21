package com.dxc.imda.cam.tls.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.imda.cam.tls.dao.TlsCamApiAuditDao;
import com.dxc.imda.cam.tls.entity.TlsCamApiAudit;
import com.dxc.imda.cam.tls.service.TlsCamApiAuditService;

@Service
public class TlsCamApiAuditServiceImpl implements TlsCamApiAuditService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TlsCamApiAuditDao tlsCamApiAuditDao;
	
	@Override
	public int saveOrUpdate(TlsCamApiAudit tempTlsCamApiAudit) {
		int result = 0;
		try {
			TlsCamApiAudit tlsCamApiAudit = tlsCamApiAuditDao.save(tempTlsCamApiAudit);
			result = tlsCamApiAudit != null ? 1 : 0;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}		
		return result;
	}
}
