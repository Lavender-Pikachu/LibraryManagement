package org.lavender.library.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionParm {
    private Long borrowId;
    //图书id
    private Long bookId;
    //判断是异常还是丢失 0：异常 1：丢失
    private String type;
    //异常还书备注
    private String excepionText;

    @Override
    public String toString() {
        return "ExceptionParm{" +
                "borrowId=" + borrowId +
                ", bookId=" + bookId +
                ", type='" + type + '\'' +
                ", excepionText='" + excepionText + '\'' +
                '}';
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListParm {
        private Long currentPage;
        private Long pageSize;
        private String categoryId;
        private String bookName;
        private String bookPlaceNum;
        private String bookAuther;
        private String username;
        private Integer borrowStatus;

        @Override
        public String toString() {
            return "ListParm{" +
                    "currentPage=" + currentPage +
                    ", pageSize=" + pageSize +
                    ", categoryId='" + categoryId + '\'' +
                    ", bookName='" + bookName + '\'' +
                    ", bookPlaceNum='" + bookPlaceNum + '\'' +
                    ", bookAuther='" + bookAuther + '\'' +
                    ", username='" + username + '\'' +
                    ", borrowStatus=" + borrowStatus +
                    '}';
        }
    }
}
