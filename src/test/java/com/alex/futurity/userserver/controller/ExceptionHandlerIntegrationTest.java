package com.alex.futurity.userserver.controller;

import com.alex.futurity.userserver.dto.SingUpRequestDto;
import com.alex.futurity.userserver.exception.CannotUploadFileException;
import com.alex.futurity.userserver.exception.ErrorMessage;
import com.alex.futurity.userserver.service.AuthService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExceptionHandlerIntegrationTest extends AuthConfigurator {
    @MockBean
    private AuthService authService;

    private static final MockMultipartFile VALID_AVATAR =
            new MockMultipartFile("avatar", "user.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[1]);

    @Test
    @DisplayName("SingUp: Should return 500 INTERNAL SERVER ERROR if the avatar cannot be read")
    @SneakyThrows
    void testSingUpIfAvatarCannotBeRead() {
        String message = "The avatar cannot be read";
        SingUpRequestDto dto = new SingUpRequestDto("alex@jpeg.com", "alex", "alexRoot", null);

        when(authService.singUp(any())).thenThrow(new CannotUploadFileException(message));

        mockMvc.perform(multipart("/singup")
                        .file(VALID_AVATAR)
                        .part(buildUserPart(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorMessage(message))));
    }

    @SneakyThrows
    protected <T> MockPart buildUserPart(T dto) {
        MockPart mockPart = new MockPart("user", objectMapper.writeValueAsBytes(dto));
        mockPart.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return mockPart;
    }
}
