package org.lavender.library.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 路由需要的数据格式
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouterVO {

    private String path;

    private String component;

    private boolean alwaysShow;

    private String name;

    private Meta meta;

    @Override
    public String toString() {
        return "RouterVO{" +
                "path='" + path + '\'' +
                ", component='" + component + '\'' +
                ", alwaysShow=" + alwaysShow +
                ", name='" + name + '\'' +
                ", meta=" + meta +
                ", children=" + children +
                '}';
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Meta {
        private String title;
        private String icon;
        private Object[] roles;

        @Override
        public String toString() {
            return "Meta{" +
                    "title='" + title + '\'' +
                    ", icon='" + icon + '\'' +
                    ", roles=" + Arrays.toString(roles) +
                    '}';
        }
    }

    private List<RouterVO> children = new ArrayList<>();

}