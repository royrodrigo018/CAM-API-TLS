package com.dxc.imda.cam.common.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dxc.imda.cam.common.constant.Constants;
import com.dxc.imda.cam.common.constant.Enums.ResourceType;
import com.dxc.imda.cam.tls.entity.TlsCamApiAudit;

public class AuditUtil {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public TlsCamApiAudit logTlsAudit(HttpServletRequest request, 
		HttpServletResponse response,
		ResourceType resourceType, Object object) {
		
		TlsCamApiAudit tlsCamApiAudit = new TlsCamApiAudit();
		if (resourceType.equals(ResourceType.USER)) {
			tlsCamApiAudit.setResource(ResourceType.USER.toString());
		}else {
			tlsCamApiAudit.setResource(ResourceType.GROUP.toString());
		}
		tlsCamApiAudit.setRequestUri(request.getRequestURI());		
		tlsCamApiAudit.setRequestDate(new Date());
		
		JSONUtil jsonUtil = new JSONUtil();
		String jsonString = jsonUtil.convertObjectToJsonString(object);
		tlsCamApiAudit.setData(jsonString);	
		
		//logger.info("logTlsAudit response.getStatus(): " + response.getStatus());
		tlsCamApiAudit.setResponseStatus(response.getStatus());
		tlsCamApiAudit.setStatus(Constants.SUCCESS);
		if (response.getStatus() >= 400) {
			tlsCamApiAudit.setStatus(Constants.ERROR);	
		}		
		return tlsCamApiAudit;
	}
}
