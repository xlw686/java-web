package com.kong.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入了doget方法");
//        ServletInputStream inputStream = req.getInputStream();
//        ServletOutputStream outputStream = resp.getOutputStream();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        PrintWriter writer = resp.getWriter();
        writer.println("<h1>hello,空唤晴<h1>");

        String username="空唤晴";
        ServletContext context = this.getServletContext();
        context.setAttribute("username",username);//从别的servlet可以获取该内容（ServletContext）


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入了dopost方法");
        super.doGet(req, resp);
    }
}
