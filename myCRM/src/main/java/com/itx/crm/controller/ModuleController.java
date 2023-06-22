package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.model.TreeModule;
import com.itx.crm.service.ModuleService;
import com.itx.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.swing.text.Style;
import java.util.List;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/15 9:28
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;


    /**
     * 功能记录视图页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "module/module";
    }

    /**
     * 功能列表视图
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> moduleList(){
        return moduleService.moduleList();
    }

    /**
     * 查询所有功能模块
     * @return
     */
    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeModule> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }

    /**
     * 添加资源菜单
     * @param module
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addModule(Module module){
        moduleService.addModule(module);
        return success("菜单添加成功！");
    }

    /**
     * 更新资源菜单
     * @param module
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateModule(Module module){
        moduleService.updateModule(module);
        return success("菜单更新成功！");
    }

    /**
     * 添加资源视图页面转发
     * @param grade
     * @param parentId
     * @param model
     * @return
     */
    @RequestMapping("addModulePage")
    public String addModulePage(Integer grade, Integer parentId, Model model){
        model.addAttribute("grade",grade);
        model.addAttribute("parentId",parentId);
        return "module/add";
    }

    /**
     * 更新资源视图页面转发
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("updateModulePage")
    public String updateModulePage(Integer id,Model model){
        model.addAttribute("module",moduleService.selectByPrimaryKey(id));
        return "module/update";
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule( Integer id){
        moduleService.deleteModuleById(id);
        return success("菜单删除成功！");
    }
}
