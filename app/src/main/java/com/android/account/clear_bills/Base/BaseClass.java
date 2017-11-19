package com.android.account.clear_bills.Base;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/19.
 */

public class BaseClass<T> {
    private Context context;
    public BaseClass(Context context){
        this.context=context;
    }
    public void toast(T s){
        Toast.makeText(context, String.valueOf(s), Toast.LENGTH_SHORT).show();
    }
}
