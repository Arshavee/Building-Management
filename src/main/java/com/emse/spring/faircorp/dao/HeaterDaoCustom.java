package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Window;

import java.util.List;

public interface HeaterDaoCustom {
    List<Heater> findRoomOnHeater(Long id);
    List<Heater> findAllHeatersByRoomName(String name);
    List<Heater> fineAllHeatersByStatusON(Long id);
}
