package com.jx.user.security.config;


import com.jx.common.vo.common.Rs;
import com.jx.user.entity.User;
import com.jx.user.util.ResponseWriterUtil;
import com.jx.user.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zhangjx
 * @Date: 2020/4/11 00:11
 * @Description: 登录成功的handler
 */
@Slf4j(topic = "login_log")
@Component
public class MyAuthenticationSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        //将认证成功信息返回前端
        User principal = (User) authentication.getPrincipal();

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(principal, userVo);
        ResponseWriterUtil.writeResponse(httpServletResponse, Rs.success(userVo));
    }
}
