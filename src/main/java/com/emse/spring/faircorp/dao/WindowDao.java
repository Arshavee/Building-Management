package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WindowDao extends JpaRepository<Window, Long>, WindowDaoCustom {

    Window getReferenceById(Long id); // (1).

    @Query("select c from Window c where c.name=:name")  // (2)
    Window findByName(@Param("name") String name);

    @Modifying // (3)
    @Query("delete from Window c where c.name = ?1")
    void deleteByName(String name);

    @Modifying
    @Query("delete  from Window c where c.room.id= ?1")
    void deleteAllWindowsByRoomId(Long id);



}