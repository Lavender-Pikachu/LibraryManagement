package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.lavender.library.exception_advice.BusinessException;
import org.lavender.library.exception_advice.BusinessExceptionEnum;
import org.lavender.library.mapper.BorrowBookMapper;
import org.lavender.library.service.BorrowBookService;
import org.lavender.library.entity.SysBooks;
import org.lavender.library.service.SysBooksService;
import org.lavender.library.entity.BorrowBook;
import org.lavender.library.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class BorrowBookServiceImpl extends ServiceImpl<BorrowBookMapper, BorrowBook> implements BorrowBookService {

    @Resource
    private SysBooksService sysBooksService;

    private final Lock lock = new ReentrantLock();

    @Override
    @Transactional
    public void borrow(BorrowParm parm, String userType) {
        //加锁
        lock.lock();
        try {
            //构造查询条件
            LambdaQueryWrapper<SysBooks> query = new LambdaQueryWrapper<>();
            query.in(SysBooks::getBookId, parm.getBookIds());
            //查看库存是否充足
            List<SysBooks> list = sysBooksService.list(query);
            List<SysBooks> collect = list.stream().filter(item -> item.getBookStore().longValue() < 1L).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                //提示哪本图书库存不足
                List<String> bookNames = collect.stream().map(SysBooks::getBookName).collect(Collectors.toList());
                throw new BusinessException(BusinessExceptionEnum.NO_STOCK.getCode(),
                        String.join(", ", bookNames) + BusinessExceptionEnum.NO_STOCK.getMessage());
            }
            //减库存，插入借书明细
            List<Long> bookIds = parm.getBookIds();
            for (Long bookId: bookIds) {
                //减库存
                int res = sysBooksService.subBook(bookId);
                if (res > 0) {
                    BorrowBook borrowBook = createBorrowBook(parm, userType, bookId);
                    //插入明细
                    this.baseMapper.insert(borrowBook);
                }else{
                    throw new BusinessException(BusinessExceptionEnum.NO_STOCK.getCode(), "库存不足，无法借书！");
                }
            }
        } finally {
            //释放锁
            lock.unlock();
        }
    }

    private BorrowBook createBorrowBook(BorrowParm parm, String userType, Long bookId){
        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setBookId(bookId);
        borrowBook.setReaderId(parm.getReaderId());
        borrowBook.setReturnTime(parm.getReturnTime());
        borrowBook.setBorrowTime(new Date());

        if ("0".equals(userType)) { // 0 读者
            borrowBook.setApplyStatus("0");
            borrowBook.setBorrowStatus("0");
        } else if ("1".equals(userType)) { // 1 管理员
            borrowBook.setApplyStatus("1");
            borrowBook.setBorrowStatus("1");
        } else {
            throw new BusinessException(500, "用户类型不存在，无法借书！");
        }
        borrowBook.setBorrowTime(new Date());
        return borrowBook;
    }

    @Override
    public IPage<ReturnBook> getBorrowList(ExceptionParm.ListParm parm) {
        //构造分页对象
        Page<ReturnBook> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getBorrowList(page, parm);
    }

    @Override
    @Transactional
    public void returnBook(List<ReturnParm> list) {
        //加库存，变更借书状态
        for (ReturnParm returnParm: list) {
            //加库存
            int res = sysBooksService.addBook(returnParm.getBookId());
            if (res > 0) {
                //变更借书状态
                BorrowBook borrowBook = new BorrowBook();
                borrowBook.setBorrowId(returnParm.getBorrowId());
                borrowBook.setBorrowStatus("2"); //已还
                borrowBook.setReturnStatus("1"); //正常还书
                this.baseMapper.updateById(borrowBook);
            }
        }

    }

    @Override
    public void exceptionBook(ExceptionParm parm) {
        // 0: 异常、破损  1：丢失 ：不能还库存
        String type = parm.getType();
        if (type.equals("0")) {
            //加库存
            int res = sysBooksService.addBook(parm.getBookId());
            if (res > 0) {
                //变更借书状态
                BorrowBook borrowBook = new BorrowBook();
                borrowBook.setBorrowId(parm.getBorrowId());
                borrowBook.setBorrowStatus("2"); //已还
                borrowBook.setReturnStatus("2"); //异常还书
                borrowBook.setExcepionText(parm.getExcepionText());
                this.baseMapper.updateById(borrowBook);
            }
        } else { //丢失
            //变更借书状态
            BorrowBook borrowBook = new BorrowBook();
            borrowBook.setBorrowId(parm.getBorrowId());
            borrowBook.setBorrowStatus("2"); //已还
            borrowBook.setReturnStatus("3"); //丢失
            borrowBook.setExcepionText(parm.getExcepionText());
            this.baseMapper.updateById(borrowBook);
        }
    }

    @Override
    public IPage<LookBorrow> getLookBorrowList(LookParm parm) {
        //构造分页对象
        Page<LookBorrow> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getLookBorrowList(page, parm);
    }

    @Override
    public IPage<LookBorrow> getReaderLookBorrowList(LookParm parm) {
        //构造分页对象
        Page<LookBorrow> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getReaderLookBorrowList(page,parm);
    }
}
