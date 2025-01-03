package org.lavender.library.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnParm {

    private Long borrowId;
    //图书id
    private Long bookId;

    @Override
    public String toString() {
        return "ReturnParm{" +
                "borrowId=" + borrowId +
                ", bookId=" + bookId +
                '}';
    }
}