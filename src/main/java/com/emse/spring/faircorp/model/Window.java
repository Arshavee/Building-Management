package com.emse.spring.faircorp.model;
import com.emse.spring.faircorp.dto.WindowDto;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.util.List;

@Entity // (1)
@Table(name = "RWINDOW") // (2)
public class Window {
    @Id// (3)
    @GeneratedValue
    private Long id;

    @Column(nullable=false)// (4)
    private String name;

    @Enumerated (EnumType.STRING) //AS the status is OPEN and CLOSE
    @Column(nullable=false)// (5)
    private WindowStatus windowStatus;

    @ManyToOne
    private Room room;



    public Window() {
    }

    public Window(String name, WindowStatus windowstatus,Room room) {
        this.windowStatus = windowstatus;
        this.name = name;
        this.room=room;
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

    public WindowStatus getWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(WindowStatus windowStatus) {
        this.windowStatus = windowStatus;
    }

    public Room getRoom() {
        return room;
    }
}