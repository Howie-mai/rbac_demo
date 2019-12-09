package com.zhku.mh.rbac.service.impl;

import com.zhku.mh.rbac.bean.Role;
import com.zhku.mh.rbac.dao.RoleDao;
import com.zhku.mh.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName：
 * Time：2019/12/5 17:05
 * Description：
 * Author： mh
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;


    public List<Role> pageQueryData(Map<String, Object> map) {
        return roleDao.pageQueryData(map);
    }

    public int pageQueryCount(Map<String, Object> map) {
        return roleDao.pageQueryCount(map);
    }

    public List<Role> queryAll() {
        return roleDao.queryAll();
    }

    public void insertRolePermission(Map<String, Object> paramMap) {
        roleDao.deleteRolePermissions(paramMap);
        roleDao.insertRolePermission(paramMap);
    }

    public void deleteRolePermissions(Map<String, Object> paramMap) {
        roleDao.deleteRolePermissions(paramMap);
    }
}
