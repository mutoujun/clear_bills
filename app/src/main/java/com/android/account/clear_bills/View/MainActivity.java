package com.android.account.clear_bills.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.account.clear_bills.Interface.Bmob_Login_interface;
import com.android.account.clear_bills.View.Fragment.Resigete_Fragment;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.ViewModel.Bmob_Net;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        init();
        setupNavButton();
    }

    private void init(){
        Toolbar toolbar= (Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
    }

    private void setupNavButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEnrollAccount(View view){
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.contrain,new Resigete_Fragment())
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:{
                Intent intent = new Intent(MainActivity.this,DetailListActivity.class);
                String name = "用户名"
                intent.putExtra()
            }
        }
    }
}
