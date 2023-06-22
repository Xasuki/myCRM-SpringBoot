package com.itx.crm.query;

import com.itx.crm.base.BaseQuery;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/19 22:46
 */
public class OrderDetailsQuery extends BaseQuery {
    private Integer oid;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }
}
