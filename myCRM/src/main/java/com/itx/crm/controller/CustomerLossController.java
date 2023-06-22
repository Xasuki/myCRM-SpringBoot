package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.query.CustomerLossQuery;
import com.itx.crm.service.CustomerLossService;
import com.itx.crm.vo.CustomerReprieve;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 9:36
 */
@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {
    @Resource
    private CustomerLossService customerLossService;

    /**
     * 客户流失管理页面
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "customerLoss/customer_loss";
    }

    /**
     * 客户流失列表
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> list(CustomerLossQuery query) {
        return customerLossService.queryCustomerLoss(query);
    }

    /**
     * 跳转客户流失管理详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toCustomerReprievePage")
    public String toCustomerReprievePage(Integer id, Model model) {
        model.addAttribute("customerLoss", customerLossService.selectByPrimaryKey(id));
        return "customerLoss/customer_rep";
    }

    /**
     * 更新客户流失状态
     * @param id
     * @param lossReason
     * @return
     */
    @RequestMapping("updateCustomerLossStateById")
    @ResponseBody
    public ResultInfo updateCustomerLossStateById(Integer id,String lossReason){
        customerLossService.updateCustomerLossStateById(id,lossReason);
        return success("确认流失成功！");
    }
}
