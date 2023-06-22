package com.itx.crm.service;


import com.itx.crm.base.BaseService;

import com.itx.crm.dao.ModuleMapper;
import com.itx.crm.dao.PermissionMapper;
import com.itx.crm.dao.RoleMapper;
import com.itx.crm.utils.AssertUtil;
import com.itx.crm.vo.Permission;
import com.itx.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/14 15:03
 */
@Service
public class RoleService extends BaseService<Role, Integer> {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 查询所有角色列表
     *
     * @return
     */
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleMapper.queryAllRoles(userId);
    }


    /**
     * 添加角色
     *
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRole(Role role) {
        //1 参数校验
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "请输入角色名！");
        AssertUtil.isTrue(null != roleMapper.selectByRoleName(role.getRoleName()), "该角色已存在！");
        //2 设置默认值
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        //3 执行操作
        AssertUtil.isTrue(roleMapper.insertSelective(role) < 1, "角色添加失败！");
    }


    /**
     * 更新角色
     *
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role) {
        //1 参数校验
        AssertUtil.isTrue(null == role.getId() || null == selectByPrimaryKey(role.getId()), "待修改记录不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "请输入角色名！");
        Role temp = roleMapper.selectByRoleName(role.getRoleName());
        AssertUtil.isTrue(null != temp && temp.getId().equals(role.getId()), "该角色已存在！");
        //2 设置默认值
        role.setUpdateDate(new Date());
        //3 执行操作
        AssertUtil.isTrue(updateByPrimaryKeySelective(role) < 1, "角色记录更新失败！");
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRoleById(Integer id) {
        Role role = selectByPrimaryKey(id);
        AssertUtil.isTrue(null == id || null == role, "待删除的记录不存在！");
        role.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(role) < 1, "角色记录删除失败！");
    }

    /**
     * 添加角色权限
     *
     * @param mids
     * @param roleId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer[] mids, Integer roleId) {
        Role temp = selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null == roleId || null ==temp , "待授权的角色不存在！");
        Integer count = permissionMapper.countPermissionByRoleId(roleId);
        if (count > 0) {
            AssertUtil.isTrue(permissionMapper.deletePermissionByRoleId(roleId) < count, "权限分配失败！");
        }
        if (null != mids && mids.length > 0) {
            List<Permission> permissions = new ArrayList<>();
            for (Integer mid : mids) {
                Permission permission = new Permission();
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setModuleId(mid);
                permission.setRoleId(roleId);
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permissions.add(permission);
            }
           AssertUtil.isTrue( permissionMapper.insertBatch(permissions)<1,"角色授权失败！");
        }
    }
}
