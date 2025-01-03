package org.lavender.library.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCateParm {
    private Long currentPage;
    private Long pageSize;
    private String categoryName;

    @Override
    public String toString() {
        return "ListCateParm{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
