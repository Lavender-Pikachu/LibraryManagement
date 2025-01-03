package org.lavender.library.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LookParm {
    private Long userId;
    //当前页
    private Long currentPage;
    //每页条数
    private Long pageSize;
    //学号
    private String username;
    //姓名
    private String learnNum;
    private String bookName;
     //0: 待审核 1： 已审核  2：拒绝
    private String applyStatus;
    //1:在借中  2：已还   3：拒绝
    private String borrowStatus;
    //1: 正常还书 2：异常还书
    private String returnStatus;
    // 1:到期 0：未到期
    private String timeStatus;

    @Override
    public String toString() {
        return "LookParm{" +
                "userId=" + userId +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", username='" + username + '\'' +
                ", learnNum='" + learnNum + '\'' +
                ", bookName='" + bookName + '\'' +
                ", applyStatus='" + applyStatus + '\'' +
                ", borrowStatus='" + borrowStatus + '\'' +
                ", returnStatus='" + returnStatus + '\'' +
                ", timeStatus='" + timeStatus + '\'' +
                '}';
    }
}