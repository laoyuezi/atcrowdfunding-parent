package com.atguigu.atcrowdfunding.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.TMenu;

public interface MenuService {

	List<TMenu> listMenu();

	List<TMenu> listAll();

	void saveTMenu(TMenu menu);

	TMenu getTMenu(Integer id);

	void updateTMenu(TMenu menu);

	void deleteTMenu(Integer id);

}
