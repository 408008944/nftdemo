package com.example.nftdemo.config;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Credentials", "true");

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String origin1 = httpServletRequest.getHeader("origin");
        /*System.out.println("1111111111" + origin1);*/

        if (StringUtils.isEmpty(origin1) || "null".equals(origin1)) {
            res.addHeader("Access-Control-Allow-Origin", "*");
        }else {
            res.addHeader("Access-Control-Allow-Origin", origin1);
        }
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            response.getWriter().println("ok");
            return;
        }
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
