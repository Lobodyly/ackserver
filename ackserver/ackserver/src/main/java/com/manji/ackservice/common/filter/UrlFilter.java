package com.manji.ackservice.common.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(filterName="urlFilter",urlPatterns={"/api/*","/apiapp/*","/app/knowledge/*","/pc/knowledge/*"})



public class UrlFilter implements Filter {

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse hResponse=(HttpServletResponse)response;
//      HttpServletRequest hRequest=(HttpServletRequest)request;

        hResponse.setHeader("Access-Control-Allow-Origin","*");
        hResponse.setHeader("Access-Control-Allow-Methods", "*");
        hResponse.setHeader("Access-Control-Max-Age", "7200"); //设置过期时间
        hResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
        hResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1.
        hResponse.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0")

        chain.doFilter(request, response);


    }

    @Override
    public void init(FilterConfig config) throws ServletException{
        System.out.println("过滤器初始化");
    }


//    /**
//     * 处理拦截往header加用户ID--token信息中获取
//     * @param req
//     * @return
//     */
//    public HttpServletRequest doRequestAddHeader(HttpServletRequest req){
//
//        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);
//        mutableRequest.addHeader("files", "111");
//        return mutableRequest;
//    }


}
