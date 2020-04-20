package com.buymypoem.springmvc.service;

import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userDao.getUserByLogin(login);
        Set<GrantedAuthority> grantedAuthority = new HashSet<>();
        Map<String, String> roles = new HashMap<String, String>();
        roles.put("Author", "ROLE_USER_AUTHOR");
        roles.put("Customer", "ROLE_USER_CUSTOMER");
        roles.put("Service", "ROLE_USER_SERVICE");

        grantedAuthority.add(new SimpleGrantedAuthority(roles.get(user.getRole())));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthority);
    }
}
