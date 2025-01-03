package org.lavender.library.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowParm {
    private Long borrowId;
    //借书人id
    private Long readerId;
    //图书id
    private List<Long> bookIds;
    //还书时间
    private Date returnTime;

    @Override
    public String toString() {
        return "BorrowParm{" +
                "borrowId=" + borrowId +
                ", readerId=" + readerId +
                ", bookIds=" + bookIds +
                ", returnTime=" + returnTime +
                '}';
    }
}