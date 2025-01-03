package org.lavender.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordParm {
    private Long userId;
    private String oldPassword;
    private String password;

    @Override
    public String toString() {
        return "UpdatePasswordParm{" +
                "userId=" + userId +
                ", oldPassword='" + oldPassword + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}