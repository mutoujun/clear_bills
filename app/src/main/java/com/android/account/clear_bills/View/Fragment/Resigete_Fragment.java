package com.android.account.clear_bills.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.account.clear_bills.Interface.Bmob_Login_interface;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.ViewModel.Bmob_Net;

/**
 * Created by Administrator on 2017/11/18.
 */

public class Resigete_Fragment extends Fragment implements View.OnClickListener{
    EditText name,account,password,repassword;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.enroll_account,container,false);
        //绑定控件
        init(view);
        return view;
    }

    private void init(View view) {
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.enroll_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("");
        ImageView imageBack = (ImageView) view.findViewById(R.id.img_back);
        imageBack.setOnClickListener(this);
        Button encrollButton = (Button) view.findViewById(R.id.btn_encroll);
        encrollButton.setOnClickListener(this);
        name = (EditText) view.findViewById(R.id.enroll_name);
        account = (EditText) view.findViewById(R.id.enroll_account);
        password = (EditText) view.findViewById(R.id.enroll_passwrd);
        repassword = (EditText) view.findViewById(R.id.enroll_repasswrd);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_encroll:
                if(TextUtils.isEmpty(name.getText())||TextUtils.isEmpty(password.getText())||
                        TextUtils.isEmpty(account.getText())||TextUtils.isEmpty(repassword.getText())){
                    Toast.makeText(getContext(), "信息不完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.getText().toString().equals(repassword.getText().toString())){
                    Toast.makeText(getContext(), "两次密码不相等", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bmob_Net.getBmob_net(getContext()).register(name.getText().toString(), account.getText().toString(), password.getText().toString(), new Bmob_Login_interface() {
                    @Override
                    public void success(int code, String message) {
                        if(code==1){
                            getFragmentManager().popBackStack();
                        }
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
}
