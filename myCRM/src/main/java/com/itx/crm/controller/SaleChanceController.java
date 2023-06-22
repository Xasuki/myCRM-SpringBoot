package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.enums.StateStatus;
import com.itx.crm.query.SaleChanceQuery;
import com.itx.crm.service.SaleChanceService;
import com.itx.crm.utils.CookieUtil;
import com.itx.crm.utils.LoginUserUtil;
import com.itx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.sql.Statement;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/11 11:45
 */
@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;

    /**
     * 多条件分页查询
     *
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery query, HttpServletRequest request, Integer flag) {
        if (  null != flag && flag == 1 ){
            query.setState(StateStatus.STATED.getType());
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            query.setAssignMan(userId);
        }
        return saleChanceService.querySaleChanceByParams(query);
    }

    /**
     * 打开营销机会管理视图
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "saleChance/sale_chance";
    }

    /**
     * 营销机会数据新增
     *
     * @param saleChance
     * @param request
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request) {
        //获取cookie中当前登录的用户名
        String userName = CookieUtil.getCookieValue(request, "userName");
        saleChance.setCreateMan(userName);
        saleChanceService.addSaleChance(saleChance);
        return success("营销机会数据添加成功！");
    }

    /**
     * 营销机会数据添加与更新⻚⾯视图转发
     *
     * @param saleChanceId
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer saleChanceId, Model model) {
        if (null != saleChanceId ) {
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(saleChanceId);
            model.addAttribute("saleChance", saleChance);
        }
        return "saleChance/add_update";
    }

    /**
     * 营销机会数据更新
     * @param saleChance
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance) {
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功！");
    }

    /**
     * 营销机会数据删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids) {
        saleChanceService.deleteSaleChance(ids);
        return success("营销机会数据删除成功！");
    }

    /**
     * 营销机会开发状态更新
     * @param id
     * @param devResult
     * @return
     */
    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id, Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success("开发状态更新成功！");
    }
}
