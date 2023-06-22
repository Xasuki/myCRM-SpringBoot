package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.query.CusDevPlanQuery;
import com.itx.crm.service.CusDevPlanService;
import com.itx.crm.service.SaleChanceService;
import com.itx.crm.vo.CusDevPlan;
import com.itx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import java.util.Map;


/**
 * @author suki
 * @version 1.0
 * @date 2023/6/13 11:11
 */
@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;
    @Resource
    private CusDevPlanService cusDevPlanService;
    /**
     * 客户开发主页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "cusDevPlan/cus_dev_plan";
    }

    /**
     * 进⼊开发计划项数据⻚面
     * @param model
     * @param sid
     * @return
     */
    @RequestMapping("toCusDevPlanDataPage")
    public String toCusDevPlanDataPage(Model model ,Integer sid){
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(sid);
        model.addAttribute("saleChance",saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }

    /**
     * 查询营销机会的计划项数据列表
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCusDevPlanByParams (CusDevPlanQuery query) {
        return cusDevPlanService.queryByParamsForTable(query);
    }

    /**
     * 添加开发计划
     * @param cusDevPlan
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return success("计划添加成功！");
    }

    /**
     * 更新开发计划
     * @param cusDevPlan
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划更新成功！");
    }

    /**
     * 打开新增或者修改开发计划的视图
     * @param sid
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateCusDevPlanPage")
    public String addOrUpdateCusDevPlanPage(Integer sid , Integer id, Model model){
        model.addAttribute("cusDevPlan",cusDevPlanService.selectByPrimaryKey(id));
        model.addAttribute("sid",sid);
        return "cusDevPlan/add_update";
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer id){
        cusDevPlanService.deleteCusDevPlan(id);
        return success("删除开发计划成功！");
    }



}
