package com.lphu.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author: lphu
 * @create: 2019-01-09 19:42
 * @description:
 */
@AllArgsConstructor
@Getter
@ToString
public enum ErrorCode {

    PARAM_ERROR(1000,"参数错误"),
    USERNAME_NOT_EXIST(1001, "用户名不存在"),
    USERNAME_EXIST(1002,"用户名重复"),
    PASSWORD_WRONG(1003, "密码错误"),
    REPEAT_RATE(1004, "重复评分");

    private int code;
    private String message;
}
