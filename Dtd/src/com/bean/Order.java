package com.bean;

import java.lang.reflect.Field;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/29 8:46
 * @description：
 * @modified By：
 */
public class Order {

    private long id;
    private String orderNo;
    private String address;
    private float price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        System.out.println(" sadsads= ");
        Class c  = Class.forName("com.bean.Order");
        Object obj = c.newInstance();
        Field f = c.getDeclaredField("address");
        System.out.println("f = " + f);
        f.setAccessible(true);
        f.set(obj,String.valueOf(1));
    }
}
