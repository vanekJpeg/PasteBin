package ru.vanek.pastebin.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.vanek.pastebin.dto.PasteDTO;
import ru.vanek.pastebin.services.PasteService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PasteControllerTest {
    @Mock
    private PasteService pasteService;
    @InjectMocks
    private PasteController pasteController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    private void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(pasteController).build();
        objectMapper=new ObjectMapper();
    }
    @Test
    void getPastes() throws Exception {
        Date date = new Date();
        List<PasteDTO> allUsers = new ArrayList<>();
        allUsers.add(new PasteDTO("name1","text1",date));
        allUsers.add(new PasteDTO("name2","text2",date));
        allUsers.add(new PasteDTO("name3","text3",date));
        allUsers.add(new PasteDTO("name4","text4",date));
        String json= objectMapper.writeValueAsString(allUsers);
        when(pasteService.findAll(1)).thenReturn(allUsers);
        mockMvc.perform(MockMvcRequestBuilders.get("/pastes?page=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(json));
        verify(pasteService, times(1)).findAll(1);
    }

    @Test
    void show_ShouldReturnCorrectJsonOnce() throws Exception {
        Date date = new Date();
        PasteDTO pasteDTO = new PasteDTO("name","text",date);
        when(pasteService.findOne(1)).thenReturn(pasteDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/pastes/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.text").value("text"))
                .andExpect(jsonPath("$.expirationAt").value(date));
        verify(pasteService, times(1)).findOne(1);
    }

    @Test
    void create() {
    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }
}