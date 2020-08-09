package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.bean.TRoleExample;
import com.atguigu.atcrowdfunding.bean.TRolePermissionExample;
import com.atguigu.atcrowdfunding.mapper.TAdminRoleMapper;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.mapper.TRolePermissionMapper;
import com.atguigu.atcrowdfunding.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	TRoleMapper roleMapper;

	@Autowired
	TAdminRoleMapper adminRoleMapper;

	@Autowired
	TRolePermissionMapper rolePermissionMapper;

	@Override
	public List<TRole> listRolePage(Map<String, Object> paramMap) {

		String condition = (String) paramMap.get("condition");

		TRoleExample example = new TRoleExample();

		if (!StringUtils.isEmpty(condition)) {
			example.createCriteria().andNameLike("%" + condition + "%");
		}

		List<TRole> list = roleMapper.selectByExample(example);

		return list;
	}

	@Override
	public void saveRole(TRole role) {
		roleMapper.insertSelective(role); // 动态sql,为null属性不参与insert语句生成的。
	}

	@Override
	public TRole getRoleById(Integer roleId) {
		return roleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public void updateRole(TRole role) {
		roleMapper.updateByPrimaryKeySelective(role); // 动态sql,属性值不为null,就参与修改操作。
	}

	@Override
	public void deleteRole(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<TRole> listAll() {
		return roleMapper.selectByExample(null);
	}

	@Override
	public List<Integer> listRoleIdByAdminId(Integer id) {
		return adminRoleMapper.listRoleIdByAdminId(id);
	}

	@Override
	public void saveAdminAndRoleRelationship(Integer adminId, Integer[] roleId) {
		adminRoleMapper.saveAdminAndRoleRelationship(adminId, roleId);
	}

	@Override
	public void deleteAdminAndRoleRelationship(Integer adminId, Integer[] roleId) {
		adminRoleMapper.deleteAdminAndRoleRelationship(adminId, roleId);
	}

	@Override
	public void saveRoleAndPermissionRelationship(Integer roleId, List<Integer> ids) {
		TRolePermissionExample example = new TRolePermissionExample();
		example.createCriteria().andRoleidEqualTo(roleId);
		// 保存角色和许可关系数据前，将以前分配的许可关系数据删除
		rolePermissionMapper.deleteByExample(example);
		if (ids!=null && ids.size()>0) {
			// 重新保存最新关系数据。（这样，不必区分哪些id是需要保存，哪些id需要删除，以及哪些id是不动的。）
			rolePermissionMapper.saveRoleAndPermissionRelationship(roleId, ids);
		}
	}

	@Override
	public List<Integer> listPermissionIdByRoleId(Integer roleId) {
		return rolePermissionMapper.listPermissionIdByRoleId(roleId);
	}

}
