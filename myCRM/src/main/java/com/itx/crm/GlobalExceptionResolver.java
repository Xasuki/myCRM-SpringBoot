package com.itx.crm;

import com.alibaba.fastjson.JSON;
import com.itx.crm.base.ResultInfo;
import com.itx.crm.exceptions.NoLoginException;
import com.itx.crm.exceptions.ParamsException;
import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/10 16:21
 */
//@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    /**
     * 全局异常的处理
     * 两种异常  1数据 方法上有@response注解    2视图
     *
     * @param request
     * @param response
     * @param handler
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        /**
         * 判断是不是未登录异常
         */
        if (e instanceof NoLoginException) {
            return new ModelAndView("redirect:/index");
        }

        //设置默认异常
        modelAndView.setViewName("");
        modelAndView.addObject("code", 500);
        modelAndView.addObject("msg", "系统异常，请重新尝试...");

        //判断 HandlerMethod
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if (null == responseBody) {
                //方法返回视图
                if (e instanceof ParamsException) {
                    ParamsException paramsException = (ParamsException) e;
                    modelAndView.addObject("code", paramsException.getCode());
                    modelAndView.addObject("msg", paramsException.getMsg());
                }
                return modelAndView;
            } else {
                //方法上返回JSON
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统异常，请重新尝试...");
                //如果捕捉到自定义异常
                if (e instanceof ParamsException) {
                    ParamsException paramsException = (ParamsException) e;
                    resultInfo.setCode(paramsException.getCode());
                    resultInfo.setMsg(paramsException.getMsg());
                }
                //设置JSON响应数据
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = null;
                try {
                    writer = response.getWriter();
                    writer.write(JSON.toJSONString(resultInfo));
                    writer.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
            }
        }

        return modelAndView;
    }
}
