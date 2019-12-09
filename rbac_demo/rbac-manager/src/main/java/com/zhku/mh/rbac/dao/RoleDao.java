package com.zhku.mh.rbac.dao;

import com.zhku.mh.rbac.bean.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * ClassName：
 * Time：2019/12/5 17:06
 * Description：
 * Author： mh
 */
public interface RoleDao {
    List<Role> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    @Select("select * from t_role")
    List<Role> queryAll();

    void insertRolePermission(Map<String, Object> paramMap);

    void deleteRolePermissions(Map<String, Object> paramMap);

}
