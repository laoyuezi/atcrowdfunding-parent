package com.atguigu.atcrowdfunding.component;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TPermissionMapper;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    TAdminMapper adminMapper;

    @Autowired
    TRoleMapper roleMapper;

    @Autowired
    TPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //1.根据用户名称查询用户信息
        TAdminExample example = new TAdminExample();
        example.createCriteria().andLoginacctEqualTo(username);
        List<TAdmin> list = adminMapper.selectByExample(example);

        if(list == null || list.size() == 0){
            return null ;
        }

        TAdmin admin = list.get(0);
        Integer adminId = admin.getId();

        //2.根据用户id查询角色
        List<TRole> roleList = roleMapper.listRoleByAdminId(adminId);


        //3.根据用户id查询许可
        List<TPermission> permissionList = permissionMapper.listPermissionByAdminId(adminId);

        //4.构建权限集合
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        //用角色表示权限，需要增加"ROLE_"前缀
        for (TRole role : roleList) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }

        for (TPermission permission : permissionList) {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }

        //5.返回查询结果，交给框架进行认证
        //User user = new User(admin.getLoginacct(),admin.getUserpswd(),authorities);
        //return user;
        return new TAdminUser(admin,authorities);
    }
}
