package com.bai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    @Length(min = 6, max = 16, message = "密码必须在6-16位之间")
    private String password;
    @Min(1)
    @Max(2)
    private Integer userTypeId;
    private String userName;
    @Min(0)
    @Max(2)
    private Integer sex;
    private String avatar;
    private LocalDateTime registrationTime;
    private Integer status;
}
