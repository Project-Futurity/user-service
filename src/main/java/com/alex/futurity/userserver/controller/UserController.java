package com.alex.futurity.userserver.controller;

import com.alex.futurity.userserver.dto.UserInfo;
import com.alex.futurity.userserver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping(value = "/avatar", produces = {
            MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE
    })
    public ResponseEntity<Resource> getAvatar(@RequestHeader("user_id") long id) {
        return ResponseEntity.ok(service.findUserAvatar(id));
    }

    @GetMapping("/user")
    public UserInfo getUserInfo(@RequestHeader("user_id") Long id) {
        return service.findById(id);
    }
}
