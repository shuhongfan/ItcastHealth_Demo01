package com.shf.service;

import com.shf.entity.PageResult;
import com.shf.entity.QueryPageBean;
import com.shf.entity.Result;
import com.shf.pojo.CheckGroup;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CheckGroupService {
    public void add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    public List<CheckGroup> findAll();
}
