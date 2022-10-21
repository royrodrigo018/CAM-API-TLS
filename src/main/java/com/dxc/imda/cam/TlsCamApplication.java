package com.dxc.imda.cam;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dxc.imda.cam.common.model.GroupInfo;
import com.dxc.imda.cam.common.model.UpdateInfo;
import com.dxc.imda.cam.common.model.UserInfo;
import com.dxc.imda.cam.tls.model.UserProfile;
import com.dxc.imda.cam.tls.model.UserRole;
import com.dxc.imda.cam.tls.service.TlsUserProfileService;
import com.dxc.imda.cam.tls.service.TlsUserRoleService;

@SpringBootApplication
@Component
@EnableTransactionManagement
public class TlsCamApplication extends SpringBootServletInitializer implements CommandLineRunner{
	
	private static Logger logger = LoggerFactory.getLogger(TlsCamApplication.class);

	public static void main(String[] args) {
		logger.info("********** STARTING THE APPLICATION ********** ");
		SpringApplication.run(TlsCamApplication.class, args);
		logger.info("********** APPLICATION END ********** ");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TlsCamApplication.class);
	}
	
	@Autowired
	TlsUserProfileService tlsUserProfileService;
	
	@Autowired
	TlsUserRoleService tlsUserRoleService;
	
	@Override
	public void run(String... args) throws Exception {
				
		String userId = "MKNS3";
		String roleName1 = "SBO-STAFF";
		String roleDesc1 = "SCS ADMINISTRATOR";
		String roleName2 = "FBO";
		String roleDesc2 = "RL";
		long uid = 26L;
		
		Long count = 0L;
		
		List<UserProfile> userProfiles = new ArrayList<>();
		List<UserInfo> userInfos = new ArrayList<>();	
		UserProfile userProfile = new UserProfile();	
		UserInfo userInfo = new UserInfo();
		UpdateInfo updateInfo = new UpdateInfo();

				
//		userInfo = tlsUserProfileService.findByUserId("ENLG");
		
//		updateInfo = tlsUserProfileService.removeUser("S7404346A", "InActive");
//
//		userInfo = tlsUserProfileService.updateUser("S7404346A", "Active");

//		count = tlsUserProfileService.countAll();		
//		logger.info("findAll count: " + count);
//
//		count = tlsUserProfileService.countByRoleNameEquals(roleName1);
//		logger.info("findByRoleNameEquals count: " + count);
//
//		count = tlsUserProfileService.countByRoleNameContaining(roleName2);
//		logger.info("findByRoleNameContaining count: " + count);
//		
//		count = tlsUserProfileService.countByRoleDescEquals(roleDesc1);
//		logger.info("findByRoleDescEquals count: " + count);
//
//		count = tlsUserProfileService.countByRoleDescContaining(roleDesc2);		
//		logger.info("findByRoleDescContaining count: " + count);
		
		Pageable pageable = PageRequest.of(0, 20, Sort.by("roleName").ascending());
		Page<UserInfo> pagedUserInfos;
		
//		pagedUserInfos = tlsUserProfileService.findAll(pageable);		
//		logger.info("findAll pagedUserInfos.size(): " + pagedUserInfos.getSize());
//
//		pagedUserInfos = tlsUserProfileService.findByRoleNameEquals(roleName1, pageable);
//		logger.info("findByRoleNameEquals pagedUserInfos.size(): " + pagedUserInfos.getSize());
//
//		pagedUserInfos = tlsUserProfileService.findByRoleNameContaining(roleName2, pageable);
//		logger.info("findByRoleNameContaining pagedUserInfos.size(): " + pagedUserInfos.getSize());
//		
//		pagedUserInfos = tlsUserProfileService.findByRoleDescEquals(roleDesc1, pageable);
//		logger.info("findByRoleDescEquals pagedUserInfos.size(): " + pagedUserInfos.getSize());
//
//		pagedUserInfos = tlsUserProfileService.findByRoleDescContaining(roleDesc2, pageable);		
//		logger.info("findByRoleDescContaining pagedUserInfos.size(): " + pagedUserInfos.getSize());
						
				
		List<UserRole> userRoles = new ArrayList<>();
		List<GroupInfo> groupInfos = new ArrayList<>();
		
		GroupInfo groupInfo = new GroupInfo();
		
		//groupInfo = tlsUserRoleService.findByRoleName(roleName1);
		
//		count = tlsUserRoleService.countAll();		
//		logger.info("findAll count: " + count);
//
//		count = tlsUserRoleService.countByRoleNameEquals(roleName1);
//		logger.info("findByRoleNameEquals count: " + count);
//
//		count = tlsUserRoleService.countByRoleNameContaining(roleName2);
//		logger.info("findByRoleNameContaining count: " + count);
//		
//		count = tlsUserRoleService.countByRoleDescEquals(roleDesc1);
//		logger.info("findByRoleDescEquals count: " + count);
//
//		count = tlsUserRoleService.countByRoleDescContaining(roleDesc2);		
//		logger.info("findByRoleDescContaining count: " + count);
		
		
		Page<GroupInfo> pagedGroupInfos;
		
//		pagedGroupInfos = tlsUserRoleService.findAll(pageable);
//		logger.info("pagedGroupInfos: " + pagedGroupInfos.getSize());	
//
//		pagedGroupInfos = tlsUserRoleService.findByRoleNameEquals(roleName1, pageable);
//		logger.info("pagedGroupInfos: " + pagedGroupInfos.getSize());	
//		
//		pagedGroupInfos = tlsUserRoleService.findByRoleNameContaining(roleName2, pageable);
//		logger.info("pagedGroupInfos: " + pagedGroupInfos.getSize());	
//		
//		pagedGroupInfos = tlsUserRoleService.findByRoleDescEquals(roleDesc1, pageable);
//		logger.info("pagedGroupInfos: " + pagedGroupInfos.getSize());	
//		
//		pagedGroupInfos = tlsUserRoleService.findByRoleDescContaining(roleDesc2, pageable);
//		logger.info("pagedGroupInfos: " + pagedGroupInfos.getSize());	
	}
}
