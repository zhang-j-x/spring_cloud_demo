package com.jx.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jx.user.dao.RoleMapper;
import com.jx.user.dao.UserMapper;
import com.jx.user.entity.Role;
import com.jx.user.entity.User;
import com.jx.user.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhangjx
 * @Date: 2020/6/18 21:47
 * @Description:
 */
@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername,userName));
        if(user == null){
            throw new UsernameNotFoundException("用户不存在!");
        }
        List<Role> roles = roleMapper.getRolesByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }


    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getTel,phoneNumber));
        return user;
    }




}
