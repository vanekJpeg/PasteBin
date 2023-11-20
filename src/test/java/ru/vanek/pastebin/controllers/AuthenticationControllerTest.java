package ru.vanek.pastebin.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.vanek.pastebin.dto.JwtRequest;
import ru.vanek.pastebin.dto.JwtResponse;
import ru.vanek.pastebin.services.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Mock
    AuthService authService;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    AuthenticationController authenticationController;

    MockMvc mockMvc;
    @BeforeEach
    void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(authenticationController).build();
        objectMapper= new ObjectMapper();
    }
    @Test
    public void createAuthToken_shouldReturnString() throws Exception {

    }
    @Test
    public void createNewUser() {
    }
}