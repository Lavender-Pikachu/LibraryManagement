package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.lavender.library.dto.PageParm;
import org.lavender.library.entity.SysUser;
import org.lavender.library.mapper.SysUserMapper;
import org.lavender.library.service.SysUserService;
import org.lavender.library.entity.UserRole;
import org.lavender.library.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {    //继承ServiceImpl使用Mybatis Plus提供的方法，实现SYsUserService可以定义自己的方法

    @Resource
    private UserRoleService userRoleService;

    @Override
    public IPage<SysUser> list(PageParm parm) {
        //构造分页对象
        Page<SysUser> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());

        //构造查询条件
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getNickName())){
             query.like(SysUser::getNickName,parm.getNickName());
        }
        if(StringUtils.isNotEmpty(parm.getPhone())){
             query.like(SysUser::getPhone,parm.getPhone());
        }
        return this.baseMapper.selectPage(page,query);

    }

    @Override
    @Transactional
    public void addUser(SysUser user) {
        //插入用户
        int insert = this.baseMapper.insert(user);
        if(insert >0){
            //插入用户对应的角色
            UserRole userRole = new UserRole();
            userRole.setRoleId(user.getRoleId());
            userRole.setUserId(user.getUserId());
            userRoleService.save(userRole);
        }
    }

    @Override
    public void editUser(SysUser user) {
        int i = this.baseMapper.updateById(user);
        //先删除原来的角色，再重新插入
        if(i > 0){
            LambdaQueryWrapper<UserRole> query = new LambdaQueryWrapper<>();
            query.eq(UserRole::getUserId, user.getUserId());
            //删除
            userRoleService.remove(query);
            //插入用户对应的角色
            UserRole userRole = new UserRole();
            userRole.setRoleId(user.getRoleId());
            userRole.setUserId(user.getUserId());
            userRoleService.save(userRole);
        }
    }

    @Override
    public SysUser loadByUsername(String username) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        query.eq(SysUser::getUsername, username);
        return this.baseMapper.selectOne(query);
    }
}
