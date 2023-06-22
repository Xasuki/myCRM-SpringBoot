package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.query.CustomerReprieveQuery;
import com.itx.crm.service.CustomerReprieveService;
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
 * @date 2023/6/20 8:35
 */
@Controller
@RequestMapping("customer_rep")
public class CustomerReprieveController extends BaseController {
    @Resource
    private CustomerReprieveService customerReprieveService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String ,Object> list(CustomerReprieveQuery query){
        return customerReprieveService.queryCustomerReprieve(query);
    }

    /**
     * 添加暂缓
     * @param customerReprieve
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addCustomerReprieve(CustomerReprieve customerReprieve){
        customerReprieveService.addCustomerReprieve(customerReprieve);
        return success("添加暂缓成功！");
    }

    /**
     * 更新暂缓
     * @param customerReprieve
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerReprieve(CustomerReprieve customerReprieve){
        customerReprieveService.updateCustomerReprieve(customerReprieve);
        return success("更新暂缓成功！");
    }

    /**
     * 删除暂缓
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomerReprieve(Integer id){
        customerReprieveService.deleteCustomerReprieve(id);
        return success("删除暂缓成功！");
    }

    /**
     * 暂缓管理视图
     * @param lossId
     * @param model
     * @return
     */
    @RequestMapping("toAddOrUpdateCustomerReprieve")
    public String toAddOrUpdateCustomerReprieve(Integer lossId,Integer id, Model model){
        model.addAttribute("lossId",lossId);
        if(null != id) {
            model.addAttribute("customerRep", customerReprieveService.selectByPrimaryKey(id));
        }
        return "customerLoss/customer_rep_add_update";
    }
}
