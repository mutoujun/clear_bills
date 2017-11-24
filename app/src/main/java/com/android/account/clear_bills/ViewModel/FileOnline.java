package com.android.account.clear_bills.ViewModel;

import android.content.SharedPreferences;

import com.android.account.clear_bills.Interface.Callback;
import com.android.account.clear_bills.Interface.Filesave;
import com.android.account.clear_bills.Public_Data;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/11/21.
 */

public class FileOnline implements Filesave{
    private static  volatile FileOnline online=null;
    private FileOnline(){}
    public static FileOnline getOnline(){
        if (online==null){
            synchronized (FileOnline.class){
                if (online==null){
                    online=new FileOnline();
                }
            }
        }
        return online;
    }
    public void savepassword(boolean b,String s){
        SharedPreferences.Editor editor = Public_Data.context.getSharedPreferences("clear_bills",
                MODE_PRIVATE).edit();
        editor.putBoolean("forget",b);
        editor.putString("password", s);
        editor.commit();
    }

    @Override
    public void getpassword(Callback<String> callback) {
        SharedPreferences pref =  Public_Data.context.getSharedPreferences("clear_bills",
                MODE_PRIVATE);
        boolean forget = pref.getBoolean("forget", false);
        String password="0";
        if (forget){
            password = pref.getString("password", "");
        }
        callback.success(password);
    }
}
