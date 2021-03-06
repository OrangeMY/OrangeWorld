package com.imooc.sm.global;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispartcherServlet extends GenericServlet {

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /**
         *    接收的请求可能为以下两种：
         *    /staff/list.do     /login.do
         *    然后再分别对这两种请求进行处理，将不同的请求给相应的Controller去处理
         */
        String path = request.getServletPath().substring(1);
        String beanName = null;
        String methodName = null;
        int index = path.indexOf('/');
        if(index != -1){
            beanName = path.substring(0, index) + "Controller";
            methodName = path.substring(index + 1, path.indexOf(".do"));
        }else{
            beanName = "selfController";
            methodName = path.substring(0, path.indexOf(".do"));
        }
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        Object obj = applicationContext.getBean(beanName);
        try {
            Method method = obj.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            try {
                method.invoke(obj, request, response);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
