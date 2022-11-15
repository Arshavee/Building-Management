package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

public class BuildingDaoCustomImplementation implements BuildingDaoCustom {

    @PersistenceContext
    private EntityManager em;

//    @Override
//    public Room findRoom(Long id) {
//        String jpsql = "Select r from Room r WHERE r.building.id= :id";
//        try {
//            return em.createQuery(jpsql, Room.class)
//                    .setParameter("id", id)
//                    .getSingleResult();
//        } catch (NoResultException nre) {
//            return null;
//        }
//    }


}
