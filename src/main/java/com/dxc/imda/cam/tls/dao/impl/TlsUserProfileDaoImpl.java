package com.dxc.imda.cam.tls.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.imda.cam.common.constant.Enums.AppName;
import com.dxc.imda.cam.common.util.PageUtil;
import com.dxc.imda.cam.tls.dao.TlsUserProfileDao;
import com.dxc.imda.cam.tls.helper.TlsUserProfileHelper;
import com.dxc.imda.cam.tls.model.UserProfile;

@Repository
public class TlsUserProfileDaoImpl implements TlsUserProfileDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate tlsJdbcTemplate;

	@Autowired
	private TlsUserProfileHelper tlsUserProfileHelper;
	
	@Autowired
	private PageUtil pageUtil;

	@Autowired
	public TlsUserProfileDaoImpl(JdbcTemplate tlsJdbcTemplate) {
		this.tlsJdbcTemplate = tlsJdbcTemplate;
	}

	private static final String countQuery = "SELECT count(userProfile.USER_ID) "
		+ "FROM SC_USER_PROFILE userProfile "
		+ "LEFT JOIN SC_USER_GP userRole ON userProfile.USER_GP = userRole.USER_GP ";

	//private static final String sqlQueryOne = "SELECT userProfile.* "
		//+ "FROM SC_USER_PROFILE userProfile ";

	private static final String sqlQuery = "SELECT userProfile.*, userRole.* "
		+ "FROM SC_USER_PROFILE userProfile "
		+ "LEFT JOIN SC_USER_GP userRole ON userProfile.USER_GP = userRole.USER_GP ";

	private static final String updateQuery = "UPDATE SC_USER_PROFILE userProfile "
		+ "SET userProfile.STATUS = ? "
		+ ", userProfile.DATE_UPDATED = CURRENT_DATE "
		+ ", userProfile.UPDATED_BY = 'CAM' "; // TODO
	
	private static final String updateClauseDeactivateDate = ", userProfile.DATE_DEACTIVATE = CURRENT_DATE ";
	private static final String updateClauseReactivateDate = ", userProfile.DATE_REACTIVATE = CURRENT_DATE ";

	private static final String whereClauseEqUserId = "WHERE UPPER(userProfile.USER_ID) = ? ";

	private static final String whereClauseEqRoleName = "WHERE UPPER(userRole.USER_GP) = ? ";
	private static final String whereClauseEqRoleDesc = "WHERE UPPER(userRole.USERGP_NAME) = ? "; // TODO Ignore Case

	private static final String whereClauseLikeRoleName = "WHERE UPPER(userRole.USER_GP) LIKE ? ";
	private static final String whereClauseLikeRoleDesc = "WHERE UPPER(userRole.USERGP_NAME) LIKE ? ";

	private static final String whereClauseActiveStatus = "WHERE userProfile.STATUS = 'A' ";
	private static final String andClauseActiveStatus = "AND userProfile.STATUS = 'A' ";
	
	//private static final String whereClauseFilterRole = "WHERE UPPER(userRole.USER_GP) <> 'INTERNET' ";
	private static final String andClauseFilterRole = "AND UPPER(userRole.USER_GP) <> 'INTERNET' ";

	private static final String pageClause = "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";

	@Override
	public UserProfile findByUserId(String userId){
		UserProfile userProfile  = new UserProfile();
		try {
			String sql = sqlQuery + whereClauseEqUserId;
			logger.info("findByUserId sql: " + sql);
			Map<String, Object> mapRow = tlsJdbcTemplate.queryForMap(
				sql, new Object[]{userId.toUpperCase()});
			userProfile = tlsUserProfileHelper.getUserProfile(mapRow);
		} catch (Exception e){
			e.printStackTrace();
		}
		return userProfile;
	}

	/** Count **/

	@Override
	public Long countAll() {
		String sql = countQuery + whereClauseActiveStatus + andClauseFilterRole;
		return tlsJdbcTemplate.queryForObject(
			sql, Long.class);
	}

	@Override
	public Long countByRoleNameEquals(String roleName) {
		String sql = countQuery + whereClauseEqRoleName + andClauseActiveStatus + andClauseFilterRole;
		return tlsJdbcTemplate.queryForObject(
			sql, Long.class, new Object[]{roleName.toUpperCase()});
	}

	@Override
	public Long countByRoleDescEquals(String roleDesc) {
		String sql = countQuery + whereClauseEqRoleDesc + andClauseActiveStatus + andClauseFilterRole;
		return tlsJdbcTemplate.queryForObject(
			sql, Long.class, new Object[]{roleDesc.toUpperCase()});
	}

	@Override
	public Long countByRoleNameContaining(String roleName) {
		String sql = countQuery + whereClauseLikeRoleName + andClauseActiveStatus + andClauseFilterRole;
		return tlsJdbcTemplate.queryForObject(
			sql, Long.class, new Object[]{"%" + roleName.toUpperCase() + "%"});
	}

	@Override
	public Long countByRoleDescContaining(String roleDesc) {
		String sql = countQuery + whereClauseLikeRoleDesc + andClauseActiveStatus + andClauseFilterRole;
		return tlsJdbcTemplate.queryForObject(
			sql, Long.class, new Object[]{"%" + roleDesc.toUpperCase() + "%"});
	}

	/** List **/
	
	@Override
	public List<UserProfile> loadAllUserProfile() {
		List<UserProfile> userProfiles = new ArrayList<>();
		try {
			String sql = sqlQuery + whereClauseActiveStatus + andClauseFilterRole;
			//logger.info("loadAllUserProfile sql: " + sql);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql);
			//logger.info("loadAllUserProfile results.size(): " + results.size());
			userProfiles = getUserProfiles(results);
		} catch (Exception e){
			e.printStackTrace();
		}
		return userProfiles;
	}
		
	@Override
	public List<UserProfile> findByRoleName(String roleName) {
		List<UserProfile> userProfiles = new ArrayList<>();
		try {
			String sql = sqlQuery + whereClauseEqRoleName + andClauseActiveStatus + andClauseFilterRole;
			logger.info("findByRoleName sql: " + sql);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[]{roleName.toUpperCase()});
			//logger.info("findByRoleName results.size(): " + results.size());
			userProfiles = getUserProfiles(results);
		} catch (Exception e){
			e.printStackTrace();
		}
		return userProfiles;
	}

	/** Page **/

	@Override
	public Page<UserProfile> findAll(Pageable pageable){
		List<UserProfile> userProfiles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseActiveStatus + andClauseFilterRole + orderByAndDirectionClause + pageClause;
			logger.info("findAll sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[] {pageNum - 1, pageable.getPageSize()});
			//logger.info("findAll results.size(): " + results.size());
			userProfiles = getUserProfiles(results);
			logger.info("findAll userProfiles.size(): " + userProfiles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserProfile>(userProfiles, pageable, userProfiles.size());
	}

	@Override
	public Page<UserProfile> findByRoleNameEquals(String roleName, Pageable pageable){
		List<UserProfile> userProfiles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseEqRoleName + andClauseActiveStatus + andClauseFilterRole + orderByAndDirectionClause + pageClause;
			logger.info("findByRoleNameEquals sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[]{roleName.toUpperCase(), pageNum - 1, pageable.getPageSize()});
			//logger.info("findByRoleNameEquals results.size(): " + results.size());
			userProfiles = getUserProfiles(results);
			logger.info("findByRoleNameEquals userProfiles.size(): " + userProfiles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserProfile>(userProfiles, pageable, userProfiles.size());
	}

	@Override
	public Page<UserProfile> findByRoleNameContaining(String roleName, Pageable pageable){
		List<UserProfile> userProfiles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseLikeRoleName + andClauseActiveStatus + andClauseFilterRole + orderByAndDirectionClause + pageClause;
			logger.info("findByRoleNameContaining sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[]{"%" + roleName.toUpperCase() + "%", pageNum - 1, pageable.getPageSize()});
			//logger.info("findByRoleNameContaining results.size(): " + results.size());
			userProfiles = getUserProfiles(results);
			logger.info("findByRoleNameContaining userProfiles.size(): " + userProfiles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserProfile>(userProfiles, pageable, userProfiles.size());
	}

	@Override
	public Page<UserProfile> findByRoleDescEquals(String roleDesc, Pageable pageable){
		List<UserProfile> userProfiles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseEqRoleDesc + andClauseActiveStatus + andClauseFilterRole + orderByAndDirectionClause + pageClause;
			logger.info("findByRoleDescEquals sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[]{roleDesc.toUpperCase(), pageNum - 1, pageable.getPageSize()});
			//logger.info("findByRoleDescEquals results.size(): " + results.size());
			userProfiles = getUserProfiles(results);
			logger.info("findByRoleDescEquals userProfiles.size(): " + userProfiles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserProfile>(userProfiles, pageable, userProfiles.size());
	}

	@Override
	public Page<UserProfile> findByRoleDescContaining(String roleDesc, Pageable pageable){
		List<UserProfile> userProfiles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseLikeRoleDesc + andClauseActiveStatus + andClauseFilterRole + orderByAndDirectionClause + pageClause;
			logger.info("findByRoleDescContaining sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[]{"%" + roleDesc.toUpperCase() + "%", pageNum - 1, pageable.getPageSize()});
			//logger.info("findByRoleDescContaining results.size(): " + results.size());
			userProfiles = getUserProfiles(results);
			logger.info("findByRoleDescContaining userProfiles.size(): " + userProfiles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserProfile>(userProfiles, pageable, userProfiles.size());
	}	
	
	@Transactional
	@Override
	public UserProfile updateUser(UserProfile userProfile) {
		logger.info("updateUser userId: " + userProfile.getUserId());
		logger.info("updateUser status: " + userProfile.getStatus());
		String sql = null;
		if (userProfile.getStatus().equalsIgnoreCase("I")) {
			sql = updateQuery + updateClauseDeactivateDate + whereClauseEqUserId;
		}else {
			sql = updateQuery + updateClauseReactivateDate + whereClauseEqUserId;
		}
		logger.info("updateUser sql: " + sql);
		Object[] params = {userProfile.getStatus(),
				userProfile.getUserId()};
		int updCount = tlsJdbcTemplate.update(sql, params);
		logger.info("updateUser updCount: " + updCount);				
		userProfile = findByUserId(userProfile.getUserId());		
		return userProfile;
	}
	
	@Transactional
	@Override
	public UserProfile removeUser(UserProfile userProfile) {
		logger.info("removeUser userId: " + userProfile.getUserId());
		logger.info("removeUser status: " + userProfile.getStatus());
		String sql = null;
		String status = "I";
		if (userProfile.getStatus().equalsIgnoreCase("A")) {
			sql = updateQuery + updateClauseDeactivateDate + whereClauseEqUserId;		
			logger.info("updateUser sql: " + sql);
			Object[] params = {status,
					userProfile.getUserId()};
			int updCount = tlsJdbcTemplate.update(sql, params);
			logger.info("updateUser updCount: " + updCount);
			if (updCount > 0) {
				userProfile = findByUserId(userProfile.getUserId());
			}
		}
		return userProfile;
	}

	private List<UserProfile> getUserProfiles(List<Map<String, Object>> results){
		List<UserProfile> userProfiles = new ArrayList<>();
		try {
			for (Map<String, Object> mapRow: results) {
				UserProfile userProfile = tlsUserProfileHelper.getUserProfile(mapRow);	
				userProfiles.add(userProfile);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		logger.info("getUserProfiles userProfiles.toString(): "+ userProfiles.toString());
		return userProfiles;
	}
}
