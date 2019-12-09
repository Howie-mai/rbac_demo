package com.zhku.mh.rbac.controller;

import com.zhku.mh.rbac.bean.AJAXResult;
import com.zhku.mh.rbac.bean.Permission;
import com.zhku.mh.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName：
 * Time：2019/12/5 17:04
 * Description：
 * Author： mh
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index() {
        return "permission/index";
    }

    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        List<Permission> permissions = new ArrayList<>();

        //读取树形结构
//        Permission root = permissionService.quertRootPermission();
//
//        List<Permission> childPermissions = permissionService.queryChildPermissions(root.getId());
//        root.setChildren(childPermissions);
//
//          permissions.add(root);
//        Permission parent = new Permission();
//        parent.setId(0);
//        queryChildPermissions(parent);
        //查询所有
        List<Permission> ps = permissionService.queryAll();

//        for (Permission p : ps) {
//            Permission child = p;
//            if (p.getPid() == 0) {
//                permissions.add(p);
//            }else {
//                for (Permission innerPermission : ps){
//                    if(child.getPid().equals(innerPermission.getId())){
//                        Permission parent = innerPermission;
//                        parent.getChildren().add(child);
//                        break;
//                    }
//                }
//            }
//        }
        Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
        for (Permission p : ps) {
            permissionMap.put(p.getId(), p);
        }
        for ( Permission p : ps ) {
            Permission child = p;
            if ( child.getPid() == 0 ) {
                permissions.add(p);
            } else {
                Permission parent = permissionMap.get(child.getPid());
                parent.getChildren().add(child);
            }
        }

        return permissions;
    }

    /**
     * 递归查询许可信息
     * 1） 方法自己调用自己
     * 2）方法一定要存在跳出逻辑
     * 3）方法调用时，参数之间应该有规律
     * 4） 递归算法，效率比较低
     *
     * @Param parent
     * @Return: void
     * @Author: mh
     * @Date: 2019/12/9 11:06
     */
    private void queryChildPermissions(Permission parent) {
        List<Permission> childPermissions = permissionService.queryChildPermissions(parent.getId());

        for (Permission permission : childPermissions) {
            queryChildPermissions(permission);
        }

        parent.setChildren(childPermissions);
    }

    @RequestMapping("/add")
    public String add(){
        return "permission/add";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public AJAXResult insert(Permission permission){
        AJAXResult result = new AJAXResult();

        try {
            permissionService.insertPermission(permission);
            result.setSuccess(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @RequestMapping("/edit")
    public String edit( Integer id, Model model ) {

        Permission permission = permissionService.queryById(id);
        model.addAttribute("permission", permission);

        return "permission/edit";
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update( Permission permission ) {
        AJAXResult result = new AJAXResult();

        try {
            permissionService.updatePermission(permission);
            result.setSuccess(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete( Integer id) {
        AJAXResult result = new AJAXResult();

        try {
            permissionService.deletePermission(id);
            result.setSuccess(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/loadAssignData")
    public Object loadAssignData( Integer roleid ) {
        List<Permission> permissions = new ArrayList<Permission>();
        List<Permission> ps = permissionService.queryAll();

        // 获取当前角色已经分配的许可信息
        List<Integer> permissionids = permissionService.queryPermissionidsByRoleid(roleid);

        Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
        for (Permission p : ps) {
            if ( permissionids.contains(p.getId()) ) {
                p.setChecked(true);
            } else {
                p.setChecked(false);
            }
            permissionMap.put(p.getId(), p);
        }
        for ( Permission p : ps ) {
            Permission child = p;
            if ( child.getPid() == 0 ) {
                permissions.add(p);
            } else {
                Permission parent = permissionMap.get(child.getPid());
                parent.getChildren().add(child);
            }
        }

        return permissions;
    }
}
