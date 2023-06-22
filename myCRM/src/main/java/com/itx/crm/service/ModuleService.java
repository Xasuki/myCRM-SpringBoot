package com.itx.crm.service;

import com.itx.crm.base.BaseService;
import com.itx.crm.dao.ModuleMapper;
import com.itx.crm.dao.PermissionMapper;
import com.itx.crm.model.TreeModule;
import com.itx.crm.utils.AssertUtil;
import com.itx.crm.vo.Module;
import com.itx.crm.vo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/15 9:29
 */
@Service
public class ModuleService extends BaseService<Module, Integer> {
    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 查询所有资源模块
     *
     * @return
     */
    public List<TreeModule> queryAllModules(Integer roleId) {
        List<TreeModule> treeModules = moduleMapper.queryAllModules();
        List<Integer> roleHasMids = permissionMapper.queryRoleHasAllModuleIdsByRoleId(roleId);
        if (null != roleHasMids && roleHasMids.size() > 0) {
            treeModules.forEach(treeModule -> {
                if (roleHasMids.contains(treeModule.getId())) {
                    treeModule.setChecked(true);
                }
            });
        }
        return treeModules;
    }

    /**
     * 资源列表显示
     *
     * @return
     */
    public Map<String, Object> moduleList() {
        Map<String, Object> map = new HashMap<>();
        List<Module> modules = moduleMapper.queryModules();
        map.put("code", 0);
        map.put("count", modules.size());
        map.put("data", modules);
        map.put("msg", "");
        return map;
    }

    /**
     * 菜单添加
     *
     * @param module
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addModule(Module module) {
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "请输入菜单名！");
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2), "菜单层级不合法！");
        AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndModuleName(grade, module.getModuleName()), "该层级下菜单已存在！");
        if (grade == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "请指定二级菜单url值！");
            AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndUrl(grade, module.getUrl()), "二级菜单url已存在！");
        }
        if (grade != 0) {
            Integer parentId = module.getParentId();
            AssertUtil.isTrue(null == parentId || null == selectByPrimaryKey(parentId), "请指定上级菜单！");
            AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "请输入权限码！");
            AssertUtil.isTrue(null != moduleMapper.queryModuleByOptValue(module.getOptValue()), "权限码重复！");
        }
        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(module) < 1, "菜单添加失败！");
    }

    /**
     * 菜单更新
     *
     * @param module
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module) {
        AssertUtil.isTrue(null == module.getId() || null == selectByPrimaryKey(module.getId()), "待更新的记录不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "请指定菜单名称!");
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 | grade == 2), "菜单层级不合法！");
        Module temp = moduleMapper.queryModuleByGradeAndModuleName(grade, module.getModuleName());
        if (null != temp) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "该层级已存在！");
        }
        if (grade == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "请指定二级菜单url！");
            temp = moduleMapper.queryModuleByGradeAndUrl(grade, module.getUrl());
            if (null != temp) {
                AssertUtil.isTrue(temp.getId().equals(module.getId()), "该层级下url已存在！");
            }
        }
        if (grade != 0) {
            Integer parentId = module.getParentId();
            AssertUtil.isTrue(null == parentId || null == selectByPrimaryKey(parentId), "请指定上级菜单！");
            AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "请输入权限码！");
            temp = moduleMapper.queryModuleByOptValue(module.getOptValue());
            if (null != temp) {
                AssertUtil.isTrue(temp.getId().equals(module.getId()), "权限码已存在！");
            }
            module.setUpdateDate(new Date());
            AssertUtil.isTrue(updateByPrimaryKeySelective(module) < 1, "菜单更新失败！");
        }
    }

    /**
     * 菜单删除
     *
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModuleById(Integer id) {
        Module temp = moduleMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == id || null == temp, "待删除记录不存在！");
        //如果存在子菜单 不能删
        Integer count = moduleMapper.countSubModuleParentId(id);
        AssertUtil.isTrue(0 < count, "存在子菜单，不可以删除！");
        //如果权限表中有值 删除值
        count = permissionMapper.countPermissionsByModuleId(id);
        if (count > 0) {
            AssertUtil.isTrue(permissionMapper.deletePermissionByModuleId(id) < 1, "菜单删除失败！");
        }
        temp.setIsValid((byte) 0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp) < 1, "菜单删除失败！");

    }

}
