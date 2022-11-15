package com.emse.spring.faircorp.dto;

import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Room;

import java.util.Set;

public class BuildingDto {
    private Long id;
    private String name;
    private String address;
    private Integer floor;

    public BuildingDto(){ }

    public BuildingDto(Building building){
        this.id=building.getId();
        this.name = building.getName();
        this.address = building.getAddress();
        this.floor=building.getFloor();
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Long getId() {
        return id;
    }

    public Integer getFloor() {
        return floor;
    }


    public void setId(long id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
