package com.dxc.imda.cam.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.imda.cam.tls.controller.TlsGroupController;

@RestController
@RequestMapping("/cam/api")
public class GroupController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Value("${spring.app.tls.accountId:defaultValue}")
	private String tlsAccountId;
			
	@Autowired
	private TlsGroupController tlsGroupController;
	
	@PostMapping(value = "groups/info", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getGroupInfo(HttpServletRequest request, 
		HttpServletResponse response,
		@RequestParam Map<String, String> reqParamMap, 
		@RequestBody Map<String, String> reqBodyMap
	) {
		logger.info("============== Start getGroupInfo ==============");
		
		String accountId = reqParamMap.get("accountid");
		logger.info("accountId: "+ accountId);
		ResponseEntity<Object> responseEntity = null;
		logger.info("tlsAccountId: "+ tlsAccountId);

		if (tlsAccountId.equalsIgnoreCase(accountId)) {
			responseEntity = tlsGroupController.getGroupInfo(request, response, reqParamMap, reqBodyMap);
		}	
		
		logger.info("============== End getGroupInfo ==============");
		logger.info("");
		return responseEntity;
	}
	
	@PostMapping(value = "groups/findbycriteria", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getGroupInfoList(HttpServletRequest request, 
		HttpServletResponse response,
		@RequestParam Map<String, String> reqParamMap, 
		@RequestBody Map<String, String> reqBodyMap
	) {
		logger.info("============== Start getGroupInfoList ==============");
		
		String accountId = reqParamMap.get("accountid");
		logger.info("accountId: "+ accountId);
		ResponseEntity<Object> responseEntity = null;
		logger.info("tlsAccountId: "+ tlsAccountId);

		if (tlsAccountId.equalsIgnoreCase(accountId)) {
			responseEntity = tlsGroupController.getGroupInfoList(request, response, reqParamMap, reqBodyMap);
		}	
		
		logger.info("============== End getGroupInfoList ==============");
		logger.info("");
		return responseEntity;	
	}
	
	@PostMapping(value = "groups/update", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> updateGroup(HttpServletRequest request, 
		HttpServletResponse response,
		@RequestParam Map<String, String> reqParamMap, 
		@RequestBody String reqBody
	) {
		logger.info("============== Start updateGroup ==============");
		
		String accountId = reqParamMap.get("accountid");
		logger.info("accountId: "+ accountId);
		ResponseEntity<Object> responseEntity = null;
		logger.info("tlsAccountId: "+ tlsAccountId);

		if (tlsAccountId.equalsIgnoreCase(accountId)) {
			responseEntity = tlsGroupController.updateGroup(request, response, reqParamMap, reqBody);
		}	
		
		logger.info("============== End updateGroup ==============");
		logger.info("");
		return responseEntity;
	}
}
