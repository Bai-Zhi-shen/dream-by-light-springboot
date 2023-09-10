package com.bai.controller;

import com.bai.entity.User;
import com.bai.service.UserService;
import com.bai.utils.Result;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname LoginCotroller
 * @Description 登录注册控制器
 * @Version 1.0.0
 * @Date 2023/9/10 0:57
 * @Created by Bai
 */
@Slf4j
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginCotroller {
    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param user 用户实体类
     * @return Result
     */
    @PostMapping
    public Result login(@Valid @RequestBody User user) {
        String userId = userService.login(user);
        if (userId != null) {
            return Result.success(userId);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    /**
     * 创建用户
     *
     * @param user 用户实体类
     * @return Result
     */
    @PostMapping("/register")
    public Result createUser(@Valid @RequestBody User user) {
        if (userService.createUser(user) > 0) {
            return Result.success();
        } else {
            return Result.error("创建失败");
        }
    }
}
