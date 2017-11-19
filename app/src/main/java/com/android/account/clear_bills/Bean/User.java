package com.android.account.clear_bills.Bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/11/19.
 */

public class User extends BmobUser {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
