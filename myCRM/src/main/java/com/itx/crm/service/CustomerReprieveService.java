package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.CustomerLossMapper;
import com.itx.crm.dao.CustomerReprieveMapper;
import com.itx.crm.query.CustomerReprieveQuery;
import com.itx.crm.utils.AssertUtil;
import com.itx.crm.vo.CustomerReprieve;
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
 * @date 2023/6/20 8:38
 */
@Service
public class CustomerReprieveService extends BaseService<CustomerReprieve, Integer> {
    @Resource
    private CustomerReprieveMapper customerReprieveMapper;
    @Resource
    private CustomerLossMapper customerLossMapper;

    /**
     * 查询客户流失
     *
     * @param query
     * @return
     */
    public Map<String, Object> queryCustomerReprieve(CustomerReprieveQuery query) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<CustomerReprieve> pageInfo = new PageInfo<>(customerReprieveMapper.selectByParams(query));
        result.put("count", pageInfo.getTotal());
        result.put("data", pageInfo.getList());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    /**
     * 添加暂缓
     *
     * @param customerReprieve
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCustomerReprieve(CustomerReprieve customerReprieve) {
        //1 参数校验
        checkParams(customerReprieve.getLossId(), customerReprieve.getMeasure());
        //2 设置默认值
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        //3 执行操作
        AssertUtil.isTrue(customerReprieveMapper.insertSelective(customerReprieve) < 1, "添加暂缓失败！");
    }

    /**
     * 更新暂缓
     *
     * @param customerReprieve
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerReprieve(CustomerReprieve customerReprieve) {
        AssertUtil.isTrue(null == customerReprieve.getId() || null == customerReprieveMapper.selectByPrimaryKey(customerReprieve.getId()), "待更新记录不存在！");
        //1 参数校验
        checkParams(customerReprieve.getLossId(), customerReprieve.getMeasure());
        //2 设置默认值
        customerReprieve.setUpdateDate(new Date());
        //3 执行操作
        AssertUtil.isTrue(customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve) < 1, "更新暂缓失败！");
    }

    /**
     * 删除暂缓
     *
     * @param
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomerReprieve(Integer id) {
        CustomerReprieve customerReprieve = customerReprieveMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == id || customerReprieve == null, "待删除记录不存在！");
        customerReprieve.setIsValid(0);
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve) < 1, "删除记录失败！");
    }


    /**
     * 参数校验
     *
     * @param lossId
     * @param measure
     */
    private void checkParams(Integer lossId, String measure) {
        AssertUtil.isTrue(null == lossId || null == customerLossMapper.selectByPrimaryKey(lossId), "流失客户记录不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(measure), "暂缓措施内容不能为空！");
    }
}
