package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.query.CustomerContactQuery;
import com.itx.crm.service.CustomerContactService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 23:18
 */
@Controller
@RequestMapping("customer_contact")
public class CustomerContactController extends BaseController {
    @Resource
    private CustomerContactService customerContactService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CustomerContactQuery query){
     return customerContactService.queryCustomerContact(query);
    }
}
