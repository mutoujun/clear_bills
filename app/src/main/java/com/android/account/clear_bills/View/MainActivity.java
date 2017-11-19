package com.android.account.clear_bills.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.account.clear_bills.Bean.Order;
import com.android.account.clear_bills.Interface.Bmob_Login_interface;
import com.android.account.clear_bills.Interface.Get_BmobData;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.View.Fragment.Resigete_Fragment;
import com.android.account.clear_bills.ViewModel.Bmob_Net;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText account,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        init();
    }

    private void init(){
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        account = (EditText) findViewById(R.id.login_account);
        password = (EditText) findViewById(R.id.login_password);
    }

    public void open_resiter(View view){
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.contrain,new Resigete_Fragment())
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:{

                Bmob_Net.getBmob_net(this).login(account.getText().toString(), password.getText().toString(), new Bmob_Login_interface() {
                    @Override
                    public void success(int code, String message,String user) {
                        if (code==1){
                            Intent intent = new Intent(MainActivity.this,DetailListActivity.class);
                            intent.putExtra("extra_name",user);
                            startActivity(intent);
                        }
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }
}
