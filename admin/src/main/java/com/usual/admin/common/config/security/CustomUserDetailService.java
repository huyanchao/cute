package com.usual.admin.common.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        User user = userService.findByUsername(username);
//
//        if(user == null){
//            throw new UsernameNotFoundException("用户名："+ username + "不存在！");
//        }
//        Collection<SimpleGrantedAuthority> collection = new HashSet<SimpleGrantedAuthority>();
//        Iterator<Role> iterator =  user.getList().iterator();
//        while (iterator.hasNext()){
//            collection.add(new SimpleGrantedAuthority(iterator.next().getRole_name()));
//        }
//
//        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),collection);
        return null;
    }
}
