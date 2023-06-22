package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.query.CustomerQuery;
import com.itx.crm.service.CustomerService;
import com.itx.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/17 18:18
 */
@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;

    /**
     * 客户信息页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "customer/customer";
    }

    /**
     * 显示所有客户信息页面
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerByParams(CustomerQuery query){
        return customerService.queryCustomerByParams(query);
    }

    /**
     * 客户添加
     * @param customer
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addCustomer(Customer customer){
        customerService.addCustomer(customer);
        return success("客户添加成功！");
    }

    /**
     * 客户更新
     * @param customer
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer){
        customerService.updateCustomer(customer);
        return success("客户更新成功！");
    }

    /**
     * 跳转到添加/更新页面视图
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateCustomerPage")
    public String toAddOrUpdateCustomerPage(Integer id, Model model){
        model.addAttribute("customer",customerService.selectByPrimaryKey(id));
        return "customer/add_update";
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deletCustomerById(Integer id){
        customerService.deletCustomerById(id);
        return success("客户删除成功！");
    }

    /**
     * 客户订单查看
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toCustomerOrderPage")
    public String toCustomerOrderPage(Integer id,Model model){
        model.addAttribute("customer",customerService.selectByPrimaryKey(id));
        return "customer/customer_order";
    }

    /**
     * 客户贡献
     * @param query
     * @return
     */
    @RequestMapping("queryCustomerContribution")
    @ResponseBody
    public Map<String ,Object> queryCustomerContribution(CustomerQuery query){
        return customerService.queryCustomerContribution(query);
    }

    /**
     * 客户构成 折线图
     */
    @RequestMapping("countCustomerMark")
    @ResponseBody
    public Map<String,Object> countCustomerMark(){
        return customerService.countCustomerMark();
    }

    /**
     * 客户构成 饼状图
     */
    @RequestMapping("countCustomerMark02")
    @ResponseBody
    public Map<String,Object> countCustomerMark02(){
        return customerService.countCustomerMark02();
    }
}

