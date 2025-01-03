package org.lavender.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookVo {
    private String name;
    private Integer value;

    @Override
    public String toString() {
        return "BookVo{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
