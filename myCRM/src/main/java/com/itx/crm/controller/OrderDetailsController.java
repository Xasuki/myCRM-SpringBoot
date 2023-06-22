package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.query.OrderDetailsQuery;
import com.itx.crm.service.OrderDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 22:48
 */
@Controller
@RequestMapping("order_details")
public class OrderDetailsController extends BaseController {
    @Resource
    private OrderDetailsService orderDetailsService;

    /**
     * 订单详情页面
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(OrderDetailsQuery query){
        return orderDetailsService.queryOrderDetails(query);
    }
}
