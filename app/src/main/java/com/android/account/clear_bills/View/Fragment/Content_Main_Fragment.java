package com.android.account.clear_bills.View.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.account.clear_bills.Adapter.OrderAdapter;
import com.android.account.clear_bills.Bean.Order;
import com.android.account.clear_bills.Interface.Callback;
import com.android.account.clear_bills.Public_Data;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.ViewModel.Bmob_Net;
import com.android.account.clear_bills.databinding.ContentMainBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class Content_Main_Fragment extends Fragment {
    private ContentMainBinding contentMain;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentMain = DataBindingUtil.inflate(inflater,R.layout.content_main,container,false);
        initGetOrderData();
        refreshOrderData();
        return contentMain.getRoot();
    }

    private void initGetOrderData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        contentMain.recOrder.setLayoutManager(layoutManager);
        Bmob_Net.getBmob_net(getContext()).searchorder(new Callback<List<Order>>() {
            @Override
            public void success(List<Order> data) {
                Public_Data.orderAdapter = new OrderAdapter(data);
                contentMain.recOrder.setAdapter(Public_Data.orderAdapter);
            }

            @Override
            public void fail(String message) {
                Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void refreshOrderData() {
        contentMain.swipeRefOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Bmob_Net.getBmob_net(getActivity()).searchorder(new Callback<List<Order>>() {
                    @Override
                    public void success(List<Order> data) {
                        Public_Data.orderAdapter.refresh(data);
                        contentMain.swipeRefOrder.setRefreshing(false);
                    }
                    @Override
                    public void fail(String message) {
                        Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_SHORT).show();
                        contentMain.swipeRefOrder.setRefreshing(false);
                    }
                });
                Public_Data.sumMoney(getContext(),Public_Data.name,Public_Data.activityMain);
            }
        });
    }

}
