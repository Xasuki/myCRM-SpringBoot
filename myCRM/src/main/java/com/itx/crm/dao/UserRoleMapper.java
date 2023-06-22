package com.itx.crm.dao;

import com.itx.crm.base.BaseMapper;
import com.itx.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    /**
     * 根据用户ID查询用户角色
     * @param userId
     * @return
     */
    Integer countUserRoleByUserId(Integer userId);

    /**
     * 根据用户ID删除用户角色
     * @param userId
     * @return
     */
    Integer deleteUserRoleByUserId(Integer userId);

}