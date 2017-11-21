package com.android.account.clear_bills.Interface;

/**
 * Created by Administrator on 2017/11/19.
 */

public interface Bmob_Login_interface {
/**
     * @param code  0：表示失败，1：表示成功
     * @param message   返回信息
     */
    void success(int code, String message, String user);
}
