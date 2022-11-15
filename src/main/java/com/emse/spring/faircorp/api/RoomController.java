package com.emse.spring.faircorp.api;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.*;
import com.emse.spring.faircorp.dao.RoomDao;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    private final RoomDao roomDao;
    private final HeaterDao heaterDao;
    private final WindowDao windowDao;

    public RoomController( RoomDao roomDao, HeaterDao heaterDao, WindowDao windowDao) {
        this.roomDao = roomDao;
        this.heaterDao=heaterDao;
        this.windowDao=windowDao;
    }
    @Secured("ROLE_ADMIN")

    @GetMapping(path = "/{id}")
    public RoomDao findById(@PathVariable Long id) {
        return (RoomDao) roomDao.findById(id).map(RoomDto::new).orElse(null); // (7)
    }
    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }
    ///api/rooms (POST) add or update a room
    @PostMapping // (8)
    public RoomDto create(@RequestBody Room dto) {

        Room room = null;
        // On creation id is not defined
        if (dto.getId() == null) {
            room = roomDao.save(new Room(dto.getName(), dto.getFloor()));
        }
        else {
            room = roomDao.getReferenceById(dto.getId());  // (9)
            room.setName(dto.getName());
            room.setCurrentTemperature(dto.getCurrentTemperature());
            room.setFloor(dto.getFloor());
        }
        return new RoomDto(room);
    }


///api/rooms/{room_id} (GET) read a room

///api/rooms/{room_id} (DELETE) delete a room and all its windows and its heaters
@DeleteMapping(path = "/{id}")
public void delete(@PathVariable Long id) {
        windowDao.deleteAllWindowsByRoomId(id);
        heaterDao.deleteAllHeatersByRoomId(id);
        roomDao.deleteById(id);
}
///api/rooms/{room_id}/switchWindow switch the room windows (OPEN to CLOSED or inverse)
@PutMapping(path = "/switchWindow/{id}")
public void switchStatusWindow(@PathVariable Long id) {
        for(Window w: roomDao.getWindow(id)){
            if(w.getWindowStatus()==WindowStatus.CLOSE){
                w.setWindowStatus(WindowStatus.OPEN);
            }
            else w.setWindowStatus(WindowStatus.CLOSE);
        }

}
    @PutMapping(path = "/switchHeater/{id}")
    public void switchStatusHeater(@PathVariable Long id) {
        for(Heater h: roomDao.getHeater(id)){
            if(h.getHeaterStatus()== HeaterStatus.OFF){
                h.setHeaterStatus(HeaterStatus.ON);
            }
            else h.setHeaterStatus(HeaterStatus.OFF);
        }

    }
///api/rooms/{room_id}/switchHeaters switch the room heaters (ON to OFF or inverse)*/


}
