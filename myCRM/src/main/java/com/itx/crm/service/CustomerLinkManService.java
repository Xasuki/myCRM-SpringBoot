package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.CustomerLinkManMapper;
import com.itx.crm.query.CustomerLinkManQuery;
import com.itx.crm.vo.CustomerContact;
import com.itx.crm.vo.CustomerLinkMan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 23:35
 */
@Service
public class CustomerLinkManService extends BaseService<CustomerLinkMan,Integer> {
    @Resource
    private CustomerLinkManMapper customerLinkManMapper;

    public Map<String, Object> queryCustomerLinkMan(CustomerLinkManQuery query) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<CustomerLinkMan> pageInfo = new PageInfo<>(customerLinkManMapper.selectByParams(query));
        // 设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());
        return map;

    }
}
