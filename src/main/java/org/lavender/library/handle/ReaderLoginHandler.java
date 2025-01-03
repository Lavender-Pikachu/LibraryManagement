package org.lavender.library.handle;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.lavender.library.dto.LoginResult;
import org.lavender.library.entity.SysReader;
import org.lavender.library.jwt.JwtUtils;
import org.lavender.library.service.SysReaderService;
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
 * @since 2024/12/27  21:54
 */

@Component
public class ReaderLoginHandler implements LoginHandler{

    private final SysReaderService sysReaderService;
    private final JwtUtils jwtUtils;

    public ReaderLoginHandler(SysReaderService sysReaderService, JwtUtils jwtUtils) {
        this.sysReaderService = sysReaderService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResultVo handleLogin(String username, String password) {
        LambdaQueryWrapper<SysReader> query = new LambdaQueryWrapper<>();
        query.eq(SysReader::getUsername, username)
                .eq(SysReader::getPassword, DigestUtils.md5DigestAsHex(password.getBytes()));
        SysReader one = sysReaderService.getOne(query);
        if(one == null){
            return ResultUtils.error("用户名或密码错误");
        }
        if("0".equals(one.getUserStatus())){
            return ResultUtils.error("读者账号未启用, 请联系管理员审核!");
        }

        LoginResult result = new LoginResult();
        result.setToken(jwtUtils.generateToken(one.getUsername(),"0"));
        result.setUserId(one.getReaderId());
        return ResultUtils.success("登陆成功", result);
    }
}
