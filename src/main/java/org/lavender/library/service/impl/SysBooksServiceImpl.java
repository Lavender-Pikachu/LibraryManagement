package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lavender.library.dto.BookVo;
import org.lavender.library.dto.ExceptionParm;
import org.lavender.library.entity.SysBooks;
import org.lavender.library.mapper.SysBooksMapper;
import org.lavender.library.service.SysBooksService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysBooksServiceImpl extends ServiceImpl<SysBooksMapper, SysBooks> implements SysBooksService {
    @Override
    public IPage<SysBooks> getList(ExceptionParm.ListParm parm) {
        //构造分页对象
        Page<SysBooks> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getList(page,parm);
    }

    @Override
    public int subBook(Long bookId) {
        return this.baseMapper.subBook(bookId);
    }

    @Override
    public int addBook(Long bookId) {
        return this.baseMapper.addBook(bookId);
    }

    @Override
    public List<BookVo> getHotBook() {
        return this.baseMapper.getHotBook();
    }
}
