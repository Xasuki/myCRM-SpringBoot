package com.itx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DataDic {
    private Integer id;

    private String dataDicName;

    private String dataDicValue;

    private Byte isValid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//如果传递的是Date类型。要求传递的时间字符串的格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//如果传递的是Date类型。要求传递的时间字符串的格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataDicName() {
        return dataDicName;
    }

    public void setDataDicName(String dataDicName) {
        this.dataDicName = dataDicName == null ? null : dataDicName.trim();
    }

    public String getDataDicValue() {
        return dataDicValue;
    }

    public void setDataDicValue(String dataDicValue) {
        this.dataDicValue = dataDicValue == null ? null : dataDicValue.trim();
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}