package com.example.bonvoath.tms.Entities;

public class OrderMaster {
    public OrderMaster(){

    }
    public OrderMaster(String orderNum, String orderDate, String address){
        _orderNum = orderNum;
        _orderDate = orderDate;
        _address = address;
    }

    public String getOrderNumber()
    {
        return  _orderNum;
    }

    public String getOrderDate(){
        return _orderDate;
    }

    public String getAddress() {
        return _address;
    }

    public double getLat(){return _lat;}

    public double getLng(){return  _lng;}

    public String getRemark() {
        return _remark;
    }

    public void setLat(double lat){
        _lat = lat;
    }

    public void setLong(double lng){
        _lng = lng;
    }

    public  void setRemark(String remark){
        _remark = remark;
    }

    private String _orderNum;
    private String _orderDate;
    private String _address;
    private double _lat;
    private double _lng;
    private String _remark;
}
