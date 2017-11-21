package com.android.account.clear_bills.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/19.
 */

public class Order extends BmobObject {
    private String name;
    private Float money;
    private String remark;
    private Boolean enter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getEnter() {
        return enter;
    }

    public void setEnter(Boolean enter) {
        this.enter = enter;
    }
}
