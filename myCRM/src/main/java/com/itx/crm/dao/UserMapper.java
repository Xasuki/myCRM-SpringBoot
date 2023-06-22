package com.itx.crm.dao;
import com.itx.crm.base.BaseMapper;
import com.itx.crm.vo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {
    //通过用户名，查询用户
    User queryUserByUserName(String userName);
    //查询所有销售
    List<Map<String,Object>> queryAllSales();


    List<Map<String, Object>> queryAllCustomerManagers();
}