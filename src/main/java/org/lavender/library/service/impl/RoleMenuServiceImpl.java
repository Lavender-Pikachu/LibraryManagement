package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lavender.library.entity.RoleMenu;
import org.lavender.library.mapper.RoleMenuMapper;
import org.lavender.library.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Override
    @Transactional
    public void assignSave(Long roleId, List<Long> menuList) {
        //1、删除角色原来的菜单
        LambdaQueryWrapper<RoleMenu> query = new LambdaQueryWrapper<>();
        query.eq(RoleMenu::getRoleId, roleId);
        this.baseMapper.delete(query);
        //2、插入新的菜单
        this.baseMapper.assignSave(roleId,menuList);
    }
}
