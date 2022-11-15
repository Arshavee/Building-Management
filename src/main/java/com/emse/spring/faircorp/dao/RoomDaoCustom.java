package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;

import java.util.List;

public interface RoomDaoCustom {
    Room findRoombyName(String name);
    List<Window> getWindow(Long id);
    List<Heater> getHeater(Long id);
}