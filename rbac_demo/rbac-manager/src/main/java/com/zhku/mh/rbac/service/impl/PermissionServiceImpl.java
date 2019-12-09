package com.zhku.mh.rbac.service.impl;

import com.zhku.mh.rbac.bean.Permission;
import com.zhku.mh.rbac.bean.User;
import com.zhku.mh.rbac.dao.PermissionDao;
import com.zhku.mh.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName：
 * Time：2019/12/5 17:05
 * Description：
 * Author： mh
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;


    @Override
    public Permission quertRootPermission() {
        return permissionDao.quertRootPermission();
    }

    @Override
    public List<Permission> queryChildPermissions(Integer pid) {
        return permissionDao.queryChildPermissions(pid);
    }

    @Override
    public List<Permission> queryAll() {
        return permissionDao.queryAll();
    }

    @Override
    public void insertPermission(Permission permission) {
        permissionDao.insertPermission(permission);
    }

    @Override
    public Permission queryById(Integer id) {
        return permissionDao.queryById(id);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionDao.updatePermission(permission);
    }

    @Override
    public void deletePermission(Integer id) {
        permissionDao.deletePermission(id);
    }

    @Override
    public List<Integer> queryPermissionidsByRoleid(Integer roleid) {
        return permissionDao.queryPermissionidsByRoleid(roleid);
    }

    @Override
    public List<Permission> queryPermissionidsByUser(User dbUser) {
        return permissionDao.queryPermissionidsByUser(dbUser);
    }
}
