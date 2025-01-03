package org.lavender.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeParm {
    private Long currentPage;
    private Long pageSize;
    private String noticeTitle;

    @Override
    public String toString() {
        return "NoticeParm{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", noticeTitle='" + noticeTitle + '\'' +
                '}';
    }
}
