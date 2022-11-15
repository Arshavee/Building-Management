package com.emse.spring.faircorp.dao;
import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.Room;
//import com.emse.spring.faircorp.model.RoomDao;
import com.emse.spring.faircorp.model.Window;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RoomDaoTest {
    @Autowired
    private RoomDao roomDao;

    @Autowired
    private HeaterDao heaterDao;
    @Autowired
    private WindowDao windowDao;


    @Test
    public void shouldFindARoom() {
        Room room = roomDao.getReferenceById(-10L);
        Assertions.assertThat(room.getName()).isEqualTo("Room1");
    }

//    @Test
//    public void shouldNOTFindARoom() {
//        Room room = roomDao.getReferenceById(-1000L);
//        List<Room> result = (List<Room>) roomDao.getReferenceById(-1000L);
//        Assertions.assertThat(result).isEmpty();
//    }


    @Test
    public void shouldFindARoomByName() {
        Room room = roomDao.findByName("Room1");
        Assertions.assertThat(room.getId()).isEqualTo(-10L);


    }

    @Test
    public void shouldFindAWindowByName() {
        Room room = roomDao.findRoombyName("Room1");
        Assertions.assertThat(room.getId()).isEqualTo(-10L);


    }
    @Test
    public void GetHeaterOfARoom() {
        List<Heater> result = (List<Heater>) roomDao.getHeater(-10L);
        Assertions.assertThat(result.size()).isEqualTo(2);

    }
    @Test
    public void GetWindowsOfARoomName() {
        List<Window> result = roomDao.getWindow(-10L);
        Assertions.assertThat(result.size()).isEqualTo(2);

    }
    @Test
    public void DeleteAllRooms() {
        Room room = roomDao.getReferenceById(-10L);
        List<Long> roomIds = room.getWindows().stream().map(Window::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds.size()).isEqualTo(2);

        heaterDao.deleteAllHeatersByRoomId(-10L);
        List<Heater> result= heaterDao.findAllById(roomIds);
        Assertions.assertThat(result).isEmpty();


        windowDao.deleteAllWindowsByRoomId(-10L);
        List<Window> res = windowDao.findAllById (roomIds);
        Assertions.assertThat(res).isEmpty();

       roomDao.deleteAllRooms(-10L);
        Assertions.assertThat(result).isEmpty();

    }
    }




