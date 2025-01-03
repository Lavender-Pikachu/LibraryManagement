package org.lavender.library.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lavender.library.dto.CategoryEcharts;
import org.lavender.library.dto.ListCateParm;
import org.lavender.library.entity.SysCategory;


public interface SysCategoryService extends IService<SysCategory> {
    IPage<SysCategory> getList(ListCateParm parm);
    CategoryEcharts getCategoryVo();
}
