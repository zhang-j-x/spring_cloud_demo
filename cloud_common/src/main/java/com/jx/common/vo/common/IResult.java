package com.jx.product.vo.common;

import java.io.Serializable;

/**
 * 业务代码接口
 */
public interface IResult extends Serializable {

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return int
     */
    int getCode();

}
