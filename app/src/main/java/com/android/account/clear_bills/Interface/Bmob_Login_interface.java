package com.android.account.clear_bills.Interface;

import com.android.account.clear_bills.ViewModel.Bmob_Net;

/**
 * Created by Administrator on 2017/11/19.
 */

public interface Bmob_Login_interface {
    //登录和注册同一个接口
    /**
     * 调用例子
     *  Bmob_Net.getBmob_net(this).register("name", "username", "password", new Bmob_Login_interface() {
            @Override
            public void success(int code, String message) {

            }
            });
     *
     * @param code  0：表示失败，1：表示成功
     * @param message   返回信息
     */
    void success(int code,String message);
}
