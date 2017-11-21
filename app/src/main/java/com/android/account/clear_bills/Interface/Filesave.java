package com.android.account.clear_bills.Interface;

import com.android.account.clear_bills.ViewModel.FileOnline;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface Filesave {
   // FileOnline fileonline=new FileOnline();
    //Filesave filesave=(Filesave)fileonline;
    /**记住密码
     * 登录成功调用
     * @param b 填入是否记住密码
     */
    void savepassword(boolean b);

    /**获取密码
     * 打开软件调用，接口回调密码在success中
     * @param callback
     */
    void getpassword(Callback<String> callback);
}
