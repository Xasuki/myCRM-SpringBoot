package com.itx.crm.query;

import com.itx.crm.base.BaseQuery;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 23:44
 */
public class CustomerLossQuery extends BaseQuery {
    private String cusNo;
    private String cusName;
    private Integer state;

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
