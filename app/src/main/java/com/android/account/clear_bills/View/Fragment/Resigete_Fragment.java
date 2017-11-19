package com.android.account.clear_bills.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.account.clear_bills.View.MainActivity;
import com.android.account.clear_bills.R;

/**
 * Created by Administrator on 2017/11/18.
 */

public class Resigete_Fragment extends Fragment implements View.OnClickListener{
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
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getFragmentManager().popBackStack();
                break;
        }
    }
}
