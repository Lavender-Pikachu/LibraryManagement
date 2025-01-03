package org.lavender.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@TableName("sys_books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysBooks {
    @TableId(type = IdType.AUTO)
    private Long bookId;
     private Long categoryId;
    @TableField(exist = false)
    private String categoryName;
    private String bookName;
    private String bookCode;
    private String bookPlaceNum;
    private String bookAuther;
    private String bookProduct;
    private BigDecimal bookPrice;
    private Integer bookStore;

    @Override
    public String toString() {
        return "SysBooks{" +
                "bookId=" + bookId +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookCode='" + bookCode + '\'' +
                ", bookPlaceNum='" + bookPlaceNum + '\'' +
                ", bookAuther='" + bookAuther + '\'' +
                ", bookProduct='" + bookProduct + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookStore=" + bookStore +
                '}';
    }
}
