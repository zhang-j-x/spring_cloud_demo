package com.jx.user.security.config;



import com.jx.common.vo.common.Rs;
import com.jx.user.enums.LoginRcEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhangjx
 * @Date: 2020/4/10 23:10
 * @Description: 提供统一返回登录页面或者信息
 */
@RestController
@Slf4j
@RequestMapping("/authentication/")
/**跨域注解*/
@CrossOrigin
public class BrowserSecurityController {

    @GetMapping("require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Rs requireAuthentication(){
        return Rs.fail(LoginRcEnum.NOT_AUTHENTICATION);
    }
}
