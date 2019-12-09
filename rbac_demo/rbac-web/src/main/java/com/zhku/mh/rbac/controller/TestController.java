package com.zhku.mh.rbac.controller;

import com.zhku.mh.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName：
 * Time：2019/12/4 15:44
 * Description：
 * Author： mh
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public Object queryAll(){
        return userService.queryAll();
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/json")
    @ResponseBody
    public Object json(){
        Map map = new HashMap<>();
        map.put("username","mh");
        return map;
    }
}
