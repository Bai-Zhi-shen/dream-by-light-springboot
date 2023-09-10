package com.bai.interceptor;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.bai.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Classname LoginCheckInterceptor
 * @Description 登录检查拦截器
 * @Version 1.0.0
 * @Date 2023/9/10 0:57
 * @Created by Bai
 */
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override //目标资源方法运行前运行, 返回true: 放行, 放回false, 不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse resp, Object handler) throws Exception {
        //1.获取请求url。
        String url = request.getRequestURL().toString();
        log.info("请求的url: {}", url);

        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if (url.contains("login")) {
            log.info("登录操作, 放行...");
            return true;
        }

        //放行OPTIONS请求
        if ("OPTIONS".equals(request.getMethod())) {
            log.info("OPTIONS, 放行...");
            return true;
        }

        //3.获取请求头中的令牌（token）。
        String token = request.getHeader("token");

        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if (!StringUtils.hasLength(token)) {
            log.info("请求头token为空,返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）。
        try {
            JWTUtil.verify(token, "dream".getBytes());
        } catch (Exception e) {//jwt解析失败
            e.printStackTrace();
            log.info("解析令牌失败, 返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }

        final JWT jwt = JWTUtil.parseToken(token);

        NumberWithFormat expire_time = (NumberWithFormat) jwt.getPayload("expire_time");
        if (expire_time.longValue() < System.currentTimeMillis()) {
            log.info("token过期");
            Result error = Result.error("EXPIRATION");
            String expiration = JSONObject.toJSONString(error);
            resp.getWriter().write(expiration);
            return false;
        }

        NumberWithFormat uid = (NumberWithFormat) jwt.getPayload("uid");

        //6.放行。
        request.setAttribute("uid", uid.intValue());
        return true;
    }

    @Override //目标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override //视图渲染完毕后运行, 最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
