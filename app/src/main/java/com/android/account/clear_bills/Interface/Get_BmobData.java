package com.android.account.clear_bills.Interface;

/**
 * Created by Administrator on 2017/11/19.
 */

public interface Get_BmobData<T> {
    /**
     * Bmob_Net.getBmob_net(this).searchorder(new Get_BmobData<List<Order>>() {
            @Override
            public void success(List<Order> data) {

            }

            @Override
            public void fail(String message) {

            }
            });
     *
     *
     * @param data   List<Order> 数据
     */
    void success(T data);
    void fail(String message);
}
