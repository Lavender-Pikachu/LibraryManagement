package org.lavender.library.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.lavender.library.annotation.Auth;
import org.lavender.library.entity.SysReader;
import org.lavender.library.utils.ResultUtils;
import org.lavender.library.utils.ResultVo;
import org.lavender.library.service.SysRoleService;
import org.lavender.library.service.RoleMenuService;
import org.springframework.web.bind.annotation.*;


import java.util.Date;

@RestController
@RequestMapping("/api/role")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private RoleMenuService roleMenuService;

    //新增
    @Auth
    @PostMapping
    public ResultVo addRole(@RequestBody SysReader.SysRole role) {
        if (role.getRoleType().equals("2")) {
            QueryWrapper<SysReader.SysRole> query = new QueryWrapper<>();
            query.lambda().eq(SysReader.SysRole::getRoleType, "2");
            SysReader.SysRole one = sysRoleService.getOne(query);
            if (one != null) {
                return ResultUtils.error("读者角色已经存在!");
            }
        }
        role.setCreateTime(new Date());
        boolean save = sysRoleService.save(role);
        if (save) {
            return ResultUtils.success("新增角色成功!");
        }
        return ResultUtils.error("新增角色失败!");
    }

    //编辑
    @Auth
    @PutMapping
    public ResultVo editRole(@RequestBody SysReader.SysRole role) {
        if (role.getRoleType().equals("2")) {
            QueryWrapper<SysReader.SysRole> query = new QueryWrapper<>();
            query.lambda().eq(SysReader.SysRole::getRoleType, "2");
            SysReader.SysRole one = sysRoleService.getOne(query);
            if (one != null && role.getRoleId() != one.getRoleId()) {
                return ResultUtils.error("读者角色已经存在!");
            }
        }
        role.setCreateTime(new Date());
        boolean save = sysRoleService.updateById(role);
        if (save) {
            return ResultUtils.success("编辑角色成功!");
        }
        return ResultUtils.error("编辑角色失败!");
    }

    //删除
    @Auth
    @DeleteMapping("/{roleId}")
    public ResultVo deleteRole(@PathVariable("roleId") Long roleId) {
        boolean remove = sysRoleService.removeById(roleId);
        if (remove) {
            return ResultUtils.success("删除角色成功!");
        }
        return ResultUtils.error("删除角色失败!");
    }

    //列表
    @Auth
    @GetMapping("/list")
    public ResultVo getList(SysReader.RoleParm parm) {
        IPage<SysReader.SysRole> list = sysRoleService.list(parm);
        return ResultUtils.success("查询成功!", list);
    }

    //查询角色权限树的回显
    @Auth
    @GetMapping("/getAssingShow")
    public ResultVo getAssingShow(SysReader.AssignParm parm) {
        SysReader.AssignVo show = sysRoleService.getAssignShow(parm);
        return ResultUtils.success("查询成功", show);
    }

    //角色分配权限保存
    @Auth
    @PostMapping("/assignSave")
    public ResultVo assignSave(@RequestBody SysReader.SaveAssign parm) {
        roleMenuService.assignSave(parm.getRoleId(), parm.getList());
        return ResultUtils.success("分配成功!");
    }
}
