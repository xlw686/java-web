package com.kong.servlet.user;

import com.kong.pojo.User;
import com.kong.service.user.UserServiceImpl;
import com.kong.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ThreePure
 * @date 20/12/1 20:29
 * @description:  Servlet控制层，调用业务层代码
 * @since 1.8
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("LoginServlet---start");

        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        System.out.println("LoginServlet:"+userCode);
        System.out.println("LoginServlet:"+userPassword);

        //和数据库中的密码进行比较，调用业务层
        UserServiceImpl userService = new UserServiceImpl();
        //这里已经把登录的人查出来了
        User user = userService.login(userCode, userPassword);


        //查到了，则进行登录，将用户信息放到Session中
        if (user!=null){
            req.getSession().setAttribute(Constants.USER_SESSION, user);
            //跳转主页
            resp.sendRedirect("jsp/frame.jsp");
        }else {
            //查无此人，无法登录，转发回登录页面，提示用户名或者密码错误
            req.setAttribute("error", "用户名或者密码错误");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
