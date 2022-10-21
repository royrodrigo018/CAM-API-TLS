package com.dxc.imda.cam.tls.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dxc.imda.cam.tls.dao.TlsUserRoleAccessDao;
import com.dxc.imda.cam.tls.helper.TlsUserRoleHelper;
import com.dxc.imda.cam.tls.model.UserRoleAccess;

@Repository
public class TlsUserRoleAccessDaoImpl implements TlsUserRoleAccessDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JdbcTemplate tlsJdbcTemplate;
	
	@Autowired
	private TlsUserRoleHelper tlsUserRoleHelper;
	
	private static final String sqlQuery = "SELECT "
		+ "userRoleAccess.USER_GP, userFunction.* "
		+ "FROM SC_FUNCTION_ACCESS userRoleAccess "
		+ "LEFT JOIN SC_FUNCTION userFunction "
		+ "ON userRoleAccess.FUNCTION_ID = userFunction.FUNCTION_ID ";
	
	private static final String whereClauseEqRoleName = "WHERE UPPER(userRoleAccess.USER_GP) = ? ";

	@Override
	public Set<UserRoleAccess> getUserRoleAccess(String roleName) {
		Set<UserRoleAccess> userRoleAccessSet = new HashSet<>();
		try {
			String sql = sqlQuery + whereClauseEqRoleName;
			logger.info("getUserRoleAccess sql: " + sql);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[]{roleName.toUpperCase()});
			//logger.info("getUserRoleAccess results.size(): " + results.size());
			for (Map<String, Object> mapRow: results) {
				UserRoleAccess userRoleAccess = tlsUserRoleHelper.getUserRoleAccess(mapRow);
				userRoleAccessSet.add(userRoleAccess);
			}
			logger.info("getUserRoleAccess userRoleAccessSet.size(): " + userRoleAccessSet.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return userRoleAccessSet;
	}

	@Override
	public Set<UserRoleAccess> loadUserRoleAccesses() {
		Set<UserRoleAccess> userRoleAccessSet = new HashSet<>();
		try {
			String sql = sqlQuery;
			logger.info("getUserRoleAccess sql: " + sql);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql);
			//logger.info("loadUserRoleAccesses results.size(): " + results.size());
			for (Map<String, Object> mapRow: results) {
				UserRoleAccess userRoleAccess = tlsUserRoleHelper.getUserRoleAccess(mapRow);
				userRoleAccessSet.add(userRoleAccess);
			}
			logger.info("loadUserRoleAccesses userRoleAccessSet.size(): " + userRoleAccessSet.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return userRoleAccessSet;
	}
}
