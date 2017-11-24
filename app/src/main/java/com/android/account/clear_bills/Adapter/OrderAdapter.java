package com.android.account.clear_bills.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.account.clear_bills.Bean.Order;
import com.android.account.clear_bills.Public_Data;
import com.android.account.clear_bills.R;
import com.android.account.clear_bills.databinding.ItemOrderBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/11/19.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> orderList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemOrderBinding itemView;
        public ViewHolder(ItemOrderBinding itemView) {
            super(itemView.getRoot());
            this.itemView=itemView;
        }
        public ItemOrderBinding getItemView(){
            return itemView;
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
        ItemOrderBinding itemOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_order,parent,false);
        return new ViewHolder(itemOrderBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.getItemView().tvUser.setText(order.getName());
        String userName = holder.getItemView().tvUser.getText().toString();
        if(userName.equals("陈煜")){
            holder.getItemView().setColorLabel(Color.BLACK);
            holder.getItemView().itemOrderCardView.setCardBackgroundColor(Color.parseColor("#ffaa00"));
            holder.getItemView().setColorText(Color.BLUE);
        }else {
            holder.getItemView().setColorLabel(Color.WHITE);
            holder.getItemView().itemOrderCardView.setCardBackgroundColor(Color.parseColor("#0000ff"));
            holder.getItemView().setColorText(Color.parseColor("#ffff9900"));
        }
        holder.getItemView().tvMoney.setText(order.getMoney().toString());
        holder.getItemView().tvRemark.setText(order.getRemark());
        if (order.getCreatedAt()==null){
            holder.getItemView().tvDate.setText(updateDateDisplay());
        }else{
            holder.getItemView().tvDate.setText(order.getCreatedAt().substring(0,order.getCreatedAt().length()-5));
        }

       
    }
    private String updateDateDisplay() {
        String time=new StringBuilder().append(Public_Data.mYear).append("-")
                .append(Public_Data.mMonth + 1).append("-").append(Public_Data.mDay).toString();
        return time;
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void add(Order order){
        orderList.add(0,order);
        notifyDataSetChanged();
    }

    public void refresh(List<Order> orderList){
        this.orderList=orderList;
        notifyDataSetChanged();
    }





}
