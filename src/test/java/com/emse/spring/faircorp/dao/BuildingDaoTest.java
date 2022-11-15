package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest

class BuildingDaoTest {

    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private WindowDao windowDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private HeaterDao heaterDao;

    @Test
    public void shouldFindABuilding() {
        Building building = buildingDao.getReferenceById(-2L);
        Assertions.assertThat(building.getName()).isEqualTo("ecm");
    }

    @Test
    public void shouldFindABuildingByName() {
        Building building = buildingDao.findByName("ecm");
        Assertions.assertThat(building.getId()).isEqualTo(-2L);
    }
    @Test
    public void shouldDeleteWindowsRoomBuilding() {
        Room room = roomDao.getReferenceById(-10L);
        List<Long> roomIds = room.getWindows().stream().map(Window::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds.size()).isEqualTo(2);

        windowDao.deleteAllWindowsByRoomId(-10L);
        List<Window> result = windowDao.findAllById (roomIds);
        Assertions.assertThat(result).isEmpty();

    }
   }
