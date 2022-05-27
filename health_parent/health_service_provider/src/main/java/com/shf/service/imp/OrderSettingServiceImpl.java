package com.shf.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.shf.dao.OrderSettingDao;
import com.shf.pojo.OrderSetting;
import com.shf.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 预约设置服务
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入预约设置数据
     * @param list
     */
    @Override
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0){
            for (OrderSetting orderSetting : list){
//                判断当前日期是否已经进行了预约设置
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate > 0){
//                    已经进行了预约设置,执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
//                    没有进行预约设置,支持插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

//    根据月份查询对应的预约设置数据
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String[] split = date.split("-");

        Map<String,String> map = new HashMap<>();
        map.put("y",split[0]);
        map.put("m",split[1]);
//        调用dao，根据日期范围查询预约设置数据
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if (list == null || list.size() > 0){
            for (OrderSetting orderSetting : list) {
                HashMap<String, Object> m = new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate());  // 获取日期数字
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }

    /**
     * 根据日期这是预约数据
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
//        根据日期查询是否已经进行了预约设置
        Date orderDate = orderSetting.getOrderDate();
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if (count > 0) {
//            当前日期已经进行了预约设置,需要执行更新操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        } else {
//            当前日期没有哪些预约设置,需要执行插入操作
            orderSettingDao.add(orderSetting);
        }
    }
}
