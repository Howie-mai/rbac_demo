package com.zhku.mh.rbac.dao;

import com.zhku.mh.rbac.bean.Permission;
import com.zhku.mh.rbac.bean.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName：
 * Time：2019/12/5 17:06
 * Description：
 * Author： mh
 */
public interface PermissionDao {
    @Select("select * from t_permission where pid is null")
    Permission quertRootPermission();

    @Select("select * from t_permission where pid = #{pid}")
    List<Permission> queryChildPermissions(Integer pid);

    @Select("select * from t_permission")
    List<Permission> queryAll();

    void insertPermission(Permission permission);

    @Select("select * from t_permission where id = #{id}")
    Permission queryById(Integer id);

    void updatePermission(Permission permission);

    void deletePermission(Integer id);

    @Select("select permissionid from t_role_permission where roleid = #{roleid}")
    List<Integer> queryPermissionidsByRoleid(Integer roleid);

    List<Permission> queryPermissionidsByUser(User dbUser);
}
