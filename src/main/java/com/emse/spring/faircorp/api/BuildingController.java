package com.emse.spring.faircorp.api;


import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Room;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {

    private final BuildingDao buildingDao;
    private final RoomDao roomDao;
    private final HeaterDao heaterDao;
    private final WindowDao windowDao;

    public BuildingController(BuildingDao buildingDao, RoomDao roomDao, HeaterDao heaterDao, WindowDao windowDao) {
        this.buildingDao = buildingDao;
        this.heaterDao = heaterDao;
        this.roomDao = roomDao;
        this.windowDao = windowDao;

    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }
    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null); // (7)
    }

    ///api/rooms (POST) add or update a room
    @PostMapping // (8)
    public BuildingDto create(@RequestBody Building dto) {

        Building building = null;
        // On creation id is not defined
        if (dto.getId() == null) {
            Long id = 0L;
            if (!buildingDao.findAll().isEmpty()){
                id = buildingDao.findAll().stream().max(Comparator.comparing(building1 -> building1.getId())).get().getId();
            }
            building = buildingDao.save(new Building(id+1,dto.getName(), dto.getAddress()));
        } else {
            building = buildingDao.getReferenceById(dto.getId());  // (9)
            building.setName(dto.getName());
            building.setAddress(dto.getAddress());
            building.setFloor(dto.getFloor());
        }
        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        Building building = buildingDao.getReferenceById(id);
        if (building!=null) {
            for (Room r : building.getRooms()) {
                windowDao.deleteAllWindowsByRoomId(r.getId());
                heaterDao.deleteAllHeatersByRoomId(r.getId());
                roomDao.deleteById(r.getId());
            }
            buildingDao.deleteById(id);
        }

    }
}