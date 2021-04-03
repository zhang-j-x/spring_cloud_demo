package com.jx.user.security.config;



import com.jx.common.vo.common.Rs;
import com.jx.user.util.ResponseWriterUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhangjx
 * @Date: 2020/4/11 00:12
 * @Description: 登录失败的handler
 */
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {

            //将认证失败信息返回前端
            ResponseWriterUtil.writeResponse(httpServletResponse, Rs.fail(e.getMessage()));
    }
}
