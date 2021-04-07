package com.jx.user.security.filter.usernamepwd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jx.user.security.properties.SecurityProperties;
import com.jx.user.util.BufferedServletRequestWrapper;
import com.jx.user.util.RsaEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: zhangjx
 * @Date: 2020/6/21 16:39
 * @Description:
 */
@Slf4j
public class MyUserNamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private RsaEncryptUtil rsaEncryptUtil;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        String username = null;
        String password = null;
        // 暂时只有支持 post json 传参实现,
        if(RequestMethod.POST.toString().equals(request.getMethod())
                &&request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)){
            try {
                HttpServletRequest httpRequest = new BufferedServletRequestWrapper(request);
                Map<String,String> map = new ObjectMapper().readValue(httpRequest.getInputStream(),Map.class );
                username = map.get(securityProperties.getBrowser().getLoginUserNameParamName());
                String rsaPassword = map.get(securityProperties.getBrowser().getLoginUserPasswordParamName());
                password = rsaEncryptUtil.decrypt(rsaPassword,rsaEncryptUtil.getPrivateKey());
            } catch (IOException e) {
                log.error("登录操作 获取post参数异常！", e);
                throw new RuntimeException("登录操作 获取post参数异常！" + e.getMessage());
            }catch (Exception e) {
                log.error("登录操作 密码解密异常！", e);
                throw new BadCredentialsException("密码不符合加密规则！请在指定登陆页面登陆。");
            }
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);
            setDetails(request, authRequest);

            return super.getAuthenticationManager().authenticate(authRequest);
        }else {
            throw new AuthenticationServiceException(
                    "Authentication method " + request.getMethod() + " and Authentication contentType " + request.getContentType() + " not supported. " +
                            "only supported Authentication method:" + RequestMethod.POST + " and Authentication contentType:" +
                            MediaType.APPLICATION_JSON_VALUE);
        }
    }


}
