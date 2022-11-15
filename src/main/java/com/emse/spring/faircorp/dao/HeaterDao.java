package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.Window;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.testng.annotations.Test;

import java.util.List;
public interface HeaterDao extends JpaRepository<Heater, Long> {

 Heater getReferenceById(Long id);
//   @Query("select c from Heater c where c.name=:name")
//    Heater findByName(@Param("name") String name);
//    @Modifying // (3)
//    @Query("delete from Heater c where c.name = ?1")
//    void deleteByName(String name);
    @Modifying
    @Query("delete  from Heater c where c.room.id= ?1")
    void deleteAllHeatersByRoomId(Long id);

    @Query("select h from Heater h where h.heaterStatus=:heaterStatus")
    List<Heater> getHeatersByHeaterStatus (HeaterStatus heaterStatus);
    @Query("select a from Heater a where a.room=:id")
    List<Heater> getHeatersByroom (Long id);

}