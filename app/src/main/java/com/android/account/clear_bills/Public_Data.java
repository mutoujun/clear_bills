package com.android.account.clear_bills;

import android.content.Context;

import com.android.account.clear_bills.Adapter.OrderAdapter;
import com.android.account.clear_bills.Interface.Callback;
import com.android.account.clear_bills.ViewModel.Bmob_Net;
import com.android.account.clear_bills.databinding.ActivityMainBinding;

/**
 * Created by Administrator on 2017/11/19.
 */

public class Public_Data {
    public static String user;
    public static Context context;
    public static int mYear,mMonth,mDay;
    public static String[] name={"陈煜","沙杰"};
    public static ActivityMainBinding activityMain;
    public static OrderAdapter orderAdapter;

    public static void sumMoney(Context context, final String[] name, final ActivityMainBinding activityMain){

        Bmob_Net.getBmob_net(context).getsummoney(name[0],new Callback<Float>() {
            @Override
            public void success(Float data) {
                activityMain.tvCyNum.setText(name[0].substring(0, 1) + ": ￥ " + data.toString());
            }
            @Override
            public void fail(String message) {

            }
        });

        Bmob_Net.getBmob_net(context).getsummoney(name[1],new Callback<Float>() {
            @Override
            public void success(Float data) {
                activityMain.tvSjNum.setText(name[1].substring(0,1)+": ￥ "+data.toString());
            }

            @Override
            public void fail(String message) {

            }
        });
    }
}
