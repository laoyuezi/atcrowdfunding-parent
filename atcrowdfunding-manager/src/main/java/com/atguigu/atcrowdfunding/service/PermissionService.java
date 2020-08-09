package com.atguigu.atcrowdfunding.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.TPermission;

public interface PermissionService {

	List<TPermission> getAllPermissions();

	void savePermission(TPermission permission);

	void deletePermission(Integer id);

	void editPermission(TPermission permission);

	TPermission getPermissionById(Integer id);

	List<TPermission> getPermissionByMenuid(Integer mid);

	void assignPermissionToMenu(Integer mid, List<Integer> perIdArray);
}
