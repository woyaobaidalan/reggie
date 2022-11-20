//package com.controller.interceptor;
//
//import com.alibaba.fastjson.JSON;
//import com.common.R;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//import java.nio.charset.StandardCharsets;
//
//@Component
//@Slf4j
//public class LoginCheckInterceptor implements HandlerInterceptor {
//
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        String requestURI = request.getRequestURI();
//        //不需要拦截的路径
//        log.info("requestURI的值是：{}", requestURI);
//        String[] url = new String[]{
//                "/employee/login",
//                "/employee/logout",
//                "/backend/**",
//                "/front/**"
//        };
//
//        //判断本次请求是否需要处理
//        Boolean check = check(url, requestURI);
//        if(check){
//            log.info("本次请求{}不需要处理", requestURI);
//            return true;
//        }
//
//        //判断登录状态，如果已登录，直接放行
//        if(request.getSession().getAttribute("employee") != null){
//            log.info("用户已登录");
//            return true;
//        }
//
//        log.info("用户未登录");
//        //如果未登录则返回未登录结果，通过输出流的方式向客户端页面响应数据
////        response.getOutputStream().write(JSON.toJSONString(R.error("NOTLOGIN")));
////        response.getOutputStream().write(Integer.parseInt(JSON.toJSONString(R.error("NOTLOGIN"))));
////        response.getOutputStream().write(JSON.toJSONString(R.error("NOTLOGIN")).getBytes(StandardCharsets.UTF_8));
//
//        log.info("拦截器生效");
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    }
//
//    public boolean check(String[] urls, String requestURI){
//
//        for(String url : urls){
//            boolean match = PATH_MATCHER.match(url, requestURI);
//            if(match){
//                return true;
//            }
//        }
//        return false;
//    }
//}
