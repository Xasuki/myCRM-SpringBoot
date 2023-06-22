package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.dao.PermissionMapper;
import com.itx.crm.service.PermissionService;
import com.itx.crm.service.UserService;
import com.itx.crm.utils.LoginUserUtil;
import com.itx.crm.vo.Permission;
import com.itx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/9 9:23
 */
@Controller
public class IndexController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    /**
     * 系统登录⻚
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    // 系统界⾯欢迎⻚
    @RequestMapping("welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * 后端管理主⻚⾯
     *
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request) {
        //通过工具类，获取cookie中获取userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.selectByPrimaryKey(userId);
        request.getSession().setAttribute("user",user);
        //登录⽤⼾⻆⾊拥有权限查询实现
        List<String> permissions = permissionService.queryUserHasRolesHasPermissions(userId);
        request.getSession().setAttribute("permissions",permissions);
        return "main";
    }

}
