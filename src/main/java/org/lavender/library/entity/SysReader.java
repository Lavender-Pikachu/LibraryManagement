package org.lavender.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用学号登录
 */
@TableName("sys_reader")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysReader{
    @TableId(type = IdType.AUTO)
    private Long readerId;
    private String learnNum; // 姓名
    private String username;  // 学号
    private String idCard;
    private String sex;
    private String phone;
    private String password;
    private String type;
    private String checkStatus;
    private String userStatus;
    private String className;

    @Override public String toString() {
        return String.format(
                "SysReader{readerId=%d, learnNum='%s', username='%s', idCard='%s', sex='%s', phone='%s', password='%s', checkStatus='%s', userStatus='%s', className='%s'}",
                readerId, learnNum, username, idCard, sex, phone, password, checkStatus, userStatus, className);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AssignParm {
        private Long userId;
        private Long roleId;

        @Override
        public String toString() {
            return "AssignParm{" +
                    "userId=" + userId +
                    ", roleId=" + roleId +
                    '}';
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignVo {
        //当前用户拥有的菜单
        private List<SysMenu> menuList = new ArrayList<>();
        //被分配的角色拥有的菜单id
        private Object[] checkList;

        @Override
        public String toString() {
            return "AssignVo{" +
                    "menuList=" + menuList +
                    ", checkList=" + Arrays.toString(checkList) +
                    '}';
        }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleParm {
        private Long currentPage;
        private Long pageSize;
        private String roleName;

        @Override
        public String toString() {
            return "RoleParm{" +
                    "currentPage=" + currentPage +
                    ", pageSize=" + pageSize +
                    ", roleName='" + roleName + '\'' +
                    '}';
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveAssign {
        private Long roleId;
        private List<Long> list;

        @Override
        public String toString() {
            return "SaveAssign{" +
                    "roleId=" + roleId +
                    ", list=" + list +
                    '}';
        }
    }

    @TableName("sys_role")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SysRole {

        @TableId(type = IdType.AUTO)
        private Long roleId;
        private String roleName;
        private String roleType;
        private String remark;
        private Date createTime;
        private Date updateTime;

        @Override
        public String toString() {
            return "SysRole{" +
                    "roleId=" + roleId +
                    ", roleName='" + roleName + '\'' +
                    ", roleType='" + roleType + '\'' +
                    ", remark='" + remark + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }
}
