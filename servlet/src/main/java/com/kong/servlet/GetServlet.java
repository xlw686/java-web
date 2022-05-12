package com.kong.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        ServletContext context = this.getServletContext();
        String username = (String) context.getAttribute("username");


        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        writer.println("<h1>名字"+username+"<h1>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入了dopost方法");
        super.doGet(req, resp);
    }
}
