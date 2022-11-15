package com.emse.spring.faircorp.dto;

import com.emse.spring.faircorp.model.Room;

public class RoomDto {

    private Long id;

    private Integer floor;

    private String name;

    private  Double currentTemperature;

    private  Double targetTemperature;

    private Long buildingId;

    private String buildingName;

    public RoomDto(){ }

    public RoomDto(Room room) {
        this.id=room.getId();
        this.name=room.getName();
        this.floor=room.getFloor();
        this.currentTemperature=room.getCurrentTemperature();
        this.targetTemperature=room.getTargetTemperature();
        this.buildingId = room.getBuilding().getId();
        this.buildingName = room.getBuilding().getName();
    }

    public  Long getId(){return id;}

    public void setId(Long id) {this.id=id;}

    public String getName(){ return name;}

    public void setName(String name){ this.name=name;}

    public Integer getFloor(){return floor;}

    public void setFloor(){this.floor=floor;}

    public Double getCurrentTemperature(){ return currentTemperature;}

    public void setCurrentTemperature(){this.currentTemperature=currentTemperature;}

    public Double getTargetTemperature(){ return targetTemperature;}

    public void setTargetTemperature(){this.targetTemperature=targetTemperature;}

    public Long getBuildingId(){  return buildingId;}

    public void setBuildingId(Long buildingId) {this.buildingId = buildingId;}

    public String getBuildingName() {  return buildingName; }

    public void setBuildingName(String buildingName) {this.buildingName = buildingName;}


}

