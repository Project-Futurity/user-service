package com.alex.futurity.userserver.dto;

import com.alex.futurity.userserver.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private Long id;
    private boolean hasTelegram;

    public static UserInfo fromUser(User user) {
        return UserInfo.builder()
                .hasTelegram(user.isHasTelegram())
                .id(user.getId())
                .build();
    }
}
