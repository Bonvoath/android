package com.example.bonvoath.tms.Entities;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderSearch {
    public OrderSearch(){
        this.count = 0;
        this.Orders = new ArrayList<>();
    }
    public OrderSearch(String tag, int count){
        this.tag = tag;
        this.count = count;
        this.Orders = new ArrayList<>();
    }
    public String getTag() {
        return tag;
    }

    public int getCount() {
        return count;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void add(int num){
        this.count += num;
    }

    public List<OrderMaster> getOrders() {
        return Orders;
    }

    public void addOrders(OrderMaster orders) {
        this.Orders.add(orders);
    }

    private List<OrderMaster> Orders;

    private String tag;
    private int count;
}
