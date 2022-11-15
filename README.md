# Faircorp Building Management BACKEND


[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

This application is able to manage sensors in a building.The management of the sanitary conditions (COVID-19 pandemic, pollution), user comfort, and energy efficiency, require concurrent management of window openings.
This Project is a building Management project where it has heaters, windows as a component of rooms and rooms are the component of buidings. 
The component of this project in hierarchical order is

- Buildings
- Rooms
- Heaters and Windows

## Features
This application is able to manage the building windows. It was these features,
- the building has an outside temperature, and rooms
- each room has zero or more heaters, has zero or more windows, a name, a floor, a current temperature, a target temperature.
- each heater has a name, an on or off status, and consumption of power.
- each window has a name, an a status open or closed

## Tech

The technologies used to build this project are

- [Spring Boot] - open source Java-based framework used to create a micro Service
- [Gradle] - Gradle is a build automation tool for multi-language software development
- [JPQL] - Java Persistence Query Language
- [DAO] - Data Access Object
- [JPA] - Jakarta Persistence is a Jakarta EE application programming interface specification that describes the management of relational data in enterprise Java applications
-[REST] - HTTP requests are handled by the methods of a REST service
- [DTO] - Data transfer object
- [JUnit Test] - A JUnit test is a method contained in a class which is only used for     testing. And make the unit tests




## Installation

To run this application run FaircorpApplication. Unit tests can be run with the help of gradle > verification > test 
## APIs

Dillinger is currently extended with the following plugins.
Instructions on how to use them in your own application are linked below.

| Module | End Point | Use |
| ------ | ------ | ------ |
| RoomController | /api/rooms[GET]|findAll|
| RoomController | /api/rooms[POST]|create |
| RoomController | /api/rooms/switchHeater/{id}[PUT] |switchStatusHeater|
| RoomController | /api/rooms/switchWindow/{id}[PUT] |switchStatusWindow|
| RoomController | /api/rooms/{id}[GET] |findById|
| RoomController | /api/rooms/{id}[DELETE] |delete|
| WindowController | /api/windows[GET]|findAll |
| WindowController | /api/windows[POST]|create |
| WindowController | /api/windows/{id}[Get]|findById |
| WindowController | /api/windows/{id}[DELETE]|delete |
| WindowController | /api/windows/{id}/switch[PUT]|switchStatus |
| securityController | /secure[GET] |findUserName|
| HeaterController | /api/heaters[GET]|findAll |
| HeaterController | /api/heaters[POST]|create |
| HeaterController | /api/heaters/{id}[Get]|findById |
| HeaterController | /api/heaters/{id}[DELETE]|delete |
| HeaterController | /api/heaters/{id}/switch[PUT]|switchStatus |
| BuildingController | /api/buildings [GET]|findAll |
| BuildingController | /api/buildings[POST]|create |
| BuildingController | /api/buildings/{id}[Get]|findById |
| BuildingController | /api/buildings/{id}[DELETE]|delete |
