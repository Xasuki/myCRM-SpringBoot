package com.itx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itx.crm.base.BaseService;
import com.itx.crm.dao.DataDicMapper;
import com.itx.crm.query.DataDicQuery;
import com.itx.crm.vo.CustomerServe;
import com.itx.crm.vo.DataDic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/21 20:08
 */
@Service
public class DataDicService extends BaseService<DataDic,Integer> {
    @Resource
    private DataDicMapper dataDicMapper;

    public Map<String, Object> queryAllDataDic(DataDicQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<DataDic> pageInfo = new PageInfo<>(dataDicMapper.selectByParams(query));
        // 设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data", pageInfo.getList());
        return map;

    }
}
