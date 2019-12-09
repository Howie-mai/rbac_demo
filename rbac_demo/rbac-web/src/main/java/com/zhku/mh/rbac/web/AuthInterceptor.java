package com.zhku.mh.rbac.web;

import com.zhku.mh.rbac.bean.Permission;
import com.zhku.mh.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ClassName：
 * Time：2019/12/9 16:30
 * Description：
 * Author： mh
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取用户的请求地址
        String uri = request.getRequestURI();
        String path = request.getSession().getServletContext().getContextPath();

        // 判断当前路径是否需要进行权限验证。
        // 查询所有需要验证的路径集合
        List<Permission> permissions = permissionService.queryAll();
        Set<String> uriSet = new HashSet<String>();
        for ( Permission permission : permissions ) {
            if ( permission.getUrl() != null && !"".equals(permission.getUrl()) ) {
                uriSet.add(path + permission.getUrl());
            }
        }

        if ( uriSet.contains(uri) ) {
            // 需要权限验证
            // 判断当前用户是否拥有对应的权限
            Set<String> authUriSet = (Set<String>)request.getSession().getAttribute("authUriSet");
            if ( authUriSet.contains(uri) ) {
                return true;
            } else {
                response.sendRedirect(path + "/error");
                return false;
            }
        } else {
            //不需要直接放行
            return true;
        }
    }
}