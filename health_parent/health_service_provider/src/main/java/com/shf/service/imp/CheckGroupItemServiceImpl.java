package com.shf.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shf.dao.CheckGroupDao;
import com.shf.entity.PageResult;
import com.shf.entity.QueryPageBean;
import com.shf.entity.Result;
import com.shf.pojo.CheckGroup;
import com.shf.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 检查组服务
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupItemServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

//    新增检查组,同时需要让检查组关联检查项
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
//        新增检查组  操作 t_checkgroup表
        checkGroupDao.add(checkGroup);
//        设置检查组和检查项的多对多的关联关系,操作t_checkGroup_checkitem表
        this.setCheckGroupAndCheckItem(checkGroup,checkitemIds);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 编辑检查组信息，同时需要关联检查项
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
//        修改检查组基本信息,操作检查组t_checkgroup表
        checkGroupDao.edit(checkGroup);
//        清理当前检查组关联的检查项,操作中间关系表t_checkgroup_checkitem表
        checkGroupDao.deleteAssocication(checkGroup.getId());
//        重新建立当前检查组和检查项的关联关系
        this.setCheckGroupAndCheckItem(checkGroup,checkitemIds);
    }

    /**
     * 建立检查组和检查项的多对多关系
     */
    public void setCheckGroupAndCheckItem(CheckGroup checkGroup, Integer[] checkitemIds){
        Integer checkGroupId = checkGroup.getId();
        if (checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                HashMap<String, Integer> map = new HashMap<String, Integer>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
