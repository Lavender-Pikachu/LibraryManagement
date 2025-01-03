package org.lavender.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lavender.library.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    //保存角色的权限
    void assignSave(@Param("roleId") Long roleId, @Param("menuList")List<Long> menuList);
}
