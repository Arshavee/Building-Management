package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.model.*;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HeaterController.class)
class HeaterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HeaterDao heaterDao;
    @MockBean
    private RoomDao roomDao;



    private Heater createHeater(String name) {
        Room room = new Room("Room1");
        return new Heater(name,HeaterStatus.ON,room);
    }
    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadHeaters() throws Exception {
        given(heaterDao.findAll()).willReturn(List.of(
                createHeater("Heater1"),
                createHeater("Heater2")
        ));

        mockMvc.perform(get("/api/heaters")
                        .accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("Heater1", "Heater2")));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadAHeaterAndReturnNullIfNotFound() throws Exception {
        given(heaterDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/heaters/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadAHeater() throws Exception {
        given(heaterDao.findById(999L)).willReturn(Optional.of(createHeater("Heater1")));

        mockMvc.perform(get("/api/heaters/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("$.name").value("Heater1"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldSwitchHeater() throws Exception {
        Heater expectedHeater = createHeater("Heater1");
        Assertions.assertThat(expectedHeater.getHeaterStatus()).isEqualTo(HeaterStatus.ON);
        expectedHeater.setHeaterStatus(HeaterStatus.OFF);
        String json = objectMapper.writeValueAsString(new HeaterDto(expectedHeater));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedHeater.getRoom());
        given(heaterDao.getReferenceById(anyLong())).willReturn(expectedHeater);

        mockMvc.perform(post("/api/heaters").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Heater1"))
                .andExpect(jsonPath("$.heater_status").value("OFF"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldUpdateHeater() throws Exception {
        Heater expectedHeater = createHeater("Heater1");
        expectedHeater.setId(1L);
        String json = objectMapper.writeValueAsString(new HeaterDto(expectedHeater));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedHeater.getRoom());
        given(heaterDao.getReferenceById(anyLong())).willReturn(expectedHeater);

        mockMvc.perform(post("/api/heaters").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Heater1"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldCreateHeater() throws Exception {
        Heater expectedHeater = createHeater("Heater1");
        String json = objectMapper.writeValueAsString(new HeaterDto(expectedHeater));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedHeater.getRoom());
        given(heaterDao.getReferenceById(anyLong())).willReturn(expectedHeater);

        mockMvc.perform(post("/api/heaters").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Heater1"))
                .andExpect(jsonPath("$.id").value("0"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldDeleteHeater() throws Exception {
        mockMvc.perform(delete("/api/heaters/999").with(csrf()))
                .andExpect(status().isOk());
    }


}