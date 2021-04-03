package com.jx.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jx.user.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangjx
 * @since 2020-06-21
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过用户id查询角色信息
     * @param userId
     * @return
     */
    List<Role> getRolesByUserId(@Param("userId") Integer userId);
}
