package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.lavender.library.dto.NoticeParm;
import org.lavender.library.entity.SysNotice;
import org.lavender.library.mapper.SysNoticeMapper;
import org.lavender.library.service.SysNoticeService;
import org.springframework.stereotype.Service;

@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    @Override
    public IPage<SysNotice> getList(NoticeParm parm) {
        //构造分页对象
        Page<SysNotice> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //构造查询条件
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getNoticeTitle())){
            queryWrapper.like(SysNotice::getNoticeTitle,parm.getNoticeTitle());
        }
        queryWrapper.orderByDesc(SysNotice::getCreateTime);
        return this.baseMapper.selectPage(page,queryWrapper);
    }
}
