package com.itx.crm.dao;

import com.itx.crm.base.BaseMapper;
import com.itx.crm.query.CustomerQuery;
import com.itx.crm.vo.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    Customer queryCustomerByName(String name);

    List<Map<String ,Object>> queryCustomerContribution(CustomerQuery query);

    List<Map<String, Object>> countCustomerMark();

}