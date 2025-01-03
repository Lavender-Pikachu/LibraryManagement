package org.lavender.library.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lavender.library.dto.NoticeParm;
import org.lavender.library.entity.SysNotice;

public interface SysNoticeService extends IService<SysNotice> {
    IPage<SysNotice> getList(NoticeParm parm);
}
