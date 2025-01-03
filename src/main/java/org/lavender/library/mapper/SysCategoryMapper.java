package org.lavender.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lavender.library.dto.CategoryVo;
import org.lavender.library.entity.SysCategory;

import java.util.List;

public interface SysCategoryMapper extends BaseMapper<SysCategory> {
    List<CategoryVo> getCategoryVo();
}
