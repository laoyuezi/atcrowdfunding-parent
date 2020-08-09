package com.atguigu.atcrowdfunding.component;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

public class TAdminUser extends User {

    TAdmin admin;

    Set<GrantedAuthority> authorities;

    public TAdminUser(TAdmin admin , Set<GrantedAuthority> authorities){
        //super(admin.getLoginacct(),admin.getUserpswd(),authorities);
        super(admin.getLoginacct(),admin.getUserpswd(),true,true,true,true,authorities);
        this.admin = admin ;
        this.authorities = authorities;
    }

    public TAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(TAdmin admin) {
        this.admin = admin;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
