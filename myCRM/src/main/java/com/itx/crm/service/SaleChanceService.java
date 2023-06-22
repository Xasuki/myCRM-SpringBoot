package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.SaleChanceMapper;
import com.itx.crm.enums.DevResult;
import com.itx.crm.enums.StateStatus;
import com.itx.crm.query.SaleChanceQuery;
import com.itx.crm.utils.AssertUtil;
import com.itx.crm.utils.PhoneUtil;
import com.itx.crm.vo.SaleChance;
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
 * @date 2023/6/11 11:43
 */
@Service
public class SaleChanceService extends BaseService<SaleChance, Integer> {
    @Resource
    private SaleChanceMapper saleChanceMapper;

    /**
     * 查询营销机会数据
     *
     * @param query
     * @return
     */
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery query) {
        Map<String, Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(query.getPage(), query.getLimit());
        //查询数据
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(query));
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 添加营销机会数据
     *
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance) {
        //1 参数校验
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        //2 设置参数默认值
        //分配状态
        saleChance.setIsValid(1);
        saleChance.setUpdateDate(new Date());
        saleChance.setCreateDate(new Date());
        //存在指派人  设置分配状态 分配时间 开发状态
        if (!StringUtils.isBlank(saleChance.getAssignMan())) {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        //不存在指派人 设置分配状态  开发状态
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getStatus());

        //执行添加操作
        AssertUtil.isTrue(insertSelective(saleChance) < 1, "营销机会数据添加失败！");
    }

    /**
     * 更新营销机会数据
     *
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance) {
        //1 通过id查询数据记录
        SaleChance temp = (SaleChance) saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");
        //2 参数校验
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        //3 设置相关参数
        saleChance.setUpdateDate(new Date());
        //修改前未分配 修改后已分配
        if (StringUtils.isBlank(temp.getAssignMan()) && StringUtils.isNotBlank(saleChance.getAssignMan())) {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        } else if (StringUtils.isNotBlank(temp.getAssignMan()) && StringUtils.isBlank(saleChance.getAssignMan())) {
            //修改前已分配 修改后未分配
            saleChance.setAssignMan("");
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
            saleChance.setAssignTime(null);
        }
        //执行更新，判断结果
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) < 1, "营销机会数据更新失败！");
    }

    /**
     * 删除营销机会数据
     *
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChance(Integer[] ids) {
        //判断id是否为空
        AssertUtil.isTrue(null == ids || ids.length < 1, "请选择需要删除的记录！");
        //删除记录
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids) < 0, "营销机会记录删除失败！");
    }

    /**
     * 更新营销机会的状态
     * 成功 = 2
     * 失败 = 3
     * @param id
     * @param devResult
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id, Integer devResult){
        AssertUtil.isTrue(null==id,"待更新记录不存在！");
        SaleChance temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(null==temp,"待更新记录不存在！");
        temp.setDevResult(devResult);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"营销机会记录更新失败！");
    }

    /**
     * 参数校验
     *
     * @param customerName
     * @param linkMan
     * @param linkPhone
     */
    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName), "请输入客户名称！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "请输入联系人！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone), "请输入手机号码！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone), "手机号码格式不正确！");
    }
}
