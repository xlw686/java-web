package com.kong.filter;

import javax.servlet.*;
import java.io.IOException;

public class FilterTest implements Filter {
    //初始化
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FilterTest已经初始化了");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=UTF-8");

        System.out.println("FilterTest执行前、、、、、、、、");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("FilterTest执行后、、、、、、、、");
    }


    //销毁
    @Override
    public void destroy() {
        System.out.println("FilterTest已经销毁了");
    }
}
