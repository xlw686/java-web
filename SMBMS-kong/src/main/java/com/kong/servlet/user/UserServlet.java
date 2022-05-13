package com.kong.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.kong.pojo.Role;
import com.kong.pojo.User;
import com.kong.service.role.RoleServiceImpl;
import com.kong.service.user.UserService;
import com.kong.service.user.UserServiceImpl;
import com.kong.util.Constants;
import com.kong.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kongkong
 * @date 22/5/10 8:49
 * @description:
 * @since 1.8
 * 实现Servlet复用,提取出方法，然后再通过路径匹配
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("savepwd".equals(method) && method != null) {
            this.updatePwd(req, resp);
        } else if ("pwdmodify".equals(method) && method != null) {
            this.pwdmodify(req, resp);
        } else if ("query".equals(method) && method != null) {
            this.userQuery(req, resp);
        } else if ("add".equals(method) && method != null) {
            this.add(req, resp);
        } else if ("getrolelist".equals(method) && method != null) {
            this.getRoleList(req, resp);
        } else if ("ucexist".equals(method) && method != null) {
            this.userCodeExist(req, resp);
        } else if ("view".equals(method) && method != null) {
            this.getUserById(req, resp, "userview.jsp");
        } else if ("modify".equals(method) && method != null) {
            this.getUserById(req, resp, "usermodify.jsp");
        } else if ("modifyexe".equals(method) && method != null) {
            this.userModify(req, resp);
        } else if ("deluser".equals(method) && method != null) {
            this.deleteUser(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * @description: 修改密码
     * @date 22/5/10 9:55
     * @Param: [req, resp]
     * @Return: void
     */
    private void updatePwd(HttpServletRequest req, HttpServletResponse resp) {
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");
        boolean flog = false;

        if (o != null && !StringUtils.isNullOrEmpty(newpassword)) {
            UserService userService = new UserServiceImpl();
            flog = userService.updatePwd(((User) o).getId(), newpassword);
            if (flog) {
                //修改密码成功，移除Session
                req.setAttribute("message", "修改密码成功，请用新密码重新登录");
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                req.setAttribute("message", "修改密码失败");
            }
        } else {
            req.setAttribute("message", "新密码有问题");
        }

        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 验证旧密码, Session中有用户的密码
     * @date 22/5/10 10:57
     * @Param: [req, resp]
     * @Return: void
     */
    private void pwdmodify(HttpServletRequest req, HttpServletResponse resp) {
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");//用户输入的旧密码

        //万能的Map：结果集
        Map<String, String> resultMap = new HashMap<String, String>();

        //进行判断
        if (o == null) {
            //1.Session失效了或者Session过期了
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) {
            //输入为空
            resultMap.put("result", "error");
        } else {
            //获取Session中的用户密码
            String userPassword = ((User) o).getUserPassword();
            //判断输入的旧密码是否与当前Session中的密码一致
            if (oldpassword.equals(userPassword)) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }

        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JSONArray:阿里巴巴的JSON工具类，转换格式
            /* resultMap=["result", "true","result", "false"]
               Json格式={key:value}
            * */
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @description: 对用户进行查询【重点和难点】
     * @date 22/5/10 19:42
     * @Param: [req, resp]
     * @Return: void
     * 1.获取用户前端的数据（查询）
     * 2.判断请求是否需要执行，看参数的值判断
     * 3.为了实现分页，需要计算出当前页面和总页面，页面大小…
     * 4.用户列表展示
     * 5.返回前端
     */
    private void userQuery(HttpServletRequest req, HttpServletResponse resp) {
        //查询用户列表
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;

        //获取用户列表
        UserServiceImpl userService = new UserServiceImpl();

        //第一次走这个请求，一定是第一页，页面大小固定的
        //可用把pageSize写道配置文件，方便后期修改
        int pageSize = 5;
        int currentPageNo = 1;

        if (queryUserName == null) {
            queryUserName = "";
        }
        if (temp != null && !temp.equals("")) {
            //给查询赋值 0，1，2，3，
            queryUserRole = Integer.parseInt(temp);
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户数量
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        List<User> userList = null;

        //总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        //最大页数，总共有几页【如果totalCount刚好是pageSize的整数倍，那么是不是多算了一页】
        int totalPageCount = ((int) (totalCount / pageSize)) + 1;

        //控制首页和尾页
        if (currentPageNo < 1) {
            //如果页面小于第一页，就显示第一页
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            //如果页面大于最后一页，就显示最后一页
            currentPageNo = totalPageCount;
        }

        //获取用户列表展示
        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList", userList);

        //获取角色列表
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();

        req.setAttribute("roleList", roleList);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("queryUserName", queryUserName);
        req.setAttribute("queryUserRole", queryUserRole);

        //返回前端
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 添加用户
     * @date 20/12/7 18:25
     * @Param: [req, resp]
     * @Return: void
     */
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //获取输入的用户参数
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");

        //创建一个User对象，并且根据表单获取的内容设置其属性值
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setAddress(address);
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new Date());
        user.setCreatedBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());

        //调用Service层的方法
        UserService userService = new UserServiceImpl();
        if (userService.add(user)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        } else {
            req.getRequestDispatcher("useradd.jsp").forward(req, resp);
        }
    }

    /**
     * @description: 返回用户角色列表
     * @date 20/12/7 20:04
     * @Param: [req, resp]
     * @Return: void
     */
    private void getRoleList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Role> roleList = null;
        RoleServiceImpl roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();
        //把roleList转换成json对象返回
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(roleList));
        writer.flush();
        writer.close();
    }

    /**
     * @description: 判断添加用户的userCode是否可用（是否已经被占用了）
     * @date 20/12/7 20:10
     * @Param: [req, resp]
     * @Return: void
     */
    private void userCodeExist(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //首先我们判断数据库中是否存在该用户usercode，首先得获取到用户输入的userCode
        String userCode = req.getParameter("userCode");
        //万能的Map：结果集
        Map<String, String> map = new HashMap<String, String>();
        //判断输入是否为空
        if (StringUtils.isNullOrEmpty(userCode)) {
            map.put("userCode", "empty");
        } else {
            UserServiceImpl userService = new UserServiceImpl();
            User user = userService.selectUserCodeExist(userCode);
            if (user != null) {
                map.put("userCode", "exist");
            } else {
                map.put("userCode", "notexist");
            }
        }
        //将Map数据转换成json返回
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(map));
        writer.flush();
        writer.close();
    }

    /**
     * @description: 通过Id获取用户，查看具体的某一个用户
     * @date 20/12/7 20:56
     * @Param: [request, response, url]
     * @Return: void
     */
    private void getUserById(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException, IOException {
        //获取传入的id
        String uid = req.getParameter("uid");
        if (!StringUtils.isNullOrEmpty(uid)) {
            UserServiceImpl userService = new UserServiceImpl();
            User userById = userService.getUserById(uid);
            req.setAttribute("user", userById);
            req.getRequestDispatcher(url).forward(req, resp);
        }
    }

    /**
     * @description: 对用户进行修改
     * @date 20/12/7 21:22
     * @Param: [req, resp]
     * @Return: void
     */
    private void userModify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //接收更改内容
        String id = req.getParameter("uid");
        String userName = req.getParameter("userName");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");

        //创建一个User对象，并且根据表单获取的内容设置其属性值
        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setUserName(userName);
        user.setAddress(address);
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setModifyBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());

        UserService userService = new UserServiceImpl();
        boolean modify = userService.userModify(user);
        if (modify) {
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        } else {
            req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
        }
    }

    /**
     * @description: 删除用户
     * @date 20/12/13 10:12
     * @Param: [req, resp]
     * @Return: void
     */
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //接收id
        String id = req.getParameter("uid");
        Integer deleteId = 0;

        try {
            deleteId = Integer.parseInt(id);
        } catch (Exception e) {
            deleteId = 0;
        }

        //获取Session中的登录id。
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        //获取Session中的用户id
        Integer sessionId = ((User) o).getId();
        System.out.println("接收到的目标id："+deleteId);
        System.out.println("当前登录用户id："+sessionId);

        HashMap<String, String> map = new HashMap<String, String>();
        if (deleteId <= 0) {
            map.put("delResult", "notexist");
        } else if (sessionId.equals(deleteId)) {
            //判断要删除的id是否等于当前登录用户的id。
            map.put("delResult", "cannotdel");
        } else {
            UserServiceImpl userService = new UserServiceImpl();
            boolean b = false;
            try {
                b = userService.deleteUserById(deleteId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (b) {
                map.put("delResult", "true");
            } else {
                map.put("delResult", "false");
            }
        }
        //把map转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(map));
        writer.flush();
        writer.close();
    }

}
