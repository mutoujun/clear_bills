package com.android.account.clear_bills.View;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.account.clear_bills.Bean.User;
import com.android.account.clear_bills.Interface.Bmob_Login_interface;
import com.android.account.clear_bills.Interface.Callback;
import com.android.account.clear_bills.Interface.Filesave;
import com.android.account.clear_bills.Public_Data;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.View.Fragment.Register_Fragment;
import com.android.account.clear_bills.ViewModel.Bmob_Net;
import com.android.account.clear_bills.ViewModel.FileOnline;
import com.android.account.clear_bills.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityLoginBinding activityLoginBinding;
    private Filesave filesave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding=DataBindingUtil.setContentView(this,R.layout.activity_login);

        //绑定控件
        init();
    }

    private void init(){
        activityLoginBinding.setOnClickListener(this);
        activityLoginBinding.loginAccount.setText(User.getCurrentUser(User.class)==null?"":User.getCurrentUser(User.class).getUsername());
        filesave = FileOnline.getOnline();
        filesave.getpassword(new Callback<String>() {
            @Override
            public void success(String data) {
                if (data.equals("0")){
                    activityLoginBinding.loginPassword.setText("");
                    activityLoginBinding.rememberPasword.setChecked(false);
                }else {
                    activityLoginBinding.loginPassword.setText(data);
                    activityLoginBinding.rememberPasword.setChecked(true);
                }
            }

            @Override
            public void fail(String message) {

            }
        });
    }

    public void open_resiter(View view){
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.contrain,new Register_Fragment())
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:{
                String account = activityLoginBinding.loginAccount.getText().toString();
                final String password = activityLoginBinding.loginPassword.getText().toString();
                if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
                    Toast.makeText(this, "请输入帐号或密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bmob_Net.getBmob_net(this).login(account, password, new Bmob_Login_interface() {
                    @Override
                    public void success(int code, String message,String user) {
                        if (code==1){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("extra_name",user);
                            Public_Data.user = user;
                            startActivity(intent);
                            finish();
                            filesave.savepassword(activityLoginBinding.rememberPasword.isChecked()?true:false,password);
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }else if(code==0){
                            Toast.makeText(LoginActivity.this,"帐号或密码不正确，请重新输入!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }
}
