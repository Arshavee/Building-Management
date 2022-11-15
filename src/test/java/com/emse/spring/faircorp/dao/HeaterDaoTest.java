package com.emse.spring.faircorp.dao;


import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.Room;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class HeaterDaoTest {
    @Autowired
    private HeaterDao heaterDao;
    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindAHeater() {
        Heater heater = heaterDao.getReferenceById(-10L);
        Assertions.assertThat(heater.getName()).isEqualTo("Heater1");
        Assertions.assertThat(heater.getHeaterStatus()).isEqualTo(HeaterStatus.ON);
    }

    @Test
    public void shouldFindAOnHeater() {
        List<Heater> result = (List<Heater>) heaterDao.getHeatersByHeaterStatus(HeaterStatus.valueOf("ON"));
        Assertions.assertThat(result.get(0).getId())
                .isEqualTo(-10L);
          }

    @Test
    public void shouldNotFindAOnHeater() {
        List<Heater> result = (List<Heater>) heaterDao.getHeatersByHeaterStatus(HeaterStatus.OFF);
        Assertions.assertThat(result)
                .isEmpty();
    }

    @Test
    public void deleteHeatersByRooms() {
        Room room = roomDao.getReferenceById(-9L);
        List<Long> ids = room.getHeaters().stream().map(Heater::getId).collect(Collectors.toList());
        Assertions.assertThat(ids.size()).isEqualTo(2);

        heaterDao.deleteAllHeatersByRoomId(-9L);
        List<Heater> result= heaterDao.findAllById(ids);
        Assertions.assertThat(result).isEmpty();

    }

}