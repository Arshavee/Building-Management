package com.emse.spring.faircorp.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Building")
public class Building {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;


    @Column(nullable = true)
    private Long tempOut;

    @Column(nullable = true)
    private Integer floor;
    @OneToMany(mappedBy = "building")
    private Set<Room> Rooms;

    public Building() {
    }

    public Building(String name) {
        this.name = name;
    }

    public Building(Long id,String name, String address) {
        this.id =  id;
        this.name = name;
        this.address = address;
    }
    public Building(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Set<Room> getRooms() {
        return Rooms;
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

    public void setId(Long id) {
        this.id = id;
    }




}