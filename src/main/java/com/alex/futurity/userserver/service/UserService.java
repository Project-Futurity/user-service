package com.alex.futurity.userserver.service;

import com.alex.futurity.userserver.dto.UserInfo;
import com.alex.futurity.userserver.entity.User;
import com.alex.futurity.userserver.exception.ClientSideException;
import com.alex.futurity.userserver.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepo;


    public boolean isUserExist(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    public UserInfo findById(@NonNull Long id) {
        return userRepo.findById(id)
                .map(UserInfo::fromUser)
                .orElseThrow(() -> new IllegalStateException(String.format("Failed to found the user with id=%s", id)));
    }

    @Transactional
    public void addUserTelegram(@NonNull Long id) {
        userRepo.findById(id)
                .ifPresent(user -> user.setHasTelegram(true));
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }


    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    public Resource findUserAvatar(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ClientSideException(String.format("User with id \"%s\" is not found", id), HttpStatus.NOT_FOUND));

        return new ByteArrayResource(user.getAvatar());
    }
}
