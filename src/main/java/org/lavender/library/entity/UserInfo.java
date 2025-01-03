package org.lavender.library.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String name;
    private String avatar;
    private String introduction;
    private Object[] roles;

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", introduction='" + introduction + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
