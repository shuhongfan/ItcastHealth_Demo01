package com.shf.service;

import com.shf.entity.PageResult;
import com.shf.entity.QueryPageBean;
import com.shf.entity.Result;
import com.shf.pojo.CheckItem;

import java.util.List;

//服务接口
public interface CheckItemService {
    public void add(CheckItem checkItem);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public void deleteById(Integer id);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll();
}
