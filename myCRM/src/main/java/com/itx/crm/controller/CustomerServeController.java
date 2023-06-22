package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.query.CustomerServeQuery;
import com.itx.crm.service.CustomerServeService;
import com.itx.crm.utils.LoginUserUtil;
import com.itx.crm.vo.CustomerServe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @author suki
 * @version 1.0
 * @date 2023/6/20 20:41
 */
@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {
    @Resource
    private CustomerServeService customerServeService;

    /**
     * 服务管理⻚⾯转发⽅法
     * @param type
     * @return
     */
    @RequestMapping("index/{type}")
    public String index(@PathVariable Integer type) {
        // 判断类型是否为空
        if (type != null) {
            if (type == 1) {
                // 服务创建
                return "customerServe/customer_serve";
            } else if (type == 2) {
                // 服务分配
                return "customerServe/customer_serve_assign";
            } else if (type == 3) {
                // 服务处理
                return "customerServe/customer_serve_proce";
            } else if (type == 4) {
                // 服务反馈
                return "customerServe/customer_serve_feed_back";
            } else if (type == 5) {
                // 服务归档
                return "customerServe/customer_serve_archive";
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 服务信息列表展
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CustomerServeQuery query, Integer flag, HttpServletRequest request){
        if(null != flag && flag == 1){
            query.setAssigner(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return customerServeService.queryCustomerServe(query);
    }

    /**
     * 服务添加⻚⾯转发
     * @return
     */
    @RequestMapping("toAddCustomerServePage")
    public String toAddCustomerServePage(){
        return "customerServe/customer_serve_add";
    }

    /**
     * 服务分配⻚⾯转发
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toCustomerServeAssignPage")
    public String toCustomerServeAssignPage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_assign_add";
    }

    /**
     * 服务反馈⻚⾯转发
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toCustomerServeBackPage")
    public String toCustomerServeBackPage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_feed_back_add";
    }

    /**
     * 服务处理⻚⾯转发
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toCustomerServeProcePage")
    public String toCustomerServeProcePage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_proce_add";
    }

    /**
     * 服务添加
     * @param customerServe
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addCustomerServe(CustomerServe customerServe){
        customerServeService.addCustomerServe(customerServe);
        return success("添加服务成功！");
    }

    /**
     * 服务更新 （分配 处理 反馈）
     * @param customerServe
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerServe(CustomerServe customerServe){
        customerServeService.updateCustomerServe(customerServe);
        return success("更新服务成功！");
    }
    /**
     * 客户服务 折线图
     */
    @RequestMapping("countCustomerService")
    @ResponseBody
    public Map<String,Object> countCustomerService(){
        return customerServeService.countCustomerService();
    }

    /**
     * 客户服务 饼状图
     */
    @RequestMapping("countCustomerService02")
    @ResponseBody
    public Map<String,Object> countCustomerService02(){
        return customerServeService.countCustomerService02();
    }

}
