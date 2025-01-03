package org.lavender.library.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.lavender.library.annotation.Auth;

import org.lavender.library.entity.BorrowBook;
import org.lavender.library.jwt.JwtUtils;
import org.lavender.library.utils.ResultUtils;
import org.lavender.library.utils.ResultVo;

import org.lavender.library.service.BorrowBookService;
import io.jsonwebtoken.Claims;
import org.lavender.library.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowBookController {

    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private BorrowBookService borrowBookService;

    @PostMapping("/applyBook")
    @Auth
    public ResultVo applyBook(@RequestBody BorrowBook borrowBook) {
        borrowBook.setBorrowStatus("1");
        borrowBook.setApplyStatus("1");
        boolean b = borrowBookService.updateById(borrowBook);
        if (b) {
            return ResultUtils.success("审核成功!");
        }
        return ResultUtils.error("审核失败!");
    }

    @PostMapping
    @Auth
    public ResultVo borrow(@RequestBody BorrowParm parm, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return ResultUtils.error("token过期!", 600);
        }
        Claims claims = jwtUtils.getClaimsFromToken(token);
        String userType = (String) claims.get("userType");
        borrowBookService.borrow(parm, userType);
        return ResultUtils.success("借书成功!");
    }

    //还书列表
    @Auth
    @GetMapping("/getBorrowList")
    public ResultVo getBorrowList(ExceptionParm.ListParm parm) {
        IPage<ReturnBook> borrowList = borrowBookService.getBorrowList(parm);
        return ResultUtils.success("查询成功", borrowList);
    }

    //还书
    @Auth
    @PostMapping("/returnBooks")
    public ResultVo returnBooks(@RequestBody List<ReturnParm> parm) {
        borrowBookService.returnBook(parm);
        return ResultUtils.success("还书成功!");
    }

    //异常还书
    @Auth
    @PostMapping("/exceptionBooks")
    public ResultVo exceptionBooks(@RequestBody ExceptionParm parm) {
        borrowBookService.exceptionBook(parm);
        return ResultUtils.success("还书成功!");
    }

    //借阅查看
    @Auth
    @GetMapping("/getLookBorrowList")
    public ResultVo getLookBorrowList(LookParm parm, HttpServletRequest request) {
        //获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return ResultUtils.success("token过期!", 600);
        }
        Claims claims = jwtUtils.getClaimsFromToken(token);
        String userType = (String) claims.get("userType");
        IPage<LookBorrow> lookBorrowList = null;
        if (userType.equals("0")) {
            lookBorrowList = borrowBookService.getReaderLookBorrowList(parm);
            return ResultUtils.success("查询成功", lookBorrowList);
        } else if (userType.equals("1")) {
            lookBorrowList = borrowBookService.getLookBorrowList(parm);
            return ResultUtils.success("查询成功", lookBorrowList);
        } else {
            return ResultUtils.success("查询成功", lookBorrowList);
        }
    }

    //借书续期
    @Auth
    @PostMapping("/addTime")
    public ResultVo addTime(@RequestBody BorrowParm parm) {
        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setBorrowId(parm.getBorrowId());
        borrowBook.setReturnTime(parm.getReturnTime());
        boolean b = borrowBookService.updateById(borrowBook);
        if (b) {
            return ResultUtils.success("续期成功!");
        }
        return ResultUtils.error("续期失败!");
    }

    //借书待审核总数
    @Auth
    @GetMapping("/getBorrowApplyCount")
    public ResultVo getBorrowApplyCount(String userType, Long userId) {
        if (userType.equals("0")) {
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            query.lambda().eq(BorrowBook::getApplyStatus, "0")
                    .eq(BorrowBook::getReaderId, userId);
            long count = borrowBookService.count(query);
            return ResultUtils.success("查询成功", count);
        } else if (userType.equals("1")) {
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            query.lambda().eq(BorrowBook::getApplyStatus, "0");
            long count = borrowBookService.count(query);
            return ResultUtils.success("查询成功", count);
        } else {
            return ResultUtils.success("查询成功", 0);
        }
    }

    //到期待还书总数
    @Auth
    @GetMapping("/getBorrowReturnCount")
    public ResultVo getBorrowReturnCount(String userType, Long userId) {
        if (userType.equals("0")) { //读者
            LambdaQueryWrapper<BorrowBook> queryWrapper =  new LambdaQueryWrapper<>();
            queryWrapper.eq(BorrowBook::getBorrowStatus, "1")
                    .lt(BorrowBook::getReturnTime, new Date())
                    .eq(BorrowBook::getReaderId, userId);
            long count = borrowBookService.count(queryWrapper);
            return ResultUtils.success("查询成功", count);
        } else if (userType.equals("1")) { //系统管理员
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            LambdaQueryWrapper<BorrowBook> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BorrowBook::getBorrowStatus, "1")
                    .lt(BorrowBook::getReturnTime, new Date());
            long count = borrowBookService.count(queryWrapper);
            return ResultUtils.success("查询成功", count);
        } else {
            return ResultUtils.success("查询成功", 0);
        }

    }
}
