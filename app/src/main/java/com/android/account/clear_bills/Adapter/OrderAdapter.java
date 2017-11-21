package com.android.account.clear_bills.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.account.clear_bills.Bean.Order;
import com.android.account.clear_bills.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/19.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> orderList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvUser,tvMoney,tvRemark;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            tvUser = (TextView) itemView.findViewById(R.id.tv_user);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            tvRemark = (TextView)itemView.findViewById(R.id.tv_remark);
        }
    }

    public OrderAdapter(List<Order> orderList){
        this.orderList = orderList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvUser.setText(order.getName());
        holder.tvMoney.setText(order.getMoney().toString());
        holder.tvRemark.setText(order.getRemark());
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void add(Order order){
        orderList.add(order);
        notifyDataSetChanged();
    }

    public void refresh(List<Order> orderList){
        this.orderList=orderList;
        notifyDataSetChanged();
    }





}
