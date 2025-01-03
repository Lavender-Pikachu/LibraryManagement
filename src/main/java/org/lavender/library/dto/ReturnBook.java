package org.lavender.library.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBook {
    private Long borrowId;
    private Long bookId;
    private String bookName;
    private String bookPlaceNum;
    private String username;
    private String learnNum;
    private String className;
    private String phone;
    private String borrowStatus;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date borrowTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date returnTime;
    private String timeStatus;

    @Override
    public String toString() {
        return "ReturnBook{" +
                "borrowId=" + borrowId +
                ", bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookPlaceNum='" + bookPlaceNum + '\'' +
                ", username='" + username + '\'' +
                ", learnNum='" + learnNum + '\'' +
                ", className='" + className + '\'' +
                ", phone='" + phone + '\'' +
                ", borrowStatus='" + borrowStatus + '\'' +
                ", borrowTime=" + borrowTime +
                ", returnTime=" + returnTime +
                ", timeStatus='" + timeStatus + '\'' +
                '}';
    }
}
