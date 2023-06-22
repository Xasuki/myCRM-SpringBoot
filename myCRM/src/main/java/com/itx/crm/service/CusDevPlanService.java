package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.CusDevPlanMapper;
import com.itx.crm.dao.SaleChanceMapper;
import com.itx.crm.query.CusDevPlanQuery;
import com.itx.crm.utils.AssertUtil;
import com.itx.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/13 14:48
 */
@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {
    @Resource
    private CusDevPlanMapper cusDevPlanMapper;
    @Resource
    private SaleChanceMapper saleChanceMapper;

    /**
     * 查询开发计划数据
     * @param query
     * @return
     */
    public Map<String, Object> queryCusDevPlanByParams(CusDevPlanQuery query) {
        Map<String ,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<CusDevPlan> pageInfo = new PageInfo<>(cusDevPlanMapper.selectByParams(query));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 添加开发计划
     * @param cusDevPlan
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCusDevPlan(CusDevPlan cusDevPlan){
        // 1 参数检验
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        // 2 设置参数默认值
        cusDevPlan.setIsValid(1);
        cusDevPlan.setUpdateDate(new Date());
        cusDevPlan.setCreateDate(new Date());
        // 3 执行添加，判断结果
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan)<1,"添加开发计划失败！");
    }

    /**
     * 更新开发计划
     * @param cusDevPlan
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        //1 参数校验
        AssertUtil.isTrue(null == cusDevPlan.getId() || null == saleChanceMapper.selectByPrimaryKey(cusDevPlan.getId()),"待更新记录不存在！");
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        //2 设置默认参数
        cusDevPlan.setUpdateDate(new Date());
        //3 执行更新操作
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划记录更新失败！");
    }

    /**
     * 删除开发计划
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCusDevPlan(Integer id) {
        CusDevPlan cusDevPlan = selectByPrimaryKey(id);
        //1 参数检验
        AssertUtil.isTrue(null == id || cusDevPlan==null,"待删除记录不存在！");
        //2 设置参数
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划记录删除失败！");
    }

    /**
     * 参数校验
     * @param saleChanceId
     * @param planDate
     * @param planItem
     */
    private void checkParams(Integer saleChanceId, String planItem, Date  planDate) {
        AssertUtil.isTrue(null == saleChanceId || saleChanceMapper.selectByPrimaryKey(saleChanceId) ==null,"请设置营销机会ID");
        AssertUtil.isTrue(null == planDate,"请指定计划时间！");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请输入计划内容！");
    }


}
