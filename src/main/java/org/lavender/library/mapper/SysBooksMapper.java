package org.lavender.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lavender.library.dto.BookVo;
import org.lavender.library.dto.ExceptionParm;
import org.lavender.library.entity.SysBooks;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysBooksMapper extends BaseMapper<SysBooks> {
    IPage<SysBooks> getList(Page<SysBooks> page,@Param("parm") ExceptionParm.ListParm parm);
    int subBook(@Param("bookId") Long bookId);
    int addBook(@Param("bookId") Long bookId);
    List<BookVo> getHotBook();
}
