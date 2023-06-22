package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.CustomerLossMapper;
import com.itx.crm.query.CustomerLossQuery;
import com.itx.crm.utils.AssertUtil;
import com.itx.crm.vo.CustomerLoss;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 9:37
 */
@Service
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {
    @Resource
    private CustomerLossMapper customerLossMapper;

    /**
     * 查询客户流失管理
     * @param query
     * @return
     */
    public Map<String, Object> queryCustomerLoss(CustomerLossQuery query) {
        Map<String,Object> result = new HashMap<String,Object>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<CustomerLoss> pageInfo =new PageInfo<>(customerLossMapper.selectByParams(query));
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code",0);
        result.put("msg","");
        return result;
    }

    public void updateCustomerLossStateById(Integer id, String lossReason) {
        //1 参数校验
        CustomerLoss customerLoss = customerLossMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id||customerLoss == null,"待确认流失用户不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(lossReason),"流失原因不能为空！");
        //2 设置默认值
        customerLoss.setState(1);
        customerLoss.setLossReason(lossReason);
        customerLoss.setConfirmLossTime(new Date());
        customerLoss.setUpdateDate(new Date());
        //3 执行操作
        AssertUtil.isTrue(customerLossMapper.updateByPrimaryKeySelective(customerLoss)<1,"确认流失失败！");
    }
}
