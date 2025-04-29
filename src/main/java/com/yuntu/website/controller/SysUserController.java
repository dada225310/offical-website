package com.yuntu.website.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuntu.website.common.Result;
import com.yuntu.website.common.ResultCode;
import com.yuntu.website.common.ResultUtil;
import com.yuntu.website.config.CaffeineCache;
import com.yuntu.website.dao.SysUserMapper;
import com.yuntu.website.dto.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserMapper userMapper;
    private LineCaptcha lineCaptcha;
    @Autowired
    private CaffeineCache caffeineCache;

    /**
     * 注册
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody SysUser user) {

        if (StrUtil.isBlank(user.getUserName()) || StrUtil.isBlank(user.getPassword())) {
            return ResultUtil.failure(ResultCode.FAIL, "用户名或密码不能为空");
        }
        //用户名密码只能是密码+数字
        if (!user.getUserName().matches("^[a-zA-Z0-9]+$") || !user.getPassword().matches("^[a-zA-Z0-9]+$")) {
            return ResultUtil.failure(ResultCode.FAIL, "用户名和密码只能是密码+数字");
        }
        //密码最少6位，用户名最少4位
        if (user.getUserName().length() < 4 || user.getPassword().length() < 4) {
            return ResultUtil.failure(ResultCode.FAIL, "用户名和密码最少4位");
        }
        //验证用户名是否已经注册
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, user.getUserName());
        SysUser sysUser = userMapper.selectOne(wrapper);
        if (sysUser != null) {
            return ResultUtil.failure(ResultCode.FAIL, "用户名已注册");
        }
        //验证码不能为空
        if (StrUtil.isBlank(user.getQrCode())) {
            return ResultUtil.failure(ResultCode.FAIL, "验证码不能为空");
        }
        //验证码校验
        String qrCode = caffeineCache.getQrCache().getIfPresent(user.getUserName());
        if (!user.getQrCode().equals(qrCode)) {
            return ResultUtil.failure(ResultCode.FAIL, "验证码错误");
        }
        userMapper.insert(user);
        return ResultUtil.success();
    }

    /**
     * 登陆
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody SysUser user) {
        if (StrUtil.isBlank(user.getUserName()) || StrUtil.isBlank(user.getPassword())) {
            return ResultUtil.failure(ResultCode.FAIL, "用户名或密码不能为空");
        }
        //验证用户名是否已经注册
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>();
        wrapper.eq(SysUser::getUserName, user.getUserName());
        wrapper.eq(SysUser::getPassword, user.getPassword());
        SysUser sysUser = userMapper.selectOne(wrapper);
        if (sysUser == null) {
            return ResultUtil.failure(ResultCode.FAIL, "用户名或者密码错误");
        }
        return ResultUtil.success(sysUser);
    }

    @GetMapping("/getCode")
    public void getCode(@RequestParam(required = false) String userName,HttpServletResponse response) {
        // 判断username
        if (StrUtil.isBlank(userName)) {
            userName = IdUtil.fastSimpleUUID();
        }
        // 随机生成 4 位验证码
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        // 定义图片的显示大小
        lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        try {
            // 调用父类的 setGenerator() 方法，设置验证码的类型
            lineCaptcha.setGenerator(randomGenerator);
            // 输出到页面
            lineCaptcha.write(response.getOutputStream());
            log.info("用户:{},生成的验证码:{}", userName, lineCaptcha.getCode());

            caffeineCache.getQrCache().put(userName, lineCaptcha.getCode());
            // 关闭流
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
