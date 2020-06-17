package com.lphu.utils;


import com.lphu.dto.Result;

/**
 * @author: lphu
 * @create: 2019-01-09 15:39
 * @description:
 */

public class Results {

    public static Result success(Object data){
        Result result = new Result();
        result.setCode(0);
        result.setData(data);
        return result;
    }

    public static Result error(int code, String message){ return error(code, message, null);}

    public static Result error(int code, String message, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
