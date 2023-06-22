package com.itx.crm.query;

import com.itx.crm.base.BaseQuery;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/12 21:14
 */
public class SaleChanceQuery extends BaseQuery {
    private String customerName; // 客户名称
    private String createMan; // 创建⼈
    private Integer state; // 分配状态
    private Integer devResult; // 开发状态
    private Integer assignMan;// 分配⼈

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDevResult() {
        return devResult;
    }

    public void setDevResult(Integer devResult) {
        this.devResult = devResult;
    }

    public Integer getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(Integer assignMan) {
        this.assignMan = assignMan;
    }
}
