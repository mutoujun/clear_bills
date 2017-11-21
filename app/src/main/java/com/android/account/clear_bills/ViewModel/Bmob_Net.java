package com.android.account.clear_bills.ViewModel;

import android.content.Context;
import android.util.Log;

import com.android.account.clear_bills.Base.BaseClass;
import com.android.account.clear_bills.Bean.Order;
import com.android.account.clear_bills.Bean.User;
import com.android.account.clear_bills.Interface.Bmob_Login_interface;
import com.android.account.clear_bills.Interface.Get_BmobData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
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
                    bmob_login_interface.success(1,"注册成功",null);
                }else{
                    bmob_login_interface.success(0,e.getMessage(),null);
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
                    bmob_login_interface.success(1,"登录成功",bmobUser.getName());
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                }else{
                    bmob_login_interface.success(0,e.getMessage(),null);
                }
            }
        });
    }
    public void addorder(float money,String remark,final Bmob_Login_interface bmob_login_interface){
        User user=User.getCurrentUser(User.class);
        Order order=new Order();
//注意：不能调用gameScore.setObjectId("")方法
        order.setName(user.getName());
        order.setMoney(Float.valueOf(money));
        order.setRemark(remark);
        order.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    bmob_login_interface.success(1,"创建数据成功：",null);
                    toast("创建数据成功：" + objectId);
                }else{
                    bmob_login_interface.success(0,"失败："+e.getMessage()+","+e.getErrorCode(),null);
                }
            }
        });
    }
    public void searchorder(final Get_BmobData<List<Order>> get_bmobData){
        BmobQuery<Order> query = new BmobQuery<Order>();
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("enter",false);
        query.order("-updatedAt");
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if(e==null){
                    get_bmobData.success(object);
                   // toast("查询成功：共"+object.size()+"条数据。");

                }else{
                    get_bmobData.fail("失败："+e.getMessage()+","+e.getErrorCode());
                    //Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    public void getsummoney(String name,final Get_BmobData<Integer> get_bmobData){
        BmobQuery<Order> query = new BmobQuery<Order>();
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("enter", false);
        query.addWhereEqualTo("name", name);
        query.sum(new String[] { "money" });
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(500);
//执行查询方法
        query.findStatistics(Order.class,new QueryListener<JSONArray>() {

            @Override
            public void done(JSONArray ary, BmobException e) {
                if(e==null){
                    if(ary!=null){//
                        try {
                            JSONObject obj = ary.getJSONObject(0);
                            int sum = obj.getInt("_sumMoney");//_(关键字)+首字母大写的列名
                            get_bmobData.success(sum);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }else{
                        get_bmobData.success(0);
                       // showToast("查询成功，无数据");
                    }
                }else{
                    get_bmobData.fail("失败："+e.getMessage()+","+e.getErrorCode());
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }


}
