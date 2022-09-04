package com.kenton.elemei.filter;

import com.alibaba.fastjson.JSON;
import com.kenton.elemei.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Kenton
 * @description 检查用户是否已经完成登录
 * @date: 2022/9/3 11:54
 */
@Slf4j
// 指定过滤器名称
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    // 工具类 路径匹配器 支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        /**
         * URL（统一资源定位符）是Internet上资源的地址，可以定义为引用地址的字符串，用于指示资源的位置以及用于访问它的协议。
         * URL是在网络上定位资源的最普遍使用的方式，它提供了一种通过描述其网络位置或主要访问机制来检索物理位置的表示的方法。
         * URL中描述了协议，该URL用于检索资源和资源名称。如果资源是Web类型资源，则URL在开头包含http / https。
         * 同样，如果资源是文件，则以ftp开头，如果资源是电子邮件地址，则以mailto开头。
         *
         * URI（统一资源标识符）是标识逻辑或物理资源的字符序列，与URL类似，也是一串字符。通过使用位置，名称或两者来标识Internet上的资源；它允许统一识别资源。
         * 有两种类型的URI，统一资源标识符（URL）和统一资源名称（URN）
         */
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1.获取本次请求的URI
        String requestURI = request.getRequestURI();

        log.info("拦截到请求:{}",requestURI);

        // 定义不需要处理的请求路径
        String[] urls = new String[]{
                // 登录路径 不需要检查
                "/employee/login",
                // 退出路径 不需要检查
                "/employee/logout",
                // 展示的静态图片 不需要检查
                "/backend/**",
                "/front/**",
                "/common/**"
        };

        // 2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        // 3.如果不需要处理 则直接放行
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            // 放行
            filterChain.doFilter(request,response);
            return;
        }

        // 4.判断登录状态 如果已登录 则直接放行
        if (request.getSession().getAttribute("employee") != null){
            log.info("用户已登录,用户ID为:{}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        // 5.如果未登录则返回未登录结果 通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
