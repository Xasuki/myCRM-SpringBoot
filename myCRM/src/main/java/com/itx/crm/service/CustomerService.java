package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.CustomerMapper;
import com.itx.crm.query.CustomerQuery;
import com.itx.crm.utils.AssertUtil;
import com.itx.crm.utils.PhoneUtil;
import com.itx.crm.vo.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 10:45
 */
@Service
public class CustomerService extends BaseService<Customer, Integer> {
    @Resource
    private CustomerMapper customerMapper;

    /**
     * 分页查询
     * @param query
     * @return
     */
    public Map<String, Object> queryCustomerByParams(CustomerQuery query) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<Customer> pageInfo = new PageInfo<>(customerMapper.selectByParams(query));
        // 设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 客户添加
     * @param customer
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCustomer(Customer customer) {
        //1 参数校验
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        AssertUtil.isTrue(null !=customerMapper.queryCustomerByName(customer.getName()),"该客户已存在！");
        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        customer.setState(0);
        String khno = "KH"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        customer.setKhno(khno);
        AssertUtil.isTrue(insertSelective(customer)<1,"客户添加失败！");
    }


    /**
     * 客户更新
     * @param customer
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer) {
        AssertUtil.isTrue(null==customer.getId() || null == selectByPrimaryKey(customer.getId()),"待更新记录不存在！");
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        Customer temp = customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null!=temp && !(temp.getId().equals(customer.getId())),"该客户已存在！");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户更新失败！");
    }



    /**
     * 客户删除
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deletCustomerById(Integer id) {
        Customer customer = selectByPrimaryKey(id);
        AssertUtil.isTrue(null == id || customer == null,"待删除记录不存在！");
        customer.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户删除失败！");
    }


    /**
     * 参数校验
     * @param name
     * @param phone
     * @param fr
     */
    private void checkParams(String name, String phone, String fr) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"客户名称不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号码格式不正确！");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"请指定公司法人！");
    }

    public Map<String, Object> queryCustomerContribution(CustomerQuery query) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo< Map<String,Object>> pageInfo = new PageInfo<>(customerMapper.queryCustomerContribution(query));
        // 设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 客户构成 折线图
     * @return
     */
    public Map<String, Object> countCustomerMark() {
        Map<String ,Object> map = new HashMap<>();
        List<Map<String ,Object>> list = customerMapper.countCustomerMark();
        List<String> dataX = new ArrayList<>();
        List<Integer> dataY = new ArrayList<>();

        if(list!=null && list.size()>0){
            list.forEach( m ->{
                dataX.add(m.get("level").toString());
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
    public Map<String, Object> countCustomerMark02() {
        Map<String ,Object> map = new HashMap<>();
        List<Map<String ,Object>> list = customerMapper.countCustomerMark();
        List<String> dataX = new ArrayList<>();
        List<Map<String,Object>> dataY = new ArrayList<>();

        if(list!=null && list.size()>0){
            list.forEach( m ->{
                dataX.add(m.get("level").toString());
               Map<String,Object> dataMap = new HashMap<>();
               dataMap.put("name",m.get("level").toString());
               dataMap.put("value",m.get("total").toString());
               dataY.add(dataMap);
            });
        }
        map.put("dataX",dataX);
        map.put("dataY",dataY);
        return map;
    }
}
