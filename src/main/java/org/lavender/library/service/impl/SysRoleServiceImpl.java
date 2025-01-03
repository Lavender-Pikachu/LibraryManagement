package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.lavender.library.utils.MakeTree;
import org.lavender.library.entity.SysMenu;
import org.lavender.library.entity.SysReader;
import org.lavender.library.service.SysMenuService;
import org.lavender.library.mapper.SysRoleMapper;
import org.lavender.library.service.SysRoleService;
import org.lavender.library.entity.SysUser;
import org.lavender.library.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysReader.SysRole> implements SysRoleService {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;
    @Override
    public IPage<SysReader.SysRole> list(SysReader.RoleParm parm) {
        //构造分页对象
        IPage<SysReader.SysRole> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //查询条件
        LambdaQueryWrapper<SysReader.SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getRoleName())){
            queryWrapper.like(SysReader.SysRole::getRoleName,parm.getRoleName());
        }
        return this.baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public SysReader.AssignVo getAssignShow(SysReader.AssignParm parm) {
        //查询当前用户的信息
        SysUser user = sysUserService.getById(parm.getUserId());
        //菜单数据
        List<SysMenu> list ;
        boolean isAdmin = "1".equals(user.getIsAdmin());
        if(isAdmin){ //如果是超级管理员，拥有所有的权限
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByAsc(SysMenu::getOrderNum);
            list = sysMenuService.list(queryWrapper);
        }else{
            list = sysMenuService.getMenuByUserId(user.getUserId());
        }
        //组装树
        List<SysMenu> menuList = MakeTree.makeMenuTree(list, 0L);
        //查询角色原来的菜单
        List<SysMenu> roleList = sysMenuService.getMenuByRoleId(parm.getRoleId());
        List<Long> ids = roleList == null ? Collections.emptyList():roleList.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        //组装数据
        SysReader.AssignVo vo = new SysReader.AssignVo();
        vo.setMenuList(menuList);
        vo.setCheckList(ids.toArray());
        return vo;
    }
}
