package com.itx.crm.dao;

import com.itx.crm.base.BaseMapper;
import com.itx.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    //查询当前用户角色
    List<Map<String, Object>> queryAllRoles(Integer userId);
    //查询角色记录
    Role selectByRoleName(String roleName);
}