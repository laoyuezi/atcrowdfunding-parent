package com.atguigu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.TAdmin;

public interface AdminService {

	TAdmin getUser(String loginacct, String userpswd);

	List<TAdmin> listAdminByPage(Map<String, Object> paramMap);

	void saveAdmin(TAdmin admin);

	TAdmin getAdminById(Integer id);

	void updateAdmin(TAdmin admin);

	void deleteAdminById(Integer id);

	void deleteAdminsByIds(String[] ids);

}
