package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;
import com.emse.spring.faircorp.model.WindowStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WindowController.class)
class WindowControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WindowDao windowDao;
    @MockBean
    private RoomDao roomDao;


    private Window createWindow(String name) {
        Room room = new Room("Room 1");
        return new Window(name, WindowStatus.OPEN, room);
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadWindows() throws Exception {
        given(windowDao.findAll()).willReturn(List.of(
                createWindow("Window 1"),
                createWindow("Window 2")
        ));

        mockMvc.perform(get("/api/windows")
                        .accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("Window 1", "Window 2")));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldDeleteWindow() throws Exception {
        mockMvc.perform(delete("/api/windows/999").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadAWindowAndReturnNullIfNotFound() throws Exception {
        given(windowDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/windows/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadAWindow() throws Exception {
        given(windowDao.findById(999L)).willReturn(Optional.of(createWindow("Window 1")));

        mockMvc.perform(get("/api/windows/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("$.name").value("Window 1"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldSwitchWindow() throws Exception {
        Window expectedWindow = createWindow("Window 1");
        Assertions.assertThat(expectedWindow.getWindowStatus()).isEqualTo(WindowStatus.OPEN);
        expectedWindow.setWindowStatus(WindowStatus.CLOSE);
        String json = objectMapper.writeValueAsString(new WindowDto(expectedWindow));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedWindow.getRoom());
        given(windowDao.getReferenceById(anyLong())).willReturn(expectedWindow);

        mockMvc.perform(post("/api/windows").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Window 1"))
                .andExpect(jsonPath("$.window_status").value("CLOSED"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldUpdateWindow() throws Exception {
        Window expectedWindow = createWindow("Window 1");
        expectedWindow.setId(1L);
        String json = objectMapper.writeValueAsString(new WindowDto(expectedWindow));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedWindow.getRoom());
        given(windowDao.getReferenceById(anyLong())).willReturn(expectedWindow);

        mockMvc.perform(post("/api/windows").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Window 1"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldCreateWindow() throws Exception {
        Window expectedWindow = createWindow("Window 1");
        String json = objectMapper.writeValueAsString(new WindowDto(expectedWindow));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedWindow.getRoom());
        given(windowDao.getReferenceById(anyLong())).willReturn(expectedWindow);

        mockMvc.perform(post("/api/windows").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Window 1"))
                .andExpect(jsonPath("$.id").value("-10L"));
    }

}