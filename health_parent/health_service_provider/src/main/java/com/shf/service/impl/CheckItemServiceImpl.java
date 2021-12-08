package com.shf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shf.POJO.CheckItem;
import com.shf.dao.CheckItemDao;
import com.shf.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

//检查服务
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
//    注入DAO对象
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem item) {
        checkItemDao.add(item);
    }
}
