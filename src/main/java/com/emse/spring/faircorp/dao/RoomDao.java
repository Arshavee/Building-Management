package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomDao extends JpaRepository<Room, Long>,RoomDaoCustom {

    Room getReferenceById(Long id); // (1).
    @Query("select c from Room c where c.name=:name")  // (2)


    //Room getById(Long id);
    Room findByName(@Param("name") String name);
//    @Modifying // (3)
//    @Query("delete from Room c where c.name = ?1")
//    Room deleteByName(String name);
//
    @Modifying
   @Query("delete from Room c where c.id= ?1")
    void deleteAllRooms(Long id);
}