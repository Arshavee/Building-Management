package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;

import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

public class RoomDaoCustomImpl implements RoomDaoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Room findRoombyName(String name) {
        String jpsql = "Select r from Room r WHERE r.name= :name";
        try {
            return em.createQuery(jpsql, Room.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Window> getWindow(Long id) {
        String jpsql = "Select w from Window w WHERE w.room.id= :id";
        try {
            return em.createQuery(jpsql, Window.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Heater> getHeater(Long id) {
        String jpsql = "Select h from Heater h WHERE h.room.id= :id";
        try {
            return em.createQuery(jpsql, Heater.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}