package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.model.UserModel;
import com.itx.crm.query.UserQuery;
import com.itx.crm.service.UserService;
import com.itx.crm.utils.LoginUserUtil;
import com.itx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/9 11:41
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userName
     * @param uPwd
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String uPwd) {
        ResultInfo resultInfo = new ResultInfo();
        UserModel userModel = userService.userLogin(userName, uPwd);
        resultInfo.setResult(userModel);
        return resultInfo;
    }

    /**
     * 修改密码
     *
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @PostMapping("updatePassword")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,
                                         String oldPassword, String newPassword,
                                         String confirmPassword) {
        ResultInfo resultInfo = new ResultInfo();
        //获取用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //调用service层更新密码方法
        userService.updateUserPassword(userId, oldPassword, newPassword, confirmPassword);
        return resultInfo;
    }

    /**
     * 打开修改密码视图
     *
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }

    /**
     * 查询所有销售人员
     *
     * @return
     */
    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> queryAllSales() {
        return userService.queryAllSales();
    }

    /**
     * 查询所有客户经理
     *
     * @return
     */
    @RequestMapping("queryAllCustomerManagers")
    @ResponseBody
    public List<Map<String, Object>> queryAllCustomerManagers() {
        return userService.queryAllCustomerManagers();
    }

    /**
     * 多条件查询用户数据
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryUserByParams(UserQuery query) {
        return userService.queryUserByParams(query);
    }

    /**
     * 进入用户页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "user/user";
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addUser(User user){
        userService.addUser(user);
        return success("添加用户成功！");
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("更新用户成功！");
    }

    /**
     * 进入用户添加/更新视图页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("openAddOrUpdateUserPage")
    public String openAddOrUpdateUserPage(Integer id, Model model){
        if(null != id){
            model.addAttribute("userInfo",userService.selectByPrimaryKey(id));
        }
        return "user/add_update";
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer [] ids){
        userService.deleteUser(ids);
        return success("删除用户成功！");
    }
}
