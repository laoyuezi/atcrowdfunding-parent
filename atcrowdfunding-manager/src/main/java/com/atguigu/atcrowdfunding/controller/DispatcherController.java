package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DispatcherController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private AdminService adminService;

	@RequestMapping("/unauth")
	public String unauth() {
		return "unauth";
	}

	@RequestMapping("/main")
	public String main(HttpSession session) {

		// 读取菜单数据
		List<TMenu> menus = menuService.listMenu();
		Map<Integer, TMenu> menuMap = new HashMap<Integer, TMenu>();
		
		List<TMenu> rootMenus = new ArrayList<TMenu>();
		
		for ( TMenu menu : menus ) {
			menuMap.put(menu.getId(), menu);
			if ( menu.getPid() == 0 ) {
				rootMenus.add(menu);
			}
		}
		
		for ( TMenu menu : menus ) {
			// 子菜单
			TMenu childMenu = menu;
			// 父菜单
			TMenu parentMenu = menuMap.get(childMenu.getPid());
			if (parentMenu != null) {
				// 组合父子菜单的关系
				parentMenu.getChildren().add(childMenu);
			}
		}
		
		// 将菜单形成上下级的父子关系
		// 假设每一个菜单都是子菜单
		/*
		for ( TMenu menu : menus ) {

			// 子菜单
			TMenu childMenu = menu;
			// 查找父菜单
			Integer pid = childMenu.getPid();
			
			if ( pid == 0 ) {
				rootMenus.add(menu);
			} else {
				for ( TMenu innerMenu : menus ) {
					if ( pid.equals(innerMenu.getId()) ) {
						// 父菜单
						TMenu parentMenu = innerMenu;
						// 建立父子菜单的关系
						parentMenu.getChildmenus().add(childMenu);
						break;
					}
				}		
			}
		
		}
		*/
		
		
		// 保存菜单信息
		session.setAttribute("menus", rootMenus);
		
		return "main";
	}
	
	
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}

//	@RequestMapping("/login")
//	//public String login(String loginacct, String userpswd) {
//	public String login(TAdmin admin, HttpSession session) {
//		session.removeAttribute("errorMsg");
//
//		TAdmin dbAdmin = adminService.getUser(admin.getLoginacct(), MD5Util.digest(admin.getUserpswd()));
//
//		if ( dbAdmin != null ) {
//			// model的数据存储保存在请求范围中。所以重定向无法共享数据
//			// 将数据保存到Session中
//			session.setAttribute("dbAdmin", dbAdmin);
//			return "redirect:/main";
//		} else {
//
//			String errorMsg = "用户账号或密码不正确，请重新输入";
//
//			session.setAttribute("errorMsg", errorMsg);
//
//		    return "redirect:/";
//		}
//	}
}
