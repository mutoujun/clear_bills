package com.android.account.clear_bills.View;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.account.clear_bills.Adapter.OrderAdapter;
import com.android.account.clear_bills.Adapter.OrderPagerAdapter;
import com.android.account.clear_bills.Bean.Order;
import com.android.account.clear_bills.Control.OnCheckedChangeListener;
import com.android.account.clear_bills.Control.TickView;
import com.android.account.clear_bills.Interface.Bmob_Login_interface;
import com.android.account.clear_bills.Interface.Callback;
import com.android.account.clear_bills.Public_Data;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.View.Fragment.Content_Main_Fragment;
import com.android.account.clear_bills.View.Fragment.Order_Oneself_Fragment;
import com.android.account.clear_bills.ViewModel.Bmob_Net;
import com.android.account.clear_bills.databinding.ActivityMainBinding;
import com.android.account.clear_bills.databinding.DialogOrderBinding;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    OrderAdapter adapter;
    private Calendar calendar;
    private  DialogOrderBinding dialogView;
    private Content_Main_Fragment mainFragment;
    private Order_Oneself_Fragment oneFragment;
    private List<Fragment> fragmentList;
    private OrderPagerAdapter<Fragment> orderPagerAdapter;

    private DatePickerDialog.OnDateSetListener mDataSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Public_Data.mYear = i;
            Public_Data.mMonth = i1;
            Public_Data.mDay = i2;
            updateDateDisplay();
        }
    };
    /**
     * 更新日期显示(记得month要+1，因为DatePicker索引是0-11)
     */
    private void updateDateDisplay() {
        dialogView.btnDate.setText(new StringBuilder().append(Public_Data.mYear).append("-")
                .append(Public_Data.mMonth + 1).append("-").append(Public_Data.mDay).toString());
    }

    private void initSetDataTime(){
        final Calendar calendar = Calendar.getInstance();
        Public_Data.mYear = calendar.get(Calendar.YEAR);
        Public_Data.mMonth = calendar.get(Calendar.MONTH);
        Public_Data.mDay = calendar.get(Calendar.DAY_OF_MONTH);
        updateDateDisplay();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Public_Data.activityMain = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //初始化绑定视图ID
        initToolbar();
//        initGetOrderData();
        initGetOrderPager();
        initGetUserSum();
        setupTickViewCheckedEvent();

    }

    private void initGetOrderPager() {
        mainFragment = new Content_Main_Fragment();
        oneFragment = new Order_Oneself_Fragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(mainFragment);
        fragmentList.add(oneFragment);
        orderPagerAdapter = new OrderPagerAdapter<>(getSupportFragmentManager(),fragmentList);
        Public_Data.activityMain.orderPager.setAdapter(orderPagerAdapter);
    }

    private void setupTickViewCheckedEvent() {
        Public_Data.activityMain.tickView.getConfig().setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(TickView tickView, boolean isCheck) {
                recordBills();
            }
        });
    }

    private void initGetUserSum() {
        Public_Data.sumMoney(MainActivity.this,Public_Data.name,Public_Data.activityMain);
    }



    private void initGetOrderData() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_order);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        Bmob_Net.getBmob_net(this).searchorder(new Callback<List<Order>>() {
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


    private void initToolbar() {
        setSupportActionBar(Public_Data.activityMain.toolbar);
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
                recordBills();
                break;
            case R.id.clear:
                clearBills();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearBills() {
        double cyNum = Double.parseDouble(Public_Data.activityMain.tvCyNum.getText().toString().substring(5));
        double sjNum = Double.parseDouble(Public_Data.activityMain.tvSjNum.getText().toString().substring(5));
        double resultNum = (cyNum-sjNum)/2.0;
        new AlertDialog.Builder(MainActivity.this).setTitle("清算账单")
                .setMessage(resultNum>0? "沙给陈："+String.valueOf(Math.abs(resultNum))
                        : "陈给沙："+String.valueOf(Math.abs(resultNum)))
                .create().show();
    }

    private void recordBills() {
        dialogView =DataBindingUtil.inflate( LayoutInflater.from(MainActivity.this),
                R.layout.dialog_order,null,false) ;
        initSetDataTime();
        dialogView.btnDate.setOnClickListener(this);
        new AlertDialog.Builder(this).setTitle(Public_Data.user).setView(dialogView.getRoot())
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if ("".equals(dialogView.money.getText().toString())|| "".equals(dialogView.remark.getText().toString())) {
                            Toast.makeText(MainActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Bmob_Net.getBmob_net(MainActivity.this).addorder(Float.parseFloat(dialogView.money.getText().toString()), dialogView.remark.getText().toString(), new Bmob_Login_interface() {
                            @Override
                            public void success(int code, String message, String user) {
                                Order order = new Order();
                                order.setName(Public_Data.user);
                                order.setMoney(Float.parseFloat(dialogView.money.getText().toString()));
                                order.setRemark(dialogView.remark.getText().toString());
                                adapter.add(order);
                                Public_Data.sumMoney(MainActivity.this,Public_Data.name,Public_Data.activityMain);
                                Toast.makeText(MainActivity.this, "创建成功，请核对账单", Toast.LENGTH_SHORT).show();
                                if (code == 0) {
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).create().show();
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_date:
                new DatePickerDialog(MainActivity.this,mDataSetListener,Public_Data.mYear,Public_Data.mMonth,Public_Data.mDay).show();
        }

    }
}
