package com.atguigu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.TRole;

public interface RoleService {

	List<TRole> listRolePage(Map<String, Object> paramMap);

	void saveRole(TRole role);

	TRole getRoleById(Integer roleId);

	void updateRole(TRole role);

	void deleteRole(Integer id);

	List<TRole> listAll();

	List<Integer> listRoleIdByAdminId(Integer id);

	void saveAdminAndRoleRelationship(Integer adminId, Integer[] roleId);

	void deleteAdminAndRoleRelationship(Integer adminId, Integer[] roleId);

	void saveRoleAndPermissionRelationship(Integer roleId, List<Integer> ids);

	List<Integer> listPermissionIdByRoleId(Integer roleId);

}
