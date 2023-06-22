package com.itx.crm.query;

import com.itx.crm.base.BaseQuery;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 23:23
 */
public class CustomerContactQuery extends BaseQuery {
    private Integer cid;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
