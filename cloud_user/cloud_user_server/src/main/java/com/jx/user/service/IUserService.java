package com.jx.user.service;


import com.jx.user.entity.User;

/**
 * @Author: zhangjx
 * @Date: 2020/6/18 21:46
 * @Description:
 */
public interface IUserService {


    /**
     * 通过电话号码查询用户
     * @param phoneNumber
     * @return
     */
    User getUserByPhoneNumber(String phoneNumber);



}
