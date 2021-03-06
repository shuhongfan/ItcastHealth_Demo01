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

    public List<Setmeal> findAll();

    public Setmeal findById(int id);

    public List<Map<String, Object>> findSetmealCount();

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(Setmeal setmeal);

    void deleteAssocication(Integer id);

    void deleteById(Integer id);
}
