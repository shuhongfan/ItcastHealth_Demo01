package com.shf.dao;

import com.shf.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date orderDate);
    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    public OrderSetting findByOrderDate(Date orderDate);

    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);
}
