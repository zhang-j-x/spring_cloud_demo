package com.jx.user.util;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zhangjx
 * @Date: 2020/4/11 00:15
 * @Description: 输出流
 */
public class ResponseWriterUtil {

    /**
     *
     * @param response
     * @param data 回写数据
     * @return: void
     */
    public static void writeResponse(HttpServletResponse response, Object data){
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().write(JSON.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param response
     * @param data 回写数据
     * @param httpStatus 服务状态码
     * @return: void
     */
    public static void writeResponse(HttpServletResponse response, Object data, HttpStatus httpStatus){
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.setStatus(httpStatus.value());
            response.getWriter().write(JSON.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
