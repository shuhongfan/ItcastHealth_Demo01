package com.shf.service;

import com.shf.entity.PageResult;
import com.shf.entity.QueryPageBean;
import com.shf.pojo.Setmeal;

public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    public void setSetmealAndCheckGroup(Integer setmealId,Integer[] checkgroupIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);
}
