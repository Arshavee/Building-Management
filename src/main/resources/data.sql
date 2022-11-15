INSERT INTO Building(id,name,address,floor) values (-2,'ecm','15 rue gambetta saint etienne',5);
INSERT INTO Building(id,name,address,TEMP_OUT,floor) values (-6,'rda','67 rue gambetta saint etienne',23,4);

INSERT INTO ROOM(id, name, floor, current_temperature, target_temperature,building_id) VALUES(-10, 'Room1', 1, 22.3, 20.0,-2);
INSERT INTO ROOM(id, name, floor,building_id) VALUES(-9, 'Room2', 1,-2);

INSERT INTO HEATER(id, heater_status, name, power, room_id) VALUES(-10, 'ON', 'Heater1', 2000, -10);
INSERT INTO HEATER(id, heater_status, name, power, room_id) VALUES(-9, 'ON', 'Heater2', null, -10);
INSERT INTO HEATER(id, heater_status, name, power, room_id) VALUES(-8, 'ON', 'Heater3', 4533, -9);
INSERT INTO HEATER(id, heater_status, name, power, room_id) VALUES(-7, 'ON', 'Heater4', 3457, -9);

INSERT INTO RWINDOW(id, window_status, name, room_id) VALUES(-10, 'CLOSE', 'Window 1', -10);
INSERT INTO RWINDOW(id, window_status, name, room_id) VALUES(-9, 'CLOSE', 'Window 2', -10);
INSERT INTO RWINDOW(id, window_status, name, room_id) VALUES(-8, 'OPEN', 'Window 3', -9);
INSERT INTO RWINDOW(id, window_status, name, room_id) VALUES(-7, 'CLOSE', 'Window 4', -9);
