package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lavender.library.utils.MakeTree;
import org.lavender.library.entity.SysMenu;
import org.lavender.library.mapper.SysMenuMapper;
import org.lavender.library.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> menuList() {
        //查询列表
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menuList = this.baseMapper.selectList(queryWrapper);
        //组装树
        return MakeTree.makeMenuTree(menuList, 0L);
    }

    @Override
    public List<SysMenu> parentList() {
        //只需要查询目录和菜单
        String[] types = {"0","1"};
        //构造查询条件
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().in(SysMenu::getType, Arrays.asList(types)).orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> sysMenus = this.baseMapper.selectList(query);
        //构造顶级菜单
        SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setParentId(-1L);
        menu.setTitle("顶级菜单");
        sysMenus.add(menu);
        //构造树
        return MakeTree.makeMenuTree(sysMenus, -1L);
    }

    @Override
    public List<SysMenu> getMenuByUserId(Long userId) {
        return this.baseMapper.getMenuByUserId(userId);
    }

    @Override
    public List<SysMenu> getMenuByRoleId(Long roleId) {
        return this.baseMapper.getMenuByRoleId(roleId);
    }

    @Override
    public List<SysMenu> getReaderMenuByUserId(Long readerId) {
        return this.baseMapper.getReaderMenuByUserId(readerId);
    }
}
