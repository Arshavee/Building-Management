package com.emse.spring.faircorp.model;
import javax.persistence.*;
@Entity
@Table(name = "HEATER")
public class Heater {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=true)
    private Long power;

    @ManyToOne
    //@Column(nullable=false)
    private Room room;
    @Enumerated (EnumType.STRING) // As it is ON and OFF
    @Column(nullable=false)
    private HeaterStatus heaterStatus;


    public Heater() {
    }

    public Heater(String name,  HeaterStatus heaterStatus,Room room) {
        this.heaterStatus = heaterStatus;
        this.name = name;
        this.room = room;
       // this.power=power;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeaterStatus getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus(HeaterStatus heaterStatus) {
        this.heaterStatus = heaterStatus;
    }


    public Long getPower() {
        return power;
    }

    public void setPower() {
        this.power=power;}

    public Room getRoom() {
        return room;
    }

    public void setRoom(){
        this.room=room;
    }




}