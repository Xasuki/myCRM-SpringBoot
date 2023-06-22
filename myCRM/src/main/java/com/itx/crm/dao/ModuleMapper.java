package com.itx.crm.dao;

import com.itx.crm.base.BaseMapper;
import com.itx.crm.model.TreeModule;
import com.itx.crm.vo.Module;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module, Integer> {
    //查询所有资源权限
    List<TreeModule> queryAllModules();

    //查询资源权限
    List<Module> queryModules();

    //通过层级与模块名查询资源对象
    Module queryModuleByGradeAndModuleName(Integer grade, String moduleName);

    //通过层级与URL查询资源对象
    Module queryModuleByGradeAndUrl(Integer grade, String url);

    //通过权限码查询资源对象
    Module queryModuleByOptValue(String optValue);

    //查询指定资源是否存在子记录
    Integer countSubModuleParentId(Integer id);
}