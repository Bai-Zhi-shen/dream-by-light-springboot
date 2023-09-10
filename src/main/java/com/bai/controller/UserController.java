package com.bai.controller;

import com.bai.service.UserService;
import com.bai.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname TestController
 * @Description 用户控制器
 * @Version 1.0.0
 * @Date 2023/9/9 9:26
 * @Created by Bai
 */
@Slf4j
@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取当前登录用户信息
     *
     * @return Result
     */
    @GetMapping("/user")
    public Result getMyUser(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("uid");
        return Result.success(userService.getUserById(userId));
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return Result
     */
    @GetMapping("/user/{id}")
    public Result getUserById(@PathVariable("id") int userId) {
        return Result.success(userService.getUserById(userId));
    }
}
