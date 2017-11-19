package com.android.account.clear_bills.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.account.clear_bills.Interface.Bmob_Login_interface;
import com.android.account.clear_bills.Public_Data;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.ViewModel.Bmob_Net;

public class DetailListActivity extends AppCompatActivity implements View.OnClickListener{
    View dialogView;
    EditText money,things;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        //初始化绑定视图ID
        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.add:
                dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_detail,null);
                money = (EditText)dialogView.findViewById(R.id.money);
                things = (EditText)dialogView.findViewById(R.id.things);
                new AlertDialog.Builder(this).setTitle(Public_Data.user)
                                                        .setView(dialogView)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(TextUtils.isEmpty(money.getText().toString())||TextUtils.isEmpty(things.getText().toString())){
                                    Toast.makeText(DetailListActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Bmob_Net.getBmob_net(DetailListActivity.this).addorder( Float.parseFloat(money.getText().toString()), things.getText().toString(), new Bmob_Login_interface() {
                                    @Override
                                    public void success(int code, String message,String user) {
                                        Toast.makeText(DetailListActivity.this, message, Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:{
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            }

        }
    }
}
