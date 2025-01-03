package org.lavender.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListParm {
    private Long currentPage;
    private Long pageSize;
    private String username;
    private String borrowStatus;

    @Override
    public String toString() {
        return "ListParm{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", username='" + username + '\'' +
                ", borrowStatus='" + borrowStatus + '\'' +
                '}';
    }
}