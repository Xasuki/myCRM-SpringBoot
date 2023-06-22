package com.itx.crm.service;

import com.itx.crm.base.BaseService;
import com.itx.crm.dao.PermissionMapper;
import com.itx.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/15 11:25
 */
@Service
public class PermissionService extends BaseService<Permission,Integer> {
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 登录⽤⼾⻆⾊拥有权限查询实现
     * @param userId
     * @return
     */
    public List<String> queryUserHasRolesHasPermissions(Integer userId) {
        return permissionMapper.queryUserHasRolesHasPermissions(userId);
    }
}
