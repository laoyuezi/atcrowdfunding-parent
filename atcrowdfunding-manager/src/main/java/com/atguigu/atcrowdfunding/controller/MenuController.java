package com.atguigu.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.service.MenuService;
import com.atguigu.atcrowdfunding.service.PermissionService;

@Controller
public class MenuController {

	@Autowired
	MenuService menuService;

	@Autowired
	PermissionService permissionService;

	@ResponseBody
	@GetMapping("/menu/menu_permission")
	public List<TPermission> getPermissionByMenuid(@RequestParam("menuid") Integer mid) {
		// 查询出当前菜单能被哪些权限（自定义标识）操作
		return permissionService.getPermissionByMenuid(mid);
	}

	/**
	 * 为菜单分配权限 {mid: "3", perIds: "1,2,4,5,6"}
	 */
	@ResponseBody
	@PostMapping("/menu/assignPermissionToMenu")
	public String assignPermissionToMenu(@RequestParam("mid") Integer mid, @RequestParam("perIds") String perIds) {
		// 权限id的集合
		List<Integer> perIdArray = new ArrayList<>();
		String[] split = perIds.split(",");
		for (String str : split) {
			int id;
			try {
				id = Integer.parseInt(str);
				perIdArray.add(id);
			} catch (NumberFormatException e) {
			}
		}
		// 1、将菜单和权限id集合的关系保存起来
		permissionService.assignPermissionToMenu(mid, perIdArray);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/menu/delete")
	public String delete(Integer id) {
		menuService.deleteTMenu(id);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/menu/update")
	public String update(TMenu menu) {
		menuService.updateTMenu(menu);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/menu/get")
	public TMenu get(Integer id) {
		TMenu menu = menuService.getTMenu(id);
		return menu;
	}

	@RequestMapping("/menu/index")
	public String index() {
		return "menu/index";
	}

	@ResponseBody
	@RequestMapping("/menu/loadTree")
	public List<TMenu> loadTree() {
		List<TMenu> list = menuService.listAll();
		return list; // json格式 树 的数据
	}

	@ResponseBody
	@RequestMapping("/menu/save")
	public String save(TMenu menu) {
		menuService.saveTMenu(menu);
		return "ok";
	}

}
