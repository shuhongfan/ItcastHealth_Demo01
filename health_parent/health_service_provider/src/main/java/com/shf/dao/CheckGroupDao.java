package com.shf.dao;

import com.github.pagehelper.Page;
import com.shf.entity.PageResult;
import com.shf.entity.QueryPageBean;
import com.shf.pojo.CheckGroup;

import java.util.HashMap;
import java.util.List;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);

    public void setCheckGroupAndCheckItem(HashMap<String, Integer> map);

    public Page<CheckGroup> findByCondition(String queryString);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup);

    public void deleteAssocication(Integer id);
}
