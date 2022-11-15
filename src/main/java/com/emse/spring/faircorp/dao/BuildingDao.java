package com.emse.spring.faircorp.dao;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BuildingDao extends JpaRepository<Building, Long>,BuildingDaoCustom  {
      Building getReferenceById(Long id);
      @Query("select b from Building b where b.name=:name")  // (2)
      Building findByName(@Param("name") String name);
      @Modifying
      @Query("delete from Building b where b.id = ?1")
      void deleteByid(Long id);

}
