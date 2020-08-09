package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TAdminExample.Criteria;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private TAdminMapper adminMapper;

	@Override
	public TAdmin getUser(String loginacct, String userpswd) {
		return adminMapper.selectByLogin(loginacct, userpswd);

	}

	//@PreAuthorize("hasRole('学徒')")
	@Override
	public List<TAdmin> listAdminByPage(Map<String, Object> paramMap) {

		TAdminExample example = new TAdminExample();
		String content = (String) paramMap.get("content");
		if (content != null) {
			// Criteria 用于面向对象查询方式
			// loginacct = ? and age = ?
			// select * from t_admin where loginacct like '%${content}%'
			// select * from t_admin where loginacct like '%?%'
			// select * from t_admin where loginacct like '%' + ? + '%'
			// select * from t_admin where loginacct like concat('%',#{content},'%')
			// select * from t_admin where loginacct like '%'||#{content}||'%'
			example.createCriteria().andLoginacctLike("%" + content + "%");

			Criteria c1 = example.createCriteria().andUsernameLike("%" + content + "%");

			Criteria c2 = example.createCriteria().andEmailLike("%" + content + "%");

			example.or(c1);
			example.or(c2);
		}

		return adminMapper.selectByExample(example);
		// return adminMapper.listAdminByPage(paramMap);
	}

	//@PreAuthorize("hasRole('学徒')")
	@Override
	public void saveAdmin(TAdmin admin) {
		adminMapper.insert(admin);
	}

	@Override
	public TAdmin getAdminById(Integer id) {
		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateAdmin(TAdmin admin) {
		// admin.setUserpswd(null);
		// admin.setCreatetime(null);
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	@Override
	public void deleteAdminById(Integer id) {
		adminMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteAdminsByIds(String[] ids) {

		TAdminExample example = new TAdminExample();

		List<Integer> idList = new ArrayList<Integer>();

		for (String id : ids) {
			idList.add(Integer.parseInt(id));
		}

		example.createCriteria().andIdIn(idList);

		adminMapper.deleteByExample(example);
	}

}
