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

import com.dxc.imda.cam.tls.controller.TlsUserController;

@RestController
@RequestMapping("/cam/api")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.app.tls.accountId:defaultValue}")
	private String tlsAccountId;

	@Autowired
	private TlsUserController tlsUserController;
		
	@PostMapping(value = "users/info", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getUserInfo(HttpServletRequest request, 
		HttpServletResponse response,
		@RequestParam Map<String, String> reqParamMap, 
		@RequestBody Map<String, String> reqBodyMap
	) {
		logger.info("============== Start getUserInfo ==============");
		
		String accountId = reqParamMap.get("accountid");
		logger.info("getUserInfo accountId: "+ accountId);
		ResponseEntity<Object> responseEntity = null;
		logger.info("tlsAccountId: "+ tlsAccountId);
		
		if (tlsAccountId.equalsIgnoreCase(accountId)) {
			responseEntity = tlsUserController.getUserInfo(request, response, reqParamMap, reqBodyMap);
		}	
				
		logger.info("============== End getUserInfo ==============");
		logger.info("");
		return responseEntity;
	}
	
	@PostMapping(value = "users/findbycriteria", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getUserInfoList(HttpServletRequest request, 
		HttpServletResponse response,
		@RequestParam Map<String, String> reqParamMap, 
		@RequestBody Map<String, String> reqBodyMap
	) {		
		logger.info("============== Start getUserInfoList ==============");
		
		String accountId = reqParamMap.get("accountid");
		logger.info("getUserInfoList accountId: "+ accountId);
		ResponseEntity<Object> responseEntity = null;
		logger.info("tlsAccountId: "+ tlsAccountId);

		if (tlsAccountId.equalsIgnoreCase(accountId)) {
			responseEntity = tlsUserController.getUserInfoList(request, response, reqParamMap, reqBodyMap);
		}	
		
		logger.info("============== End getUserInfoList ==============");
		logger.info("");
		return responseEntity;
	}
	
	@PostMapping(value = "users/update", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> updateUser(HttpServletRequest request, 
		HttpServletResponse response,
		@RequestParam Map<String, String> reqParamMap, 
		@RequestBody String reqBody
	) {
		logger.info("============== Start updateUser ==============");
				
		String accountId = reqParamMap.get("accountid");
		logger.info("updateUser accountId: "+ accountId);
		ResponseEntity<Object> responseEntity = null;
		logger.info("tlsAccountId: "+ tlsAccountId);

		if (tlsAccountId.equalsIgnoreCase(accountId)) {
			responseEntity = tlsUserController.updateUser(request, response, reqParamMap, reqBody);
		}	
		
		logger.info("============== End updateUser ==============");
		logger.info("");
		return responseEntity;			
	}
	
	@PostMapping(value = "users/remove", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> removeUser(HttpServletRequest request, 
		HttpServletResponse response,
		@RequestParam Map<String, String> reqParamMap, 
		@RequestBody Map<String, String> reqBodyMap
	) {
		logger.info("============== Start removeUser ==============");
		
		String accountId = reqParamMap.get("accountid");
		logger.info("removeUser accountId: "+ accountId);
		ResponseEntity<Object> responseEntity = null;
		logger.info("tlsAccountId: "+ tlsAccountId);

		if (tlsAccountId.equalsIgnoreCase(accountId)) {
			responseEntity = tlsUserController.removeUser(request, response, reqParamMap, reqBodyMap);
		}	
		
		logger.info("============== End removeUser ==============");
		logger.info("");
		return responseEntity;		
	}
}
