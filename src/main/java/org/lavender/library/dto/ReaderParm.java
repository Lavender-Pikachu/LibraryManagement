package org.lavender.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderParm {
    private Long currentPage;
    private Long pageSize;
    private String username;
    private String idCard;
    private String phone;
    private String learnNum;

    @Override
    public String toString() {
        return "ReaderParm{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", username='" + username + '\'' +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                ", learnNum='" + learnNum + '\'' +
                '}';
    }
}
