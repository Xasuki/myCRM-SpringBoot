package com.itx.crm.interceptors;

import com.itx.crm.exceptions.NoLoginException;
import com.itx.crm.service.UserService;
import com.itx.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/10 17:09
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;

    /**
     * 判断用户是否是已登录状态
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie中用户的id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        if(null==userId||null==userService.selectByPrimaryKey(userId)){
            throw new NoLoginException();
        }
        return true;
    }
}
