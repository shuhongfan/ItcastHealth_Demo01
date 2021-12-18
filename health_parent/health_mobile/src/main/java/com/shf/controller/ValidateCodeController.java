package com.shf.controller;

import com.aliyuncs.exceptions.ClientException;
import com.shf.constant.MessageConstant;
import com.shf.entity.Result;
import com.shf.utils.CloopenUtils;
import com.shf.utils.SMSUtils;
import com.shf.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import static com.shf.constant.RedisMessageConstant.SENDTYPE_ORDER;
import static com.shf.utils.SMSUtils.VALIDATE_CODE;

/**
 * 验证码操作
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

//    用户在线体检预约发送验证码
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
//        随机生成4位数字验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
//        给用户发送验证码
        try {
//            SMSUtils.sendShortMessage(VALIDATE_CODE, telephone, validateCode.toString());
            CloopenUtils.sendShortMessage("1", "13437191068", validateCode.toString(), "5");
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
//        将验证码保存到redis (5分钟自动清楚)
        jedisPool.getResource().setex(telephone+SENDTYPE_ORDER, 300, validateCode.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
