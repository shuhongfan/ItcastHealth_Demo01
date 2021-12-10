package com.shf.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shf.dao.CheckItemDao;
import com.shf.entity.PageResult;
import com.shf.entity.QueryPageBean;
import com.shf.pojo.CheckItem;
import com.shf.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
//    注入DAO对象
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 检查项分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
//        完成分页查询 基于mybatis框架提供给的分页助手完成
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total,rows);
    }

    /**
     * 根据id删除检查项
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
//        判断当前检查项是否已经关联检查组
        long count = checkItemDao.findCountByCheckItemId(id);
        if (count>0){
//            当前检查项已经关联检查组,不允许删除
            new RuntimeException();
        }
        checkItemDao.deleteById(id);
    }

    /**
     * 编辑检查项
     * @param checkItem
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
