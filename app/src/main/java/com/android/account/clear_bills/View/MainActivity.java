package com.android.account.clear_bills.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.account.clear_bills.Adapter.OrderAdapter;
import com.android.account.clear_bills.Bean.Order;
import com.android.account.clear_bills.Interface.Bmob_Login_interface;
import com.android.account.clear_bills.Interface.Get_BmobData;
import com.android.account.clear_bills.Public_Data;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.ViewModel.Bmob_Net;


import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText money,remark;
    OrderAdapter adapter;
    TextView cyNum,sjNum;
    String[] name={"陈煜","沙杰"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化绑定视图ID
        init();
        initGetOrderData();
        initGetUserSum();
        refreshOrderData();

    }

    private void initGetUserSum() {
        cyNum = (TextView) findViewById(R.id.tv_cy_num);
        sjNum = (TextView) findViewById(R.id.tv_sj_num);
        sumMoney(name);
    }

    private void refreshOrderData() {
        final SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRef_order);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Bmob_Net.getBmob_net(MainActivity.this).searchorder(new Get_BmobData<List<Order>>() {
                    @Override
                    public void success(List<Order> data) {
                        adapter.refresh(data);
                        swipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void fail(String message) {
                        Toast.makeText(MainActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
                sumMoney(name);
            }
        });
    }

    private void initGetOrderData() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_order);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        Bmob_Net.getBmob_net(this).searchorder(new Get_BmobData<List<Order>>() {
            @Override
            public void success(List<Order> data) {
                adapter = new OrderAdapter(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void fail(String message) {
                Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add:
                View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_order, null);
                money = (EditText) dialogView.findViewById(R.id.money);
                remark = (EditText) dialogView.findViewById(R.id.remark);
                new AlertDialog.Builder(this).setTitle(Public_Data.user).setView(dialogView)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (TextUtils.isEmpty(money.getText().toString()) || TextUtils.isEmpty(remark.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Bmob_Net.getBmob_net(MainActivity.this).addorder(Float.parseFloat(money.getText().toString()), remark.getText().toString(), new Bmob_Login_interface() {
                                    @Override
                                    public void success(int code, String message, String user) {
                                        Order order = new Order();
                                        order.setName(Public_Data.user);
                                        order.setMoney(Float.parseFloat(money.getText().toString()));
                                        order.setRemark(remark.getText().toString());
                                        adapter.add(order);
                                        if (code == 0) {
                                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }).create().show();
        }
        return super.onOptionsItemSelected(item);
    }


    void sumMoney(final String[] name){

        Bmob_Net.getBmob_net(this).getsummoney(name[0],new Get_BmobData<Integer>() {
            @Override
            public void success(Integer data) {
                cyNum.setText(name[0].substring(0,1)+": ￥ "+data.toString());
            }

            @Override
            public void fail(String message) {

            }
        });
        Bmob_Net.getBmob_net(this).getsummoney(name[1],new Get_BmobData<Integer>() {
            @Override
            public void success(Integer data) {
                sjNum.setText(name[1].substring(0,1)+": ￥ "+data.toString());
            }

            @Override
            public void fail(String message) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
