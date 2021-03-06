package com.zhku.mh.rbac.controller;

import com.zhku.mh.rbac.bean.AJAXResult;
import com.zhku.mh.rbac.bean.Page;
import com.zhku.mh.rbac.bean.Role;
import com.zhku.mh.rbac.bean.User;
import com.zhku.mh.rbac.service.RoleService;
import com.zhku.mh.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName：
 * Time：2019/12/5 14:53
 * Description：
 * Author： mh
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String index() {
        return "user/index";
    }

    @RequestMapping("/pageQuery")
    @ResponseBody
    public AJAXResult pageQuery(String queryText, Integer pageno, Integer pagesize) {
        AJAXResult result = new AJAXResult();

        try {
            // 分页查询
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start", (pageno - 1) * pagesize);
            map.put("size", pagesize);
            map.put("queryText", queryText);

            List<User> users = userService.pageQueryData(map);
            // 当前页码
            // 总的数据条数
            int totalsize = userService.pageQueryCount(map);
            // 最大页码（总页码）
            int totalno = 0;
            if (totalsize % pagesize == 0) {
                totalno = totalsize / pagesize;
            } else {
                totalno = totalsize / pagesize + 1;
            }

            // 分页对象
            Page<User> userPage = new Page<User>();
            userPage.setDatas(users);
            userPage.setTotalno(totalno);
            userPage.setTotalsize(totalsize);
            userPage.setPageno(pageno);

            result.setData(userPage);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @RequestMapping("/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public AJAXResult insert(User user) {
        AJAXResult result = new AJAXResult();

        try {
            user.setCreatetime(new Date());
            user.setUserpswd(user.getLoginacct());
            userService.insertUser(user);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update(User user) {
        AJAXResult result = new AJAXResult();

        try {
            user.setUpdatetime(new Date());
            userService.updateUser(user);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @RequestMapping("/edit")
    public String edit(Integer id, Model model) {

        User user = userService.queryById(id);
        model.addAttribute("user", user);

        return "user/edit";
    }

    @ResponseBody
    @RequestMapping("/deletes")
    public Object deletes(Integer[] userid) {
        AJAXResult result = new AJAXResult();

        try {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userids", userid);
            userService.deleteUsers(map);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Integer id) {
        AJAXResult result = new AJAXResult();

        try {

            userService.deleteUserById(id);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @RequestMapping("/assign")
    public String assign(Integer id, Model model) {

        User user = userService.queryById(id);
        model.addAttribute("user", user);

        List<Role> roles = roleService.queryAll();
        model.addAttribute("roles", roles);

        List<Integer> roleIds = userService.queryRoleidsByUserid(id);

        List<Role> assignRoles = roles.parallelStream()
                                .filter(item -> roleIds.contains(item.getId()))
                                .collect(Collectors.toList());

        List<Role> unAssignRoles =  roles.parallelStream()
                                    .filter(item -> (!roleIds.contains(item.getId())))
                                    .collect(Collectors.toList());

        model.addAttribute("assingedRoles", assignRoles);
        model.addAttribute("unassignRoles", unAssignRoles);


        return "user/assign";
    }

    @RequestMapping("/doAssign")
    @ResponseBody
    public AJAXResult doAssign(Integer userid, Integer[] unassignroleids) {
        AJAXResult result = new AJAXResult();

        try {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("userid",userid);
            map.put("roleids",unassignroleids);
            userService.insertUserRoles(map);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @RequestMapping("/doUnAssign")
    @ResponseBody
    public AJAXResult doUnAssign(Integer userid, Integer[] assignroleids) {
        AJAXResult result = new AJAXResult();

        try {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("userid",userid);
            map.put("roleids",assignroleids);
            userService.deleteUserRoles(map);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }
}
