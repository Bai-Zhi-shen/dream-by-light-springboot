package com.bai.service;

import cn.hutool.jwt.JWTUtil;
import com.bai.entity.User;
import com.bai.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname UserService
 * @Description 用户服务
 * @Version 1.0.0
 * @Date 2023/9/9 9:26
 * @Created by Bai
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return User
     */
    public User getUserById(int userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 创建用户
     *
     * @param user 用户实体类
     * @return int
     */
    public int createUser(User user) {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        return userMapper.insert(user);
    }

    /**
     * 登录
     *
     * @param user 用户实体类
     * @return String
     */
    public String login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone()).eq("password", DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            Integer userId = user.getUserId();
            Map<String, Object> map = new HashMap<String, Object>() {
                private static final long serialVersionUID = 1L;

                {
                    put("uid", userId);
                    put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
                }
            };
            return JWTUtil.createToken(map, "dream".getBytes());
        } else {
            return null;
        }
    }

    /**
     * 更新用户信息
     * @param user 用户实体类
     * @return int
     */
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }
}
