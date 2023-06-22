package com.itx.crm.query;

import com.itx.crm.base.BaseQuery;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/21 20:05
 */
public class DataDicQuery extends BaseQuery {
    private String dataDicName;
    private String dataDicValue;

    public String getDataDicName() {
        return dataDicName;
    }

    public void setDataDicName(String dataDicName) {
        this.dataDicName = dataDicName;
    }

    public String getDataDicValue() {
        return dataDicValue;
    }

    public void setDataDicValue(String dataDicValue) {
        this.dataDicValue = dataDicValue;
    }
}
