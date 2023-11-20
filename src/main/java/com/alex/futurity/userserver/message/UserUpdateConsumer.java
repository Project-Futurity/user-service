package com.alex.futurity.userserver.message;

import com.alex.futurity.userserver.message.model.UserUpdateEvent;
import com.alex.futurity.userserver.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@AllArgsConstructor
@Component("userUpdateConsumer")
public class UserUpdateConsumer implements Consumer<UserUpdateEvent> {
    private final UserService userService;

    @Override
    public void accept(UserUpdateEvent event) {
        log.info("Got telegram update for user={}", event.getUserId());
        userService.addUserTelegram(event.getUserId());
    }
}
