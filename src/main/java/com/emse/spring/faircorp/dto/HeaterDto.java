package com.emse.spring.faircorp.dto;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;


public class HeaterDto {
    private Long id;

    private String name;

    private Long power;

    private Long roomid;

    private HeaterStatus heaterStatus;

    public HeaterDto(){ }

    public HeaterDto(Heater heater) {
        this.id = heater.getId();
        this.name = heater.getName();
        this.heaterStatus = heater.getHeaterStatus();
        this.roomid = heater.getRoom().getId();
        this.power =heater.getPower();

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HeaterStatus getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus (HeaterStatus heaterStatus) {
        this.heaterStatus =heaterStatus;
    }


    public Long getRoomid(){return roomid;}

    public void setRoomid(){this.roomid=roomid;}

    public Long getPower(){return power;}

    public void setPower(){this.power=power;}



}