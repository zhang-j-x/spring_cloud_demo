package com.jx.user.security.filter.validatecode;


import com.alibaba.nacos.common.utils.ConcurrentHashSet;
import com.jx.user.security.properties.SecurityProperties;
import com.jx.user.security.config.MyAuthenticationFailureHandler;
import com.jx.user.security.validate.code.constant.ValidateCodeConstant;
import com.jx.user.security.validate.code.dto.ResultDto;
import com.jx.user.security.validate.code.exception.ValidateCodeException;
import com.jx.user.security.validate.code.generator.SmsCodeGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @Author: zhangjx
 * @Date: 2020/5/17 22:39
 * @Description:
 */
@Component("smsValidateCodeFilter")
public class SmsValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private SmsCodeGenerator smsCodeGenerator;
    @Autowired
    private SecurityProperties securityProperties;
    /**路径判断工具类*/
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    /**需要图形验证码拦截的url*/
    private Set<String> urls = new ConcurrentHashSet<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.split(securityProperties.getValidateCode().getSmsCode().getUrls(), ",");
        for (String configUrl: configUrls) {
            urls.add(configUrl);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //判断是否是登录请求
        String requestURI = request.getRequestURI();
        Boolean needValidate = false;
        for (String url: urls) {
            if(pathMatcher.match(url, requestURI)){
                needValidate = true;
                break;
            }
        }
        if (needValidate){
            try{
                validateSmsCode(new ServletWebRequest(request));
            }catch (ValidateCodeException e){
                myAuthenticationFailureHandler.onAuthenticationFailure(request,response ,e );
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateSmsCode(ServletWebRequest servletWebRequest) {
        String uuid = servletWebRequest.getParameter(securityProperties.getValidateCode().getSmsCode().getUuidParamName());
        String randomCode = servletWebRequest.getParameter(securityProperties.getValidateCode().getSmsCode().getValidateCodeParamName());
        if(StringUtils.isBlank(randomCode)){
            throw  new ValidateCodeException(ValidateCodeConstant.VALIDATE_CODE_MUST_NOT_NULL);
        }
        ResultDto resultDto = smsCodeGenerator.judgeValidateCodeRight(uuid, randomCode);
        if(!resultDto.getSuccess()){
            throw  new ValidateCodeException(resultDto.getMsg());
        }
    }

}
