package org.lavender.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TableName("sys_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu {
    @TableId(type = IdType.AUTO)
    private Long menuId;
    private Long parentId;
    private String title;
    private String code;
    private String name;
    private String path;
    private String url;
    //类型(0 目录 1菜单，2按钮)
    private String type;
    private String icon;
    private String parentName;
    private Long orderNum;
    private Date createTime;
    private Date updateTime;
    //该字段不属于数据库表，需要排除
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
    //不属于数据库表中的字段，需要排除
    @TableField(exist = false)
     private Boolean open;

    @Override
    public String toString() {
        return "SysMenu{" +
                "menuId=" + menuId +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                ", parentName='" + parentName + '\'' +
                ", orderNum=" + orderNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", children=" + children +
                ", open=" + open +
                '}';
    }
}
