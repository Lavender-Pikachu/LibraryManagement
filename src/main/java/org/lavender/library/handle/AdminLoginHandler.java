package org.lavender.library.handle;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.lavender.library.dto.LoginResult;
import org.lavender.library.entity.SysUser;
import org.lavender.library.jwt.JwtUtils;
import org.lavender.library.service.SysUserService;
import org.lavender.library.utils.ResultUtils;
import org.lavender.library.utils.ResultVo;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;


/**
 * <p>
 * a happy start
 * </p>
 *
 * @author thg
 * @version 1.0.0
 * @since 2024/12/27  22:02
 */
@Component
public class AdminLoginHandler implements LoginHandler {
    private final SysUserService sysUserService;
    private final JwtUtils jwtUtils;
    public AdminLoginHandler(SysUserService sysUserService, JwtUtils jwtUtils) {
        this.sysUserService = sysUserService;
        this.jwtUtils = jwtUtils;
    }
    @Override
    public ResultVo handleLogin(String username, String password) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        query.eq(SysUser::getUsername, username)
                .eq(SysUser::getPassword, DigestUtils.md5DigestAsHex(password.getBytes()));
        SysUser one = sysUserService.getOne(query);
        if (one == null) {
            return ResultUtils.error("用户名或密码错误");
        }
        LoginResult  result = new LoginResult();
        result.setToken(jwtUtils.generateToken(one.getUsername(),"1"));
        result.setUserId(one.getUserId());
        return ResultUtils.success("登陆成功",result);
    }
}
