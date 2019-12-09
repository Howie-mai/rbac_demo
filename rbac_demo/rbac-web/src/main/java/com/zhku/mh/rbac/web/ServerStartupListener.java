package com.zhku.mh.rbac.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ClassName：
 * Time：2019/12/5 15:08
 * Description：
 * Author： mh
 */
public class ServerStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /**
         * 讲web应用名称保存到application范围中
         */
        ServletContext context = sce.getServletContext();
        String path = context.getContextPath();
        context.setAttribute("APP_PATH",path);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
