package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.query.CustomerOrderQuery;
import com.itx.crm.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 21:48
 */
@Controller
@RequestMapping("order")
public class CustomerOrderController extends BaseController {
    @Resource
    private CustomerOrderService customerOrderService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String ,Object> list(CustomerOrderQuery query){
        return  customerOrderService.queryCustomerOrderById(query);
    }

    //前后端数据交互的路径
    @RequestMapping("toOrderDetailPage")
    public String  toOrderDetailPage(Integer cid, Model model){
        model.addAttribute("order", customerOrderService.selectByPrimaryKey(cid));
        //本地资源路径
        return "customer/customer_order_detail";
    }


}
