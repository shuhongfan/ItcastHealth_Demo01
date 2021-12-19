package com.shf.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.shf.dao.PermissionDao;
import com.shf.dao.RoleDao;
import com.shf.dao.UserDao;
import com.shf.pojo.Permission;
import com.shf.pojo.Role;
import com.shf.pojo.User;
import com.shf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(interfaceClass=UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

//    根据用户查询数据库获取用户信息和关联的角色信息,同时需要查询角色关联的权限信息
    @Override
    public User findByUsername(String username) {
//        查询用户基本信息，不包含用户的角色
        User user = userDao.findByUsername(username);
        if (user == null){
            return null;
        }
        Integer id = user.getId();
//        根据用户ID查询对应的角色
        Set<Role> roleSet = roleDao.findByUserId(id);
        for (Role role : roleSet) {
            Integer roleId = role.getId();
//            根据角色ID查询关联的权限
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
//            让角色关联权限
            role.setPermissions(permissions);
        }
//        让用户关联角色
        user.setRoles(roleSet);
        return user;
    }
}
