package com.qfggk.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 * @author: WJQ
 * @date 2021-06-13 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code;
    private String message;
    private Object obj;

    /**
     * 成功返回结果
     * @param message
     * @param obj
     * @return
     */
    public static RespBean success(String message,Object obj)
    {
        return new RespBean(200L,message,obj);

    }

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(String message)
    {
        return new RespBean(200L,message,null);

    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static RespBean error(String message)
    {
        return new RespBean(500L,message,null);
    }

    /**
     * 失败返回结果
     * @param message
     * @param obj
     * @return
     */
    public static RespBean error(String message,Object obj)
    {
        return new RespBean(500L,message,obj);
    }
}
