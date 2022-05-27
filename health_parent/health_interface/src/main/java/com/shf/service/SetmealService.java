package com.shf.service;

import com.shf.entity.PageResult;
import com.shf.entity.QueryPageBean;
import com.shf.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    public void setSetmealAndCheckGroup(Integer setmealId,Integer[] checkgroupIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public List<Setmeal> findAll();

    public Setmeal findById(int id);

    public List<Map<String, Object>> findSetmealCount();

    public void update(Setmeal setmeal, Integer[] checkgroupIds);

    public List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    public void deleteById(Integer id);
}
