package com.itx.crm.service;

import com.itx.crm.base.BaseService;
import com.itx.crm.dao.UserRoleMapper;
import com.itx.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/14 15:54
 */
@Service
public class UserRoleService extends BaseService<UserRole, Integer> {
    @Resource
    private UserRoleMapper userRoleMapper;
}
