package com.atguigu.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.mapper.TMenuMapper;
import com.atguigu.atcrowdfunding.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private TMenuMapper menuMapper;

	@Override
	public List<TMenu> listMenu() {
		return menuMapper.selectMenus();
	}

	@Override
	public List<TMenu> listAll() {
		return menuMapper.selectByExample(null); // select * from t_menu
	}

	@Override
	public void saveTMenu(TMenu menu) {
		menuMapper.insertSelective(menu);
	}

	@Override
	public TMenu getTMenu(Integer id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTMenu(TMenu menu) {
		menuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public void deleteTMenu(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
	}
}
