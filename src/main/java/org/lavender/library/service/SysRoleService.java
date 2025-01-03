package org.lavender.library.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lavender.library.entity.SysReader;

public interface SysRoleService extends IService<SysReader.SysRole> {
    IPage<SysReader.SysRole> list(SysReader.RoleParm parm);
    //角色权限的回显
    SysReader.AssignVo getAssignShow(SysReader.AssignParm parm);
}
