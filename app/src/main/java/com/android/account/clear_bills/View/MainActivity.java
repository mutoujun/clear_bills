package com.android.account.clear_bills.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.account.clear_bills.Interface.Bmob_Login_interface;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.View.Fragment.Resigete_Fragment;
import com.android.account.clear_bills.ViewModel.Bmob_Net;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText accountEdit,passwordEdit;
    private SharedPreferences sPref;
    private CheckBox remPword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        init();
        saveAccountData();
    }
    private void saveAccountData() {
        //1.获取SharedPreferences对象
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        //2.判断是否记住密码
        boolean isRemember = sPref.getBoolean("remPword",false);
        //如果记住密码，将帐号和密码设置到文本框中
        if (isRemember){
            String account = sPref.getString("account","");
            String password = sPref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            remPword.setChecked(true);
        }else {
            String account = sPref.getString("account","");
            accountEdit.setText(account);
        }

    }

    private void init(){
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        accountEdit = (EditText) findViewById(R.id.login_account);
        passwordEdit = (EditText) findViewById(R.id.login_password);
        remPword = (CheckBox) findViewById(R.id.remember_pasword);
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
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
                    Toast.makeText(this, "请输入帐号或密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bmob_Net.getBmob_net(this).login(accountEdit.getText().toString(), passwordEdit.getText().toString(), new Bmob_Login_interface() {
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
