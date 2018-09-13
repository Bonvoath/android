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

    public String getPrice() {
        return _price;
    }

    public void setPrice(String price) {
        this._price = price;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    private String _orderNum;
    private String _orderDate;
    private String _address;
    private double _lat;
    private double _lng;
    private String _price;
    private String _remark;
    private String _name;
}
