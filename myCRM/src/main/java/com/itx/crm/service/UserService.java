package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.UserMapper;
import com.itx.crm.dao.UserRoleMapper;
import com.itx.crm.model.UserModel;
import com.itx.crm.query.UserQuery;
import com.itx.crm.utils.AssertUtil;
import com.itx.crm.utils.Md5Util;
import com.itx.crm.utils.UserIDBase64;
import com.itx.crm.vo.User;
import com.itx.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/9 10:53
 */
@Service
public class UserService extends BaseService<User, Integer> {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 用户登录
     *
     * @param userName
     * @param uPwd
     * @return
     */
    public UserModel userLogin(String userName, String uPwd) {
        //参数校验
        checkLoginParams(userName, uPwd);
        //调用dao查询用户
        User user = userMapper.queryUserByUserName(userName);
        //用户校验
        AssertUtil.isTrue(user == null, "该用户不存在或已注销！");
        //密码校验
        checkLoginPwd(uPwd, user.getUserPwd());
        return buildUserInfo(user);

    }

    /**
     * 修改密码
     *
     * @param userId          用户id
     * @param oldPassword     旧密码
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        //根据id查询用户
        User user = userMapper.selectByPrimaryKey(userId);
        //参数校验
        checkPasswordParams(user, oldPassword, newPassword, confirmPassword);
        //设置新密码
        user.setUserPwd(Md5Util.encode(newPassword));
        //执行更新操作
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "更新密码失败！");

    }

    /**
     * 验证⽤户密码修改参数
     * ⽤户ID：userId ⾮空 ⽤户对象必须存在
     * 原始密码：oldPassword ⾮空 与数据库中密⽂密码保持⼀致
     * 新密码：newPassword ⾮空 与原始密码不能相同
     * 确认密码：confirmPassword ⾮空 与新密码保持⼀致
     *
     * @param user
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    private void checkPasswordParams(User user, String oldPassword, String newPassword, String confirmPassword) {
        AssertUtil.isTrue(user == null, "用户未登录或不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "请输入原密码！");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "请输入新密码！");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword), "请输入确认密码！");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPassword)), "原密码不正确！");
        AssertUtil.isTrue(oldPassword.equals(newPassword), "新密码与原密码相同！");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword), "新密码与确认密码不相同！");
    }

    /**
     * 构造用户信息 返回给前端
     *
     * @param user
     * @return
     */
    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserId(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    /**
     * 查询所有销售人员
     *
     * @return
     */
    public List<Map<String, Object>> queryAllSales() {
        return userMapper.queryAllSales();
    }

    /**
     * 查询所有客户经理
     *
     * @return
     */
    public List<Map<String, Object>> queryAllCustomerManagers() {
        return userMapper.queryAllCustomerManagers();
    }
    /**
     * 多条件分页查询用户数据
     *
     * @param query
     * @return
     */
    public Map<String, Object> queryUserByParams(UserQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectByParams(query));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 新增用户
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        //1 参数检验
        cheackParams(user.getUserName(), user.getEmail(), user.getPhone());
        //2 设置默认值
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        //3 执行添加操作
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1, "用户添加失败！");
        //4 ⽤户⻆⾊分配
        relaionUserRole(user.getId(), user.getRoleIds());
    }

    /**
     * 更新用户
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        //1 参数校验
        User temp = userMapper.selectByPrimaryKey(user.getId());
        AssertUtil.isTrue(temp == null,"待更新记录不存在！");
        cheackParams(user.getUserName(),user.getEmail(),user.getPhone(), user.getId());
        //2  设置参数
        user.setUpdateDate(new Date());
        //3  执行操作
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"用户更新失败！");
        //4 ⽤户⻆⾊分配
        relaionUserRole(user.getId(), user.getRoleIds());
    }

    /**
     * 删除用户
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(Integer [] ids) {
        AssertUtil.isTrue(null == ids || ids.length == 0 ,"请选择待删除的记录！");
        AssertUtil.isTrue(userMapper.deleteBatch(ids)<1,"用户记录删除失败！");
    }

    /**
     * 用户角色关联
     * @param userId
     * @param roleIds
     */
    public void relaionUserRole(Integer userId, String roleIds){
        /**
         * ⽤户⻆⾊分配
         * 原始⻆⾊不存在 添加新的⻆⾊记录
         * 原始⻆⾊存在 添加新的⻆⾊记录
         * 原始⻆⾊存在 清空所有⻆⾊
         * 如何进⾏⻆⾊分配???
         * 如果⽤户原始⻆⾊存在 ⾸先清空原始所有⻆⾊ 添加新的⻆⾊记录到⽤户⻆⾊表
         */
        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count,"用户角色分配失败！");
        }
        if(StringUtils.isNotBlank(roleIds)){
            List<UserRole> userRoleList = new ArrayList<>();
            String[] roleIdsArray = roleIds.split(",");
            for (String roleId : roleIdsArray) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoleList.add(userRole);
            }
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoleList) < userRoleList.size(),"用户角色分配失败！");
        }

    }

    /**
     * 参数校验
     * @param userName
     * @param email
     * @param phone
     */
    private void cheackParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空！");
        User temp = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(temp != null, "该用户已存在！");
        AssertUtil.isTrue(StringUtils.isBlank(email), "邮箱不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(phone), "手机号码不能为空！");
    }

    /**
     * 更新参数校验
     * @param userName
     * @param email
     * @param phone
     */
    private void cheackParams(String userName, String email, String phone,Integer userId) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空！");
        User temp = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(temp != null && !(temp.getId().equals(userId)), "该用户已存在！");
        AssertUtil.isTrue(StringUtils.isBlank(email), "邮箱不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(phone), "手机号码不能为空！");

    }

    /**
     * 登录参数校验
     *
     * @param userName
     * @param uPwd
     */
    private void checkLoginParams(String userName, String uPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(uPwd), "密码不能为空！");
    }

    /**
     * 登录密码校验
     *
     * @param uPwd
     * @param userPwd
     */
    private void checkLoginPwd(String uPwd, String userPwd) {
        uPwd = Md5Util.encode(uPwd);
        AssertUtil.isTrue(!uPwd.equals(userPwd), "用户密码不正确！");
    }


}
