package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.CustomerMapper;
import com.itx.crm.dao.CustomerServeMapper;
import com.itx.crm.dao.UserMapper;
import com.itx.crm.enums.CustomerServeStatus;
import com.itx.crm.query.CustomerServeQuery;
import com.itx.crm.utils.AssertUtil;
import com.itx.crm.vo.CustomerServe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/20 20:48
 */
@Service
public class CustomerServeService extends BaseService<CustomerServe, Integer> {
    @Resource
    private CustomerServeMapper customerServeMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 查询客户服务
     *
     * @param query
     * @return
     */
    public Map<String, Object> queryCustomerServe(CustomerServeQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<CustomerServe> pageInfo = new PageInfo<>(customerServeMapper.selectByParams(query));
        // 设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 服务添加/更新操作
     *
     * @param customerServe
     */
    public void addOrUpdateCustomerServe(CustomerServe customerServe) {
        if (null == customerServe.getId()) {
            //为空 服务记录添加 校验
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()), "请指定客户!");
            AssertUtil.isTrue(null == customerMapper.queryCustomerByName(customerServe.getCustomer()), "当前客户暂不存在!");
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()), "请指定服务类型!");
            //设置默认值
            customerServe.setIsValid(1);
            customerServe.setCreateDate(new Date());
            customerServe.setUpdateDate(new Date());
            customerServe.setState(CustomerServeStatus.CREATED.getState());
            //执行操作
            AssertUtil.isTrue(insertSelective(customerServe) < 1, "服务记录添加失败!");
        } else {
            //分配 处理 反馈
            CustomerServe temp = customerServeMapper.selectByPrimaryKey(customerServe.getId());
            AssertUtil.isTrue(null == temp, "待处理的记录不存在！");
            if (customerServe.getState().equals(CustomerServeStatus.ASSIGNED.getState())) {
                // 服务分配
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getAssigner()) || (null == userMapper.selectByPrimaryKey(Integer.parseInt(customerServe.getAssigner()))), "待分配⽤户不存在");
                customerServe.setAssignTime(new Date());
                customerServe.setUpdateDate(new Date());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe) < 1, "服务分配失败!");
            }
            if (customerServe.getState().equals(CustomerServeStatus.PROCED.getState())) {
                // 服务处理
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()), "请指定处理内容!");
                customerServe.setServiceProceTime(new Date());
                customerServe.setUpdateDate(new Date());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe) < 1, "服务处理失败!");
            }
            if (customerServe.getState().equals(CustomerServeStatus.FEED_BACK.getState())) {
                // 服务处理
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()), "请指定反馈内容!");
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()), "请指定反馈满意度!");
                customerServe.setUpdateDate(new Date());
                customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe) < 1, "服务反馈失败!");
            }
        }
    }

    /**
     * 添加服务
     *
     * @param customerServe
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCustomerServe(CustomerServe customerServe) {
        /* 1. 参数校验 */
        // 客户名 customer     非空
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()), "客户名不能为空！");
        // 客户名 customer     客户表中存在客户记录
        AssertUtil.isTrue(customerMapper.queryCustomerByName(customerServe.getCustomer()) == null, "客户不存在！");
        // 服务类型 serveType  非空
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()), "请选择服务类型！");
        //  服务请求内容  serviceRequest  非空
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceRequest()), "服务请求内容不能为空！");
        /* 2. 设置参数的默认值 */
        //  服务状态    服务创建状态  fw_001
        customerServe.setState(CustomerServeStatus.CREATED.getState());
        customerServe.setIsValid(1);
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        /* 2. 执行添加操作，判断受影响的行数 */
        AssertUtil.isTrue(customerServeMapper.insertSelective(customerServe) < 1, "添加服务失败！");
    }

    /**
     * 更新服务
     *
     * @param customerServe
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerServe(CustomerServe customerServe) {
        // 客户服务ID  非空且记录存在
        AssertUtil.isTrue(customerServe.getId() == null
                || customerServeMapper.selectByPrimaryKey(customerServe.getId()) == null, "待更新的服务记录不存在！");

        // 判断客户服务的服务状态
        if (CustomerServeStatus.ASSIGNED.getState().equals(customerServe.getState())) {
            // 服务分配操作
            // 分配人       非空，分配用户记录存在
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getAssigner()), "待分配用户不能为空！");
            AssertUtil.isTrue(userMapper.selectByPrimaryKey(Integer.parseInt(customerServe.getAssigner())) == null, "待分配用户不存在！");
            // 分配时间     系统当前时间
            customerServe.setAssignTime(new Date());

        } else if (CustomerServeStatus.PROCED.getState().equals(customerServe.getState())) {
            // 服务处理操作
            // 服务处理内容   非空
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()), "服务处理内容不能为空！");
            // 服务处理时间   系统当前时间
            customerServe.setServiceProceTime(new Date());

        } else if (CustomerServeStatus.FEED_BACK.getState().equals(customerServe.getState())) {
            // 服务反馈操作
            // 服务反馈内容   非空
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()), "服务反馈内容不能为空！");
            // 服务满意度     非空
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()), "请选择服务反馈满意度！");
            // 服务状态      设置为 服务归档状态 fw_005
            customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
        }
        // 更新时间     系统当前时间
        customerServe.setUpdateDate(new Date());
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(customerServeMapper.updateByPrimaryKeySelective(customerServe) < 1, "服务更新失败！");
    }

    /**
     * 客户服务 折线图
     * @return
     */
    public Map<String, Object> countCustomerService() {
        Map<String ,Object> map = new HashMap<>();
        List<Map<String ,Object>> list = customerServeMapper.countCustomerService();
        List<String> dataX = new ArrayList<>();
        List<Integer> dataY = new ArrayList<>();

        if(list!=null && list.size()>0){
            list.forEach( m ->{
                dataX.add(m.get("serviceValue").toString());
                dataY.add(Integer.valueOf(m.get("total").toString()));
            });
        }
        map.put("dataX",dataX);
        map.put("dataY",dataY);
        return map;
    }

    /**
     * 客户构成 饼状图
     * @return
     */
    public Map<String, Object> countCustomerService02() {
        Map<String ,Object> map = new HashMap<>();
        List<Map<String ,Object>> list = customerServeMapper.countCustomerService();
        List<String> dataX = new ArrayList<>();
        List<Map<String,Object>> dataY = new ArrayList<>();

        if(list!=null && list.size()>0){
            list.forEach( m ->{
                dataX.add(m.get("serviceValue").toString());
                Map<String,Object> dataMap = new HashMap<>();
                dataMap.put("name",m.get("serviceValue").toString());
                dataMap.put("value",m.get("total").toString());
                dataY.add(dataMap);
            });
        }
        map.put("dataX",dataX);
        map.put("dataY",dataY);
        return map;
    }
}