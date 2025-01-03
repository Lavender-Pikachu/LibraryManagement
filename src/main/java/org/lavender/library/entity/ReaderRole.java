package org.lavender.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("sys_reader_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderRole {
    @TableId(type = IdType.AUTO)
    private Long readerRoleId;
    private Long readerId;
    private Long roleId;

    @Override
    public String toString() {
        return "ReaderRole{" +
                "readerRoleId=" + readerRoleId +
                ", readerId=" + readerId +
                ", roleId=" + roleId +
                '}';
    }

    public Long getReaderRoleId() {
        return readerRoleId;
    }
}
