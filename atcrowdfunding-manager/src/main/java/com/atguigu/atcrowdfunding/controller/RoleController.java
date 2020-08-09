package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.atguigu.atcrowdfunding.util.Data;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {

	@Autowired
	RoleService roleService;

	@ResponseBody
	@RequestMapping("/role/listPermissionIdByRoleId")
	public List<Integer> listPermissionIdByRoleId(Integer roleId) {
		List<Integer> list = roleService.listPermissionIdByRoleId(roleId);
		return list;
	}

	@ResponseBody
	@RequestMapping("/role/doAssignPermissionToRole")
	public String doAssignPermissionToRole(Data ds, Integer roleId) {
		roleService.saveRoleAndPermissionRelationship(roleId, ds.getIds());
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/role/delete")
	public String delete(Integer id) {
		roleService.deleteRole(id);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/role/update")
	public String update(TRole role) {
		roleService.updateRole(role);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/role/get")
	public TRole get(Integer roleId) {
		return roleService.getRoleById(roleId);
	}

	@PreAuthorize("hasRole('学徒')")
	@ResponseBody
	@RequestMapping("/role/save")
	public String save(TRole role) {
		roleService.saveRole(role);
		return "ok";
	}

	@RequestMapping("/role/index")
	public String index() {
		return "role/index";
	}

	// 如果返回是bean对象，那么，根据消息转换接口(HttpMessageConverter)，
	// 会采取(MappingJackson2HttpMessageConverter)进行转换，会将对象转换为json串

	// 如果返回是String对象，那么，根据消息转换接口(HttpMessageConverter)，
	// 会采取(StringHttpMessageConverter)进行转换，将字符原样输出
	@ResponseBody // 返回数据进行消息转换
	@RequestMapping("/role/loadData")
	public PageInfo<TRole> loadData(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "condition", required = false, defaultValue = "") String condition) {

		PageHelper.startPage(pageNum, pageSize);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("condition", condition);

		List<TRole> roleList = roleService.listRolePage(paramMap);

		PageInfo<TRole> page = new PageInfo<TRole>(roleList, 5);

		return page;
	}

}
