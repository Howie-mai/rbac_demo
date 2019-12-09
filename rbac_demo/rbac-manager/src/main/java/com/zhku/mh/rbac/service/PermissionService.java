package com.zhku.mh.rbac.service;

import com.zhku.mh.rbac.bean.Permission;
import com.zhku.mh.rbac.bean.User;
import com.zhku.mh.rbac.dao.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ClassName：
 * Time：2019/12/5 17:05
 * Description：
 * Author： mh
 */
public interface PermissionService {

    Permission quertRootPermission();

    List<Permission> queryChildPermissions(Integer pid);

    List<Permission> queryAll();

    void insertPermission(Permission permission);

    Permission queryById(Integer id);

    void updatePermission(Permission permission);

    void deletePermission(Integer id);

    List<Integer> queryPermissionidsByRoleid(Integer roleid);

    List<Permission> queryPermissionidsByUser(User dbUser);
}
