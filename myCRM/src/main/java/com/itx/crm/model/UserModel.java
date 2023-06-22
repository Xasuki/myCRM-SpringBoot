package com.itx.crm.model;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/9 10:51
 */
public class UserModel {
    private String userId;
    private String userName;
    private String trueName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
