package com.lphu.controller;

import com.lphu.dto.Result;
import com.lphu.model.request.UserRequest;
import com.lphu.service.UserService;
import com.lphu.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hupeilei
 * @create 2020/3/10 2:40 下午
 */
@Api("用户接口")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody UserRequest request) {
        return Results.success(userService.login(request));
    }

    @ApiOperation("注册接口")
    @PostMapping("/register")
    public Result register(@RequestBody UserRequest request) {
        return Results.success(userService.registerUser(request));
    }

    @ApiOperation("个人评分情况接口:TODO")
    @GetMapping("/rate")
    public Result getMyRating(@RequestParam("id") Integer id) {
        return Results.success(null);
    }

}
