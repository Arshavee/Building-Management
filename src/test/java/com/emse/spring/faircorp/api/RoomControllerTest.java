package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
class RoomControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingDao buildingDao;

    @MockBean
    private HeaterDao heaterDao;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RoomDao roomDao;
    @MockBean
    private WindowDao windowDao;
    private Room createRoom(String name) {
        Building building = new Building("QFG");
        return new Room(name,1);
    }


    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadRooms() throws Exception {
        given(roomDao.findAll()).willReturn(List.of(
                createRoom("Room 1"),
                createRoom("Room 2")
        ));

        mockMvc.perform(get("/api/rooms")
                        .accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("Room 1", "Room 2")));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadARoomAndReturnNullIfNotFound() throws Exception {
        given(roomDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/rooms/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadARoom() throws Exception {
        given(roomDao.findById(999L)).willReturn(Optional.of(createRoom("Room 1")));

        mockMvc.perform(get("/api/rooms/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("$.name").value("Room 1"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldUpdateRoom() throws Exception {
        Room expectedRoom = createRoom("Room 1");
        expectedRoom.setId(1L);
        String json = objectMapper.writeValueAsString(new RoomDto(expectedRoom) {
        });

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedRoom);

        mockMvc.perform(post("/api/rooms").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Room 1"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldCreateRoom() throws Exception {
        Room expectedRoom = createRoom("Room 1");
        String json = objectMapper.writeValueAsString(new RoomDto(expectedRoom));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedRoom);

        mockMvc.perform(post("/api/rooms").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Room 1"))
                .andExpect(jsonPath("$.id").value("0"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldDeleteRoom() throws Exception {
        given(roomDao.findById(999L)).willReturn(Optional.of(createRoom("Room 1")));
        mockMvc.perform(delete("/api/rooms/999").with(csrf()))
                .andExpect(status().isOk());
    }


}