package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.query.DataDicQuery;
import com.itx.crm.service.DataDicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/21 19:47
 */
@Controller
@RequestMapping("data_dic")
public class DataDicController extends BaseController {
    @Resource
    private DataDicService dataDicService;

    @RequestMapping("index")
    public String index(){
        return "dataDic/data_dic";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryAllDataDic(DataDicQuery query){
        return dataDicService.queryAllDataDic(query);
    }
}
