package org.lavender.library.service.impl;

import org.lavender.library.constant.UserType;
import org.lavender.library.dto.LoginParm;
import org.lavender.library.handle.AdminLoginHandler;
import org.lavender.library.handle.LoginHandler;
import org.lavender.library.handle.ReaderLoginHandler;
import org.lavender.library.jwt.JwtUtils;
import org.lavender.library.service.SysReaderService;
import org.lavender.library.service.SysUserService;
import org.lavender.library.utils.ResultUtils;
import org.lavender.library.utils.ResultVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * a happy start
 * </p>
 *
 * @author thg
 * @version 1.0.0
 * @since 2024/12/27  22:07
 */
@Service
public class LoginService {
    private final Map<String, LoginHandler> loginHandlers = new HashMap<>();
    public LoginService(SysUserService sysUserService, SysReaderService sysReaderService, JwtUtils jwtUtils) {
        loginHandlers.put(UserType.READER, new ReaderLoginHandler(sysReaderService, jwtUtils));
        loginHandlers.put(UserType.ADMIN, new AdminLoginHandler(sysUserService, jwtUtils));
    }
    public ResultVo login(LoginParm loginParm){
        try {
            String userType = loginParm.getUserType();
            LoginHandler loginHandler = loginHandlers.get(userType);
            if(loginHandler == null){
                return ResultUtils.error("用户类型不存在");
            }
            return loginHandler.handleLogin(loginParm.getUsername(), loginParm.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error("登陆失败，请稍后重试!!!");
        }
    }
}
