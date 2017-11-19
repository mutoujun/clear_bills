package com.android.account.clear_bills.ViewModel;

import android.content.Context;

import com.android.account.clear_bills.Base.BaseClass;
import com.android.account.clear_bills.Bean.User;
import com.android.account.clear_bills.Interface.Bmob_Login_interface;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/11/19.
 */

public class Bmob_Net extends BaseClass{
    private static volatile Bmob_Net bmob_net=null;
    private Context context;
    private Bmob_Net(Context context){
        super(context);
        this.context=context;
    }
    public static Bmob_Net getBmob_net(Context context){
        if (bmob_net == null) {
            synchronized (Bmob_Net.class){
                if (bmob_net==null){
                    bmob_net=new Bmob_Net(context);
                }
            }
        }
        return bmob_net;
    }
    public void register(String name, String username, String password,final Bmob_Login_interface bmob_login_interface){
        User bu = new User();
        bu.setName(name);
        bu.setUsername(username);
        bu.setPassword(password);
//注意：不能用save方法进行注册
        bu.signUp(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
                if(e==null){
                    bmob_login_interface.success(1,"注册成功");
                }else{
                    bmob_login_interface.success(0,e.getMessage());
                }
            }
        });
    }
    public void login(String username,String password,final Bmob_Login_interface bmob_login_interface){
        User bu2 = new User();
        bu2.setUsername(username);
        bu2.setPassword(password);
        bu2.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if(e==null){
                    bmob_login_interface.success(1,"登录成功");
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                }else{
                    bmob_login_interface.success(0,e.getMessage());
                }
            }
        });
    }
}
