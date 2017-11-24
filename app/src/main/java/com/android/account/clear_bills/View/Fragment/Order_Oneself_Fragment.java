package com.android.account.clear_bills.View.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.account.clear_bills.Adapter.OrderAdapter;
import com.android.account.clear_bills.Bean.Order;
import com.android.account.clear_bills.Interface.Callback;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.ViewModel.Bmob_Net;
import com.android.account.clear_bills.databinding.FragmentOrderOneselfBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class Order_Oneself_Fragment extends Fragment {
    private FragmentOrderOneselfBinding fragmentOrderOneself;
    private OrderAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentOrderOneself = DataBindingUtil.inflate(inflater,R.layout.fragment_order_oneself,container,false);
        initGetOrderData();
        return fragmentOrderOneself.getRoot();
    }
    private void initGetOrderData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        fragmentOrderOneself.recOrderOneself.setLayoutManager(layoutManager);
        Bmob_Net.getBmob_net(getContext()).searchorder(new Callback<List<Order>>() {
            @Override
            public void success(List<Order> data) {
                adapter = new OrderAdapter(data);
                fragmentOrderOneself.recOrderOneself.setAdapter(adapter);
            }

            @Override
            public void fail(String message) {
                Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
