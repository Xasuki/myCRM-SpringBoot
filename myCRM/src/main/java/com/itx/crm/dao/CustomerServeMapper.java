package com.itx.crm.dao;

import com.itx.crm.base.BaseMapper;
import com.itx.crm.vo.CustomerServe;

import java.util.List;
import java.util.Map;

public interface CustomerServeMapper extends BaseMapper<CustomerServe,Integer> {

    List<Map<String, Object>> countCustomerService();
}