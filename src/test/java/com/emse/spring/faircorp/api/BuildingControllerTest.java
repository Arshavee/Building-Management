package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuildingController.class)
class BuildingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BuildingDao buildingDao;
    @MockBean
    private RoomDao roomDao;
    @MockBean
    private WindowDao windowDao;
    @MockBean
    private HeaterDao heaterDao;


    private Building createBuilding(String name) {
        return new Building(name,name+" newaddress");
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadBuilding() throws Exception {
        given(buildingDao.findAll()).willReturn(List.of(
                createBuilding("building 1"),
                createBuilding("building 2")
        ));

        mockMvc.perform(get("/api/buildings")
                        .accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("building 1", "building 2")));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadABuildingAndReturnNullIfNotFound() throws Exception {
        given(buildingDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/buildings/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldLoadABuilding() throws Exception {
        given(buildingDao.findById(999L)).willReturn(Optional.of(createBuilding("building 1")));

        mockMvc.perform(get("/api/buildings/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("$.name").value("building 1"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldUpdateBuilding() throws Exception {
        Building expectedbuilding = createBuilding("building 1");
        expectedbuilding.setId(1L);
        String json = objectMapper.writeValueAsString(new BuildingDto(expectedbuilding));

        given(buildingDao.getReferenceById(anyLong())).willReturn(expectedbuilding);

        mockMvc.perform(post("/api/buildings").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("building 1"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldCreateBuilding() throws Exception {
        Building expectedbuilding = createBuilding("building 1");
        String json = objectMapper.writeValueAsString(new BuildingDto(expectedbuilding));

        given(buildingDao.getReferenceById(anyLong())).willReturn(expectedbuilding);

        mockMvc.perform(post("/api/buildings").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("building 1"))
                .andExpect(jsonPath("$.id").value("0"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "adminpwd", roles = "ADMIN")
    void shouldDeleteBuilding() throws Exception {
        given(buildingDao.findById(999L)).willReturn(Optional.of(createBuilding("building 1")));
        mockMvc.perform(delete("/api/buildings/999").with(csrf()))
                .andExpect(status().isOk());
    }


}