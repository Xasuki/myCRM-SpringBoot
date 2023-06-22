package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.query.RoleQuery;
import com.itx.crm.service.RoleService;
import com.itx.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/14 15:06
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    /**
     * 查询当前用户的角色信息
     * @param userId
     * @return
     */
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String ,Object>> queryAllRoles(Integer userId){
        return roleService.queryAllRoles(userId);
    }

    /**
     * 角色管理视图页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

    /**
     * 列表查询
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String ,Object> userList(RoleQuery query){
        return roleService.queryByParamsForTable(query);
    }

    /**
     * 角色添加
     * @param role
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addRole(Role role){
        roleService.addRole(role);
        return success("角色记录添加成功！");
    }

    /**
     * 角色更新
     * @param role
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("角色记录更新成功！");
    }

    /**
     * 添加/更新视图
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateRolePage")
    public String addOrUpdateRolePage(Integer id, Model model){
        if( null != id){
            model.addAttribute(roleService.selectByPrimaryKey(id));
        }
        return "role/add_update";
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer id){
        roleService.deleteRoleById(id);
        return success("角色记录删除成功！");
    }

    /**
     * 打开角色授权视图页面
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId,Model model){
        model.addAttribute("roleId",roleId);
        return "role/grant";
    }

    /**
     * 授权角色
     * @param mids
     * @param roleId
     * @return
     */
    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer [] mids,Integer roleId){
        roleService.addGrant(mids,roleId);
        System.out.println(mids);
        return success("角色权限添加成功！");
    }
}
