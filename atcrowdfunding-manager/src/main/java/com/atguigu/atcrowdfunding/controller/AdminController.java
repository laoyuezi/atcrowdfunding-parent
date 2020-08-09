package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@ResponseBody
	@RequestMapping("/doAssignRoleToAdmin")
	public String doAssignRoleToAdmin(Integer adminId, Integer[] roleId, Model model) {

		roleService.saveAdminAndRoleRelationship(adminId, roleId);

		return "ok";
	}

	@ResponseBody
	@RequestMapping("/doUnAssignRoleToAdmin")
	public String doUnAssignRoleToAdmin(Integer adminId, Integer[] roleId, Model model) {
		roleService.deleteAdminAndRoleRelationship(adminId, roleId);
		return "ok";
	}

	@RequestMapping("/assignRole")
	public String assignRole(Integer id, Model model) {
		// 1.查询所有角色
		List<TRole> allRoleList = roleService.listAll();

		// 2.查询该用户所拥有的角色id
		List<Integer> selfRoleIdList = roleService.listRoleIdByAdminId(id);

		List<TRole> assignList = new ArrayList<TRole>();
		List<TRole> unAssignList = new ArrayList<TRole>();

		model.addAttribute("assignList", assignList);
		model.addAttribute("unAssignList", unAssignList);

		for (TRole role : allRoleList) {

			if (selfRoleIdList.contains(role.getId())) {// 已分配
				// 3.获得已分配角色集合
				assignList.add(role);
			} else { // 未分配
				// 4.获得未分配角色集合
				unAssignList.add(role);
			}
		}

		return "admin/assignRole";
	}

	@RequestMapping("/batchremove")
	public String batchremove(String ids) {
		String[] idArray = ids.split(",");
		adminService.deleteAdminsByIds(idArray);

		return "redirect:/admin/index";
	}

	@RequestMapping("/remove")
	public String remove(Integer id) {

		adminService.deleteAdminById(id);
		return "redirect:/admin/index";
	}

	@RequestMapping("/update")
	public String update(TAdmin admin, Integer pageno) {

		adminService.updateAdmin(admin);

		return "redirect:/admin/index?pageno=" + pageno;
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/edit")
	public String edit(Integer id, Model model) {

		TAdmin admin = adminService.getAdminById(id);

		model.addAttribute("admin", admin);

		return "admin/edit";
	}

	/**
	 * 新增用户数据
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping("/insert")
	public String insert(TAdmin admin) {

		// 补充数据
		//admin.setUserpswd(MD5Util.digest(Const.DEFALUT_PASSWORD));
		admin.setUserpswd(new BCryptPasswordEncoder().encode(Const.DEFALUT_PASSWORD));

		// 日期格式转换
		admin.setCreatetime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

		// 保存数据
		adminService.saveAdmin(admin);

		return "redirect:/admin/index?pageno=" + Integer.MAX_VALUE;
	}

	@RequestMapping("/add")
	public String add() {
		return "admin/add";
	}

	/**
	 * 跳转到用户列表页面
	 * 
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	@PreAuthorize("hasRole('学徒')")
	//@PreAuthorize("hasRole('学徒') and hasAuthority('xxx')")
	@RequestMapping("/index")
	public String index(@RequestParam(name = "pageno", required = false, defaultValue = "1") Integer pageno,
			@RequestParam(name = "pagesize", required = false, defaultValue = "10") Integer pagesize, String content,
			Model model) {

		PageHelper.startPage(pageno, pagesize);

		// 分页查询用户的信息
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("start", (pageno-1)*pagesize);
		// paramMap.put("pagesize", pagesize);
		paramMap.put("content", content);

		List<TAdmin> admins = adminService.listAdminByPage(paramMap);

		PageInfo<TAdmin> pageInfo = new PageInfo<TAdmin>(admins, 5);

		model.addAttribute("pageInfo", pageInfo);

		return "admin/index";
	}


}
