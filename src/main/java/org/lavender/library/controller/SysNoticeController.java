package org.lavender.library.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.lavender.library.annotation.Auth;
import org.lavender.library.utils.ResultUtils;
import org.lavender.library.utils.ResultVo;
import org.lavender.library.dto.NoticeParm;
import org.lavender.library.entity.SysNotice;
import org.lavender.library.service.SysNoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class SysNoticeController {
    @Resource
    private SysNoticeService sysNoticeService;

    //新增
    @Auth
    @PostMapping
    public ResultVo add(@RequestBody SysNotice sysNotice){
        sysNotice.setCreateTime(new Date());
        boolean save = sysNoticeService.save(sysNotice);
        if(save){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @Auth
    @PutMapping
    public ResultVo edit(@RequestBody SysNotice sysNotice){
        boolean save = sysNoticeService.updateById(sysNotice);
        if(save){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @Auth
    @DeleteMapping("/{noticeId}")
    public ResultVo delete(@PathVariable("noticeId") Long noticeId){
        boolean save = sysNoticeService.removeById(noticeId);
        if(save){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @Auth
    @GetMapping("/list")
    public ResultVo getList(NoticeParm parm){
        IPage<SysNotice> list = sysNoticeService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    //列表
    @Auth
    @GetMapping("/getTopList")
    public ResultVo getTopList(){
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysNotice::getCreateTime).last("limit 3");
        List<SysNotice> list = sysNoticeService.list(queryWrapper);
        return ResultUtils.success("查询成功",list);
    }
}
