package com.shf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shf.constant.MessageConstant;
import com.shf.constant.RedisConstant;
import com.shf.entity.PageResult;
import com.shf.entity.QueryPageBean;
import com.shf.entity.Result;
import com.shf.pojo.CheckGroup;
import com.shf.pojo.Setmeal;
import com.shf.service.SetmealService;
import com.shf.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.shf.constant.MessageConstant.*;

/**
 * 检查套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

//    使用jedisPool操作Redis服务
    @Autowired
    private JedisPool jedisPool;

    /**
     * 文件上传
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        String originalFilename = imgFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index - 1);
        String fileName = UUID.randomUUID().toString() + extention;
        try {
//            上传文件到七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
//            当用户上传图片后，将图片名称保存到redis的一个Set集合中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.UPLOAD_SUCCESS,fileName);
    }

    /**
     * 新增套餐
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,ADD_SETMEAL_FAIL);
        }
        return new Result(true,ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.pageQuery(queryPageBean);
    }

    /**
     * 根据ID查询套餐
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 根据ID查询检查组包含的多个检查组ID
     */
    @RequestMapping("/findCheckGroupIdsBySetMealId")
    public Result findCheckGroupIdsBySetMealId(Integer id){
        try {
            List<Integer> checkGroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,checkGroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 更新套餐
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.update(setmeal, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,UPDARE_SETMEAL_FAIL);
        }
        return new Result(true,UPDATE_SETMEAL_SUCCESS);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            setmealService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
//            服务调用失败
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
