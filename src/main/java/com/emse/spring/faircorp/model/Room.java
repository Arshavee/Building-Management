package com.emse.spring.faircorp.model;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ROOM")
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private Integer floor;

    @Column(nullable=false)
    private String name;

    @Column
    private  Double currentTemperature;

    @Column
    private  Double targetTemperature;



    @OneToMany(mappedBy = "room")
    private Set<Heater> heaters;

    @OneToMany(mappedBy = "room")
    private Set<Window> Windows;

    @ManyToOne
    private Building building;

    public Room(){
    }
    public Room(String name, Integer floor) {
        this.floor = floor;
        this.name = name;

    }
    public Room(String name){
        this.name = name;
    }

    public Set<Window> getWindows() {
        return Windows;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Integer getFloor() {
        return floor;
    }
    public Double getCurrentTemperature(){
        return currentTemperature;
    }
    public  Double getTargetTemperature(){
        return targetTemperature;
    }

    public Set<Heater> getHeaters() {
        return heaters;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHeaters(Set<Heater> heaters) {
        this.heaters = heaters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public void setWindows(Set<Window> windows) {
        Windows = windows;
    }

    public Building getBuilding() {    return building; }

    public void setBuilding(Building building) {   this.building = building;}


}


