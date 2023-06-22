package com.itx.crm.model;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/15 9:47
 */
public class TreeModule {
    private Integer id;
    private Integer pId;
    private String name;
    //设置下拉框默认选项
    private Boolean checked = false;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
