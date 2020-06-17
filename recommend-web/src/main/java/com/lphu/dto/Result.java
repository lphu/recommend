package com.lphu.dto;

import lombok.Data;

/**
 * @author: lphu
 * @create: 2019-01-09 15:31
 * @description:
 */
@Data
public class Result {

    public Integer code;
    public String message;
    public Object data;

}
