package com.test.common.sysLog;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * auth: shi yi
 * create date: 2018/11/6
 */
@Data
public class SysLog implements Serializable {

    private static final long serialVersionUID = 5598997495284014405L;
    private Long id;

    private String username; //用户名

    private String operation; //操作

    private String method; //方法名

    private String params; //参数

    private String ip; //ip地址

    private Date createDate; //操作时间
}
