package com.zhku.mh.rbac.controller;

import com.zhku.mh.rbac.bean.AJAXResult;
import com.zhku.mh.rbac.bean.Permission;
import com.zhku.mh.rbac.bean.User;
import com.zhku.mh.rbac.service.PermissionService;
import com.zhku.mh.rbac.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * ClassName：DispatcherController
 * Time：2019/12/4 16:15
 * Description：跳转页面的控制类
 * Author： mh
 */
@Controller
public class DispatcherController {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
//        session.removeAttribute("loginUser");
        session.invalidate(); //session失效
        return "redirect:/login";
    }

    @RequestMapping("/error")
    public String error() {
        return "error";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @ResponseBody
    @RequestMapping("/doAJAXLogin")
    public AJAXResult doAJAXLogin(User user, HttpSession session) {
        AJAXResult result = new AJAXResult();

        User dbUser = userService.query4Login(user);

        if (dbUser != null) {
            session.setAttribute("loginUser", dbUser);

            List<Permission> permissions = permissionService.queryPermissionidsByUser(dbUser);
            // 获取用户权限信息
            Set<String> uriSet = new HashSet<>();
            Map<Integer, Permission> permissionMap = new HashMap<>();
            Permission root = null;
            for (Permission permission : permissions) {
                permissionMap.put(permission.getId(), permission);
                if(permission.getUrl() != null && StringUtils.isNotBlank(permission.getUrl())){
                    uriSet.add(session.getServletContext().getContextPath() + permission.getUrl());
                }
            }
            session.setAttribute("authUriSet",uriSet);
            for ( Permission permission : permissions ) {
                Permission child = permission;
                if ( child.getPid() == 0 ) {
                    root = permission;
                } else {
                    Permission parent = permissionMap.get(child.getPid());
                    parent.getChildren().add(child);
                }
            }

            session.setAttribute("rootPermission",root);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }

        return result;
    }
}
