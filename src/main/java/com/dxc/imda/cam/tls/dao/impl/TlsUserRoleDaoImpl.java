package com.dxc.imda.cam.tls.dao.impl;

import java.util.ArrayList;
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

import com.dxc.imda.cam.common.constant.Enums.AppName;
import com.dxc.imda.cam.common.util.PageUtil;
import com.dxc.imda.cam.tls.dao.TlsUserRoleDao;
import com.dxc.imda.cam.tls.helper.TlsUserRoleHelper;
import com.dxc.imda.cam.tls.model.UserRole;

@Repository
public class TlsUserRoleDaoImpl implements TlsUserRoleDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate tlsJdbcTemplate;

	@Autowired
	private TlsUserRoleHelper tlsUserRoleHelper;
	
	@Autowired
	private PageUtil pageUtil;

	@Autowired
	public TlsUserRoleDaoImpl(JdbcTemplate tlsJdbcTemplate) {
		this.tlsJdbcTemplate = tlsJdbcTemplate;
	}

	private static final String countQuery = "SELECT count(userRole.USER_GP) "
		+ "FROM SC_USER_GP userRole ";

	private static final String sqlQuery = "SELECT userRole.* "
		+ "FROM SC_USER_GP userRole ";

	private static final String whereClauseEqRoleName = "WHERE UPPER(userRole.USER_GP) = ? ";
	private static final String whereClauseEqRoleDesc = "WHERE UPPER(userRole.USERGP_NAME) = ? ";

	private static final String whereClauseLikeRoleName = "WHERE UPPER(userRole.USER_GP) LIKE ? ";
	private static final String whereClauseLikeRoleDesc = "WHERE UPPER(userRole.USERGP_NAME) LIKE ? ";
	
	private static final String whereClauseActiveStatus = "WHERE userRole.STATUS = 'A' ";
	private static final String andClauseActiveStatus = "AND userRole.STATUS = 'A' ";
	
	private static final String whereClauseFilterRole = "WHERE UPPER(userRole.USER_GP) <> 'INTERNET' ";
	private static final String andClauseFilterRole = "AND UPPER(userRole.USER_GP) <> 'INTERNET' ";

	private static final String pageClause = "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";

	@Override
	public UserRole findByRoleName(String roleName) {
		UserRole userRole = new UserRole();
		try {
			String sql = sqlQuery + whereClauseEqRoleName + andClauseFilterRole;
			logger.info("findByRoleName sql: " + sql);
			Map<String, Object> mapRow = tlsJdbcTemplate.queryForMap(
					sql, new Object[]{roleName.toUpperCase()});
			userRole = tlsUserRoleHelper.getUserRole(mapRow);
		} catch (Exception e){
			e.printStackTrace();
			userRole = null;
		}
		return userRole;
	}

	/** Count **/

	@Override
	public Long countAll() {
		String sql = countQuery + whereClauseActiveStatus + andClauseActiveStatus + andClauseFilterRole;
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
	public Long countByRoleNameContaining(String roleName) {
		String sql = countQuery + whereClauseLikeRoleName + andClauseActiveStatus + andClauseFilterRole;
		return tlsJdbcTemplate.queryForObject(
				sql, Long.class, new Object[]{"%" + roleName.toUpperCase() + "%"});
	}

	@Override
	public Long countByRoleDescEquals(String roleDesc) {
		String sql = countQuery + whereClauseEqRoleDesc + andClauseActiveStatus + andClauseFilterRole;
		return tlsJdbcTemplate.queryForObject(
				sql, Long.class, new Object[]{roleDesc.toUpperCase()});
	}

	@Override
	public Long countByRoleDescContaining(String roleDesc) {
		String sql = countQuery + whereClauseLikeRoleDesc + andClauseActiveStatus + andClauseFilterRole;
		return tlsJdbcTemplate.queryForObject(
				sql, Long.class, new Object[]{"%" + roleDesc.toUpperCase() + "%"});
	}

	/** List **/
	
	@Override
	public List<UserRole> loadAllUserRole() {
		List<UserRole> userRoles = new ArrayList<>();
		try {
			String sql = sqlQuery + whereClauseActiveStatus + andClauseFilterRole;
			logger.info("loadAllUserRole sql: " + sql);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql);
			//logger.info("loadAllUserRole results.size(): " + results.size());
			userRoles = getUserRoles(results);
		} catch (Exception e){
			e.printStackTrace();
		}
		return userRoles;
	}

	@Override
	public Page<UserRole> findAll(Pageable pageable){
		List<UserRole> userRoles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseFilterRole + andClauseActiveStatus + orderByAndDirectionClause + pageClause;
			logger.info("findAll sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
					sql, new Object[] {pageNum - 1, pageable.getPageSize()});
			//logger.info("findAll results: " + results.size());
			userRoles = getUserRoles(results);
			logger.info("findAll userRoles: " + userRoles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserRole>(userRoles, pageable, userRoles.size());
	}

	@Override
	public Page<UserRole> findByRoleNameEquals(String roleName, Pageable pageable){
		List<UserRole> userRoles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseEqRoleName + andClauseActiveStatus + andClauseFilterRole + orderByAndDirectionClause + pageClause;
			logger.info("findByRoleNameEquals sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
					sql, new Object[]{roleName.toUpperCase(), pageNum - 1, pageable.getPageSize()});
			//logger.info("findByRoleNameEquals results.size(): " + results.size());
			userRoles = getUserRoles(results);
			logger.info("findByRoleNameEquals userRoles.size(): " + userRoles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserRole>(userRoles, pageable, userRoles.size());
	}

	@Override
	public Page<UserRole> findByRoleNameContaining(String roleName, Pageable pageable){
		List<UserRole> userRoles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseLikeRoleName + andClauseActiveStatus + andClauseFilterRole + orderByAndDirectionClause + pageClause;
			logger.info("findByRoleNameContaining sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[]{"%" + roleName.toUpperCase() + "%", pageNum - 1, pageable.getPageSize()});
			//logger.info("findByRoleNameContaining results.size(): " + results.size());
			userRoles = getUserRoles(results);
			logger.info("findByRoleNameContaining userRoles.size(): " + userRoles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserRole>(userRoles, pageable, userRoles.size());
	}

	@Override
	public Page<UserRole> findByRoleDescEquals(String roleDesc, Pageable pageable){
		List<UserRole> userRoles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseEqRoleDesc + andClauseActiveStatus + andClauseFilterRole + orderByAndDirectionClause + pageClause;
			logger.info("findByRoleDescEquals sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[]{roleDesc.toUpperCase(), pageNum - 1, pageable.getPageSize()});
			//logger.info("findByRoleDescEquals results.size(): " + results.size());
			userRoles = getUserRoles(results);
			logger.info("findByRoleDescEquals userRoles.size(): " + userRoles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserRole>(userRoles, pageable, userRoles.size());
	}

	@Override
	public Page<UserRole> findByRoleDescContaining(String roleDesc, Pageable pageable){
		List<UserRole> userRoles = new ArrayList<>();
		try {
			String orderByAndDirectionClause = pageUtil.getOrderByAndDirectionClause(AppName.TLS, pageable);
			String sql = sqlQuery + whereClauseLikeRoleDesc + andClauseActiveStatus + andClauseFilterRole + orderByAndDirectionClause + pageClause;
			logger.info("findByRoleDescContaining sql: " + sql);
			int pageNum = pageUtil.getPageNum(pageable);
			List<Map<String, Object>> results = tlsJdbcTemplate.queryForList(
				sql, new Object[]{"%" + roleDesc.toUpperCase() + "%", pageNum - 1, pageable.getPageSize()});
			//logger.info("findByRoleDescContaining results.size(): " + results.size());
			userRoles = getUserRoles(results);
			logger.info("findByRoleDescContaining userRoles.size(): " + userRoles.size());
		} catch (Exception e){
			e.printStackTrace();
		}
		return new PageImpl<UserRole>(userRoles, pageable, userRoles.size());
	}

	private List<UserRole> getUserRoles(List<Map<String, Object>> results){
		List<UserRole> userRoles = new ArrayList<>();
		try {			
			for (Map<String, Object> mapRow: results) {
				UserRole userRole = tlsUserRoleHelper.getUserRole(mapRow);				
				userRoles.add(userRole);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return userRoles;
	}
}
