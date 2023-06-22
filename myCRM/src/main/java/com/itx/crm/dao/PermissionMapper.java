package com.itx.crm.dao;

import com.itx.crm.base.BaseMapper;
import com.itx.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {


    /**
     * 根据用户ID查询角色权限
     * @param roleId
     * @return
     */
    Integer countPermissionByRoleId(Integer roleId);

    /**
     * 根据用户ID删除角色权限
     * @param roleId
     * @return
     */
    Integer deletePermissionByRoleId(Integer roleId);

    /**
     * 查询当前用户的资源权限
     * @param roleId
     * @return
     */
    List<Integer> queryRoleHasAllModuleIdsByRoleId(Integer roleId);

    /**
     * 登录⽤⼾⻆⾊拥有权限查询实现
     * @param userId
     * @return
     */
    List<String> queryUserHasRolesHasPermissions(Integer userId);

    /**
     * 通过资源ID查询权限记录
     * @param id
     * @return
     */
    Integer countPermissionsByModuleId(Integer id);

    /**
     * 通过资源ID删除权限记录
     * @param id
     * @return
     */
    Integer deletePermissionByModuleId(Integer id);
}