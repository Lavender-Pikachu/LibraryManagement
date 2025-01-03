package org.lavender.library.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.lavender.library.annotation.Auth;
import org.lavender.library.utils.ResultUtils;
import org.lavender.library.utils.ResultVo;
import org.lavender.library.dto.CategoryEcharts;
import org.lavender.library.dto.ListCateParm;
import org.lavender.library.entity.SysCategory;
import org.lavender.library.service.SysCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class SysCategoryController {
    @Resource
    private SysCategoryService sysCategoryService;

    //新增
    @Auth
    @PostMapping
    public ResultVo add(@RequestBody SysCategory category){
        boolean save = sysCategoryService.save(category);
        if(save){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @Auth
    @PutMapping
    public ResultVo edit(@RequestBody SysCategory category){
        boolean save = sysCategoryService.updateById(category);
        if(save){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @Auth
    @DeleteMapping("/{categoryId}")
    public ResultVo delete(@PathVariable("categoryId") Long categoryId){
        boolean remove = sysCategoryService.removeById(categoryId);
        if(remove){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @Auth
    @GetMapping("/list")
    public ResultVo getList(ListCateParm parm){
        IPage<SysCategory> list = sysCategoryService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    //图书列表分类
    @Auth
    @GetMapping("/cateList")
    public ResultVo getCateList(){
        List<SysCategory> list = sysCategoryService.list();
        return ResultUtils.success("查询成功",list);
    }

    //图书列表分类
    @Auth
    @GetMapping("/categoryCount")
    public ResultVo categoryCount(){
        CategoryEcharts categoryVo = sysCategoryService.getCategoryVo();
        return ResultUtils.success("查询成功",categoryVo);
    }
}
