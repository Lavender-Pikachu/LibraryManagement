package org.lavender.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParm {
    private Long currentPage;
    private Long pageSize;
    private String phone;
    private String nickName;

    @Override
    public String toString() {
        return "PageParm{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", phone='" + phone + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
