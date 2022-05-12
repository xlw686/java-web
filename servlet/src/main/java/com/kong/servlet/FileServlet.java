package com.kong.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取下载路径
        String realPath = this.getServletContext().getRealPath("/WEB-INF/img.png");
        //下载的文件名
        String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
        //设置浏览器下载所需
        resp.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(fileName,"UTF-8"));
        //获取下载文件的输入流
        FileInputStream in = new FileInputStream(realPath);
        //创建缓冲区
        int len=0;
        byte[] buffer = new byte[1024];
        //获取outputStream对象
        ServletOutputStream out = resp.getOutputStream();
        //将FileInputStream流写入到buffer缓冲区,使用OutputStream将缓冲区数据输出到客户端
        while ((len=in.read(buffer))!=-1){
            out.write(buffer,0,len);
        }

        in.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
