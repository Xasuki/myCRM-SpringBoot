package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.CustomerContactMapper;
import com.itx.crm.query.CustomerContactQuery;
import com.itx.crm.vo.Customer;
import com.itx.crm.vo.CustomerContact;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 23:26
 */
@Service
public class CustomerContactService extends BaseService<CustomerContact,Integer> {
    @Resource
    private CustomerContactMapper customerContactMapper;

    public Map<String, Object> queryCustomerContact(CustomerContactQuery query) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<CustomerContact> pageInfo = new PageInfo<>(customerContactMapper.selectByParams(query));
        // 设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());
        return map;
    }
}
