package org.lavender.library.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lavender.library.dto.BookVo;
import org.lavender.library.dto.ExceptionParm;
import org.lavender.library.entity.SysBooks;

import java.util.List;

public interface SysBooksService extends IService<SysBooks> {
    IPage<SysBooks> getList(ExceptionParm.ListParm parm);
    //减库存
    int subBook(Long bookId);
    //加库存
    int addBook(Long bookId);
     List<BookVo> getHotBook();
}
