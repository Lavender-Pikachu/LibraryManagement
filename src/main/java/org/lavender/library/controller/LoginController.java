package org.lavender.library.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.lavender.library.annotation.Auth;
import org.lavender.library.jwt.JwtUtils;
import org.lavender.library.service.impl.LoginService;
import org.lavender.library.utils.ResultUtils;
import org.lavender.library.utils.ResultVo;
import org.lavender.library.dto.LoginParm;
import org.lavender.library.entity.UserInfo;
import org.lavender.library.utils.MakeTree;
import org.lavender.library.dto.RouterVO;
import org.lavender.library.entity.SysMenu;
import org.lavender.library.service.SysMenuService;
import org.lavender.library.entity.SysReader;
import org.lavender.library.service.SysReaderService;
import org.lavender.library.entity.SysUser;
import org.lavender.library.service.SysUserService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/system")
public class LoginController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysReaderService sysReaderService;

    @Resource
    private LoginService loginService;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private SysMenuService sysMenuService;

    //用户登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm loginParm) {
        if (StringUtils.isEmpty(loginParm.getUsername()) ||
                StringUtils.isEmpty(loginParm.getPassword()) ||
                StringUtils.isEmpty(loginParm.getUserType())) {
            return ResultUtils.error("用户名、密码或用户类型不能为空!");
        }
        return loginService.login(loginParm);
    }

    //获取用户权限字段
    @Auth
    @GetMapping("/getInfo")
    public ResultVo getInfo(Long userId, HttpServletRequest request) {
        //从请求的头部获取token
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            return ResultUtils.success("token过期!",600);
        }
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        //定义用户信息类
        UserInfo userInfo = new UserInfo();
        if (userType.equals("0")) { //读者
            //根据id查询读者信息
            SysReader reader = sysReaderService.getById(userId);
            userInfo.setIntroduction(reader.getLearnNum());
            userInfo.setName(reader.getLearnNum());
//            userInfo.setAvatar("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2F2021%2Fedpic_source%2F07%2F09%2F47%2F070947ecb7af19ce4ed6f1c0cc11bb35_20.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648386687&t=b1899db2b5f3ff398f994e2bfbc190fc");
            //权限字段查询与设置
            List<SysMenu> menuList = sysMenuService.getReaderMenuByUserId(userId);
            List<String> collect = menuList.stream().filter(item -> item != null && item.getCode() != null).map(SysMenu::getCode).collect(Collectors.toList());
            if (collect.isEmpty()) {
                return ResultUtils.error("暂无登录权限，请联系管理员!");
            }

            //转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("查询成功", userInfo);
        } else if (userType.equals("1")) { //管理员
            SysUser user = sysUserService.getById(userId);
            userInfo.setIntroduction(user.getNickName());
            userInfo.setName(user.getNickName());
            userInfo.setAvatar("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2F7a899e510fb30f24aac12a78c495d143ad4b0376&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648387335&t=a8d9141b4caa798d6369491b79ca243d");
            //权限字段查询与设置
            List<SysMenu> menuList = sysMenuService.getMenuByUserId(userId);
            List<String> collect = menuList.stream().filter(item -> item != null && item.getCode() != null).map(SysMenu::getCode).collect(Collectors.toList());
            if (collect.isEmpty()) {
                return ResultUtils.error("暂无登录权限，请联系管理员!");
            }
            //转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("查询成功", userInfo);
        } else {
            return ResultUtils.error("用户类型不存在", userInfo);
        }
    }

    //查询菜单
    @Auth
    @GetMapping("/getMenuList")
    public ResultVo getMenuList(HttpServletRequest request) {
        //获取token
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            return ResultUtils.success("token过期!",600);
        }
        //获取用户名和类型
        String username = jwtUtils.getUsernameFromToken(token);
        //用户类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        if (userType.equals("0")) { //读者
            //获取读者信息
            SysReader reader = sysReaderService.loadByUsername(username);
            if(reader == null) {
                return ResultUtils.error("登录信息过期");
            }
            //查询菜单信息
            List<SysMenu> menuList = sysMenuService.getReaderMenuByUserId(reader.getReaderId());
            List<SysMenu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            if (collect.isEmpty()) {
                return ResultUtils.error("暂无登录权限，请联系管理员!");
            }
            //组装路由格式的数据
            List<RouterVO> routerVOS = MakeTree.makeRouter(collect, 0L);
            return ResultUtils.success("查询成功", routerVOS);
        } else if (userType.equals("1")) { //管理员
            //获取用户信息
            SysUser reader = sysUserService.loadByUsername(username);
            if(reader == null) {
                return ResultUtils.error("登录信息过期");
            }
            //查询菜单信息
            List<SysMenu> menuList = sysMenuService.getMenuByUserId(reader.getUserId());
            List<SysMenu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            if (collect.isEmpty()) {
                return ResultUtils.error("暂无登录权限，请联系管理员!");
            }
            //组装路由格式的数据
            List<RouterVO> routerVOS = MakeTree.makeRouter(collect, 0L);
            return ResultUtils.success("查询成功", routerVOS);
        } else {
            return ResultUtils.error("用户类型不存在!");
        }
    }
}
