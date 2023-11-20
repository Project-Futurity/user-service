package com.alex.futurity.userserver.message.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UserUpdateEvent {
    @NonNull
    private Long userId;
}
