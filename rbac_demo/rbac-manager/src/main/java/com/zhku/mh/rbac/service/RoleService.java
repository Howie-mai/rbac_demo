package com.zhku.mh.rbac.service;

import com.zhku.mh.rbac.bean.Role;

import java.util.List;
import java.util.Map;

/**
 * ClassName：
 * Time：2019/12/5 17:05
 * Description：
 * Author： mh
 */
public interface RoleService {
    List<Role> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    List<Role> queryAll();

    void insertRolePermission(Map<String, Object> paramMap);

    void deleteRolePermissions(Map<String, Object> paramMap);
}
