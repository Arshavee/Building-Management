package com.emse.spring.faircorp.dao;


import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;
import com.emse.spring.faircorp.model.WindowStatus;
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
class WindowDaoTest {
    @Autowired
    private WindowDao windowDao;

    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindAWindow() {
        Window window = windowDao.getReferenceById(-10L);
        Assertions.assertThat(window.getName()).isEqualTo("Window 1");
        Assertions.assertThat(window.getWindowStatus()).isEqualTo(WindowStatus.CLOSE);
    }
    @Test
    public void shouldFindAWindowByName() {
        Window window = windowDao.findByName("Window 1");
        Assertions.assertThat(window.getId()).isEqualTo(-10L);
    }

    @Test
    public void shouldFindRoomOpenWindows() {
        List<Window> result = windowDao.findRoomOpenWindows(-9L);
        Assertions.assertThat(result)
                .hasSize(1)
                .extracting("id", "windowStatus")
                .containsExactly(Tuple.tuple(-8L, WindowStatus.OPEN));
    }

    @Test
    public void shouldFindRoomOpenWindowsbyRoomname() {
        List<Window> result = windowDao.findAllWindowsByRoomName("Room1");
        Assertions.assertThat(result)
                .hasSize(2);
                }

    @Test
    public void shouldNotFindRoomOpenWindows() {
        List<Window> result = windowDao.findRoomOpenWindows(-10L);
        Assertions.assertThat(result).isEmpty();
    }
    @Test
    public void shouldDeleteWindowsRoom() {
        Room room = roomDao.getReferenceById(-10L);
        List<Long> roomIds = room.getWindows().stream().map(Window::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds.size()).isEqualTo(2);

        windowDao.deleteAllWindowsByRoomId(-10L);
        List<Window> result = windowDao.findAllById (roomIds);
        Assertions.assertThat(result).isEmpty();

    }
    @Test
    public void deleteByName() {

        windowDao.deleteByName("Window 1");
        List<Window> result= (List<Window>) windowDao.findByName("Window 1");
        Assertions.assertThat(result).isNull();

    }


}