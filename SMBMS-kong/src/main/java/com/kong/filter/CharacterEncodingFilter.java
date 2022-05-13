package com.kong.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author kongkong
 * @date 20/11/30 9:27
 * @description:过滤器
 * @since 1.8
 */
public class CharacterEncodingFilter implements Filter {

    //初始化：web服务器启动，就初始化了，随时等待过滤对象出现！
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //FilterChain：链
        /*过滤中的所有代码，在过滤特定请求的时候会执行
         * 必须让过滤器继续运行，因为可能还有其他过滤器*/
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=UTF-8");

        //filterChain是让我们的请求继续执行下去，如果不写，程序到这里就会被停止
        //匹配过滤器后每次请求都会执行一次
        filterChain.doFilter(servletRequest, servletResponse);
    }

    //web服务器关闭的时候会被销毁
    @Override
    public void destroy() {
    }
}
