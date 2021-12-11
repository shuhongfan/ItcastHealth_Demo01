package com.shf.dao;

import com.github.pagehelper.Page;
import com.shf.pojo.CheckGroup;
import com.shf.pojo.Setmeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SetmealDao {
    public void add(Setmeal setmeal);

    public void setSetmealAndCheckGroup(Map map);

    public Page<Setmeal> findByCondition(String queryString);
}
