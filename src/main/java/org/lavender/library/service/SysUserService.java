package org.lavender.library.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lavender.library.dto.PageParm;
import org.lavender.library.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    IPage<SysUser> list(PageParm parm);
    void addUser(SysUser user);
    void editUser(SysUser user);
    SysUser loadByUsername(String username);
}
