package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    //通过AOP面向切面实现

//    该方法将在请求处理之前被调用。SpringMVC中的Interceptor是链式的调用，
//    在一个应用或一个请求中可以同时存在多个Interceptor。每个Interceptor的
//    调用会依据它的声明顺序依次执行，而且最先执行的都是Interceptor中的preHandle()方法，
//    所以可以在这个方法中进行一些前置初始化操作或者是对当前请求的一个预处理，也可以在这个
//    方法中进行一些判断来决定请求是否要继续进行下去。该方法的返回值是布尔值类型，当返回false时，
//    表示请求结束，后续的Interceptor和Controller都不会再执行；当返回值true时，就会继续调用
//    下一个Interceptor的preHandle方法，如果已经是最后一个Interceptor的时，就会调用当前请求的Controller方法。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("uid")==null){
            response.sendRedirect("/web/login.html");
            return false;
        }else {
            return true;
        }
    }
//    该方法将在当前请求进行处理之后，也就是Controller方法调用之后执行，但是它会
//    在DispatcherServlet进行视图返回渲染之前被调用，所以我们可以在这个方法中对
//    Controller处理之后的ModelAndView对象进行操作。postHandle方法被调用的方向跟
//    preHandle是相反的，也就是说先声明的Interceptor的postHandle方法反而会后执行。
//    如果当前Interceptor的preHandle()方法返回值为false，则此方法不会被调用。
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }
//    该方法将在整个当前请求结束之后，也就是在DispatcherServlet渲染了对应的
//    视图之后执行。这个方法的主要作用是用于进行资源清理工作。如果当前Interceptor的preHandle()
//    方法返回值为false，则此方法不会被调用。
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
}
