package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.OrderDetailsMapper;
import com.itx.crm.query.OrderDetailsQuery;
import com.itx.crm.vo.OrderDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 22:51
 */
@Service
public class OrderDetailsService extends BaseService<OrderDetails,Integer> {
    @Resource
    private OrderDetailsMapper orderDetailsMapper;

    /**
     * 打开订单详情页面
     * @param query
     * @return
     */
    public Map<String, Object> queryOrderDetails(OrderDetailsQuery query) {
        Map<String,Object> result = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<OrderDetails> pageInfo =new PageInfo(orderDetailsMapper.selectByParams(query));
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code",0);
        result.put("msg","");
        return result;

    }
}
