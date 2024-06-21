package com.banturov.events;

import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeNotFoundException;

import com.banturov.exceptions.AlreadyExistException;

public class Repository {

	List<Event> eventList = new ArrayList<>();
	List<Room> roomList = new ArrayList<>();

	public void showRoom() throws AttributeNotFoundException {
		if (roomList.isEmpty()) {
			throw new AttributeNotFoundException("No one room created");
		}
		System.out.println(roomList);
	}

	public void showEvent() throws AttributeNotFoundException {
		if (eventList.isEmpty()) {
			throw new AttributeNotFoundException("No one event created");
		}
		System.out.println(eventList);
	}

	public void addRoom(Long roomNumber) throws AlreadyExistException {
		for (Room room : roomList) {
			if (room.getRoomNumber() == roomNumber)
				throw new AlreadyExistException("Room already exist");
		}
		if (!(roomNumber instanceof Long) || roomNumber < 0) {
			throw new IllegalArgumentException("Room number can be digit more 0");
		}
		roomList.add(new Room(roomNumber));
	}

	public void addEvent(Long id, String date, String author, Long numberRoom) throws AttributeNotFoundException {
		Long buferRoomNumber = -1L;
		for (Room room : roomList) {
			if (room.getRoomNumber() == numberRoom)
				buferRoomNumber = numberRoom;
		}
		if (buferRoomNumber == -1L) {
			throw new AttributeNotFoundException("No room with id " + numberRoom);
		}
		eventList.add(new Event(id, date, author, numberRoom));
	}

}
