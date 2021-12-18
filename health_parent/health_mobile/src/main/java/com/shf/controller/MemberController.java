package com.shf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.shf.constant.MessageConstant;
import com.shf.constant.RedisMessageConstant;
import com.shf.entity.Result;
import com.shf.pojo.Member;
import com.shf.service.MemberService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

//    手机号快速登录
    @PostMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
//        从Redis中获取保存的验证码
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (validateCodeInRedis!=null && validateCode!=null && validateCodeInRedis.equals(validateCode)){
//            验证码输入正确
//            判断当前用户是否为会员
            Member member = memberService.findByTelephone(telephone);
            if (member == null) {
//                不是会员 自动完成注册 （自动将当前用户信息保存到会员列表）
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                memberService.add(member);
            }
//            向客户端浏览器写入cookie,内容为手机号
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");
//            有效期30天
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
//            将会员信息保存到Redis中
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone, 60*30, json);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        } else {
//            验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }
}