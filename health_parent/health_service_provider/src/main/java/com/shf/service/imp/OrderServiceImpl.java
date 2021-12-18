package com.shf.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.shf.constant.MessageConstant;
import com.shf.dao.MemberDao;
import com.shf.dao.OrderDao;
import com.shf.dao.OrderSettingDao;
import com.shf.entity.Result;
import com.shf.pojo.Member;
import com.shf.pojo.Order;
import com.shf.pojo.OrderSetting;
import com.shf.service.OrderService;
import com.shf.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 体检预约服务
 */
@Service(interfaceClass =OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

//    体检预约
    @Override
    public Result order(Map map) throws Exception {
//        1.检查用户所选择的预约日期是否已经提前进行了设置,如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(DateUtils.parseString2Date(orderDate));
        if (orderSetting == null){
//            指定日期没有进行预约设置,无法完成体检预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

//        2.检查用户所选择的预约日期是否已经约满,如果已经约满则无法预约
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (reservations>=number){
//            已经约满,无法预约
            return new Result(false, MessageConstant.ORDER_FULL);
        }

//        3.检查用户是否重复预约(同一个用户在同一天预约了同一个套餐),如果是重复预约则无法完成再次预约
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if (member != null){
//            判断是否在重复预约
//            会员id
            Integer memberId = member.getId();
//            预约日期
            Date order_Date = DateUtils.parseString2Date(orderDate);
//            套餐ID
            String setmealId = (String) map.get("setmealId");
            Order order = new Order(memberId,order_Date,Integer.parseInt(setmealId));
//            根据条件查询
            List<Order> list = orderDao.findByCondition(order);
            if (list != null  && list.size() > 0) {
//                说明用户在重复预约,无法完成预约
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        } else {
//        4.检查当前用户是否为会员,如果是会员则直接完成预约,如果不是会员则自动完成注册并进行预约
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
//            自动完成会员注册
            memberDao.add(member);
        }
//        5.预约成功,更新当日的已预约人数
        Order order = new Order();
//        设置会员ID
        order.setMemberId(member.getId());
//        预约日期
        order.setOrderDate(DateUtils.parseString2Date(orderDate));
//        预约类型
        order.setOrderType((String) map.get("orderType"));
//        到诊状态
        order.setOrderStatus(Order.ORDERSTATUS_NO);
//        设置套餐ID
        System.out.println(map.get("setmealId"));
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);

//        设置已预约人数+1
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    //根据id查询预约信息，包括体检人信息、套餐信息
    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if(map != null){
            // 处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }
}
