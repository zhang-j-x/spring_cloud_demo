package com.jx.user.security.config;

import com.jx.common.vo.common.Rs;
import com.jx.user.util.ResponseWriterUtil;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-03-28
 **/
@Component
public class MyInvalidSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //将认证失败信息返回前端
        ResponseWriterUtil.writeResponse(response, Rs.fail("登录已过期！"));
    }
}
