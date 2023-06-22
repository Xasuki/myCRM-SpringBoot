package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/21 14:59
 */
@Controller
@RequestMapping("report")
public class CustomerReportController extends BaseController {
    @RequestMapping("{type}")
    public String index(@PathVariable Integer type){
        if(type == 0){
            return "report/customer_contri";
        }else if(type == 1){
            return "report/customer_make";
        }else if(type == 2){
            return "report/customer_service";
        }else if(type == 3){
            return "report/customer_loss";
        }
        return "";
    }
}
