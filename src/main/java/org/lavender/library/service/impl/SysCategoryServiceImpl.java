package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.lavender.library.dto.CategoryEcharts;
import org.lavender.library.dto.CategoryVo;
import org.lavender.library.dto.ListCateParm;
import org.lavender.library.entity.SysCategory;
import org.lavender.library.mapper.SysCategoryMapper;
import org.lavender.library.service.SysCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysCategoryServiceImpl extends ServiceImpl<SysCategoryMapper, SysCategory> implements SysCategoryService {
    @Override
    public IPage<SysCategory> getList(ListCateParm parm) {
        //构造分页对象
        IPage<SysCategory> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //查询条件
        LambdaQueryWrapper<SysCategory> query = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getCategoryName())) {
            query.like(SysCategory::getCategoryName,parm.getCategoryName());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    public CategoryEcharts getCategoryVo() {
        CategoryEcharts echarts = new CategoryEcharts();
        List<CategoryVo> categoryVo = this.baseMapper.getCategoryVo();
        List<String> names = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        if(!categoryVo.isEmpty()){
            for(CategoryVo categoryVoItem: categoryVo){
                names.add(categoryVoItem.getCategoryName());
                counts.add(categoryVoItem.getBookCount());
            }
            echarts.setCounts(counts);
            echarts.setNames(names);
        }
        return echarts;
    }
}
