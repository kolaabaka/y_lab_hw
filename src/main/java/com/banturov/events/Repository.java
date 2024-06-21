package com.banturov.events;

import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeNotFoundException;

import com.banturov.exceptions.AlreadyExistException;

public class Repository {

	List<Event> eventList = new ArrayList<>();
	List<Room> roomList = new ArrayList<>();

	public List<Room> showRoom() throws AttributeNotFoundException {
		if (roomList.isEmpty()) {
			throw new AttributeNotFoundException("No one room created");
		}
		return roomList;
	}

	public List<Event> showEvent() throws AttributeNotFoundException {
		if (eventList.isEmpty()) {
			throw new AttributeNotFoundException("No one event created");
		}
		return eventList;
	}

	public void addRoom(Long roomNumber) throws AlreadyExistException, IllegalArgumentException {
		for (Room room : roomList) {
			if (room.getRoomNumber() == roomNumber)
				throw new AlreadyExistException("Room already exist");
		}
		if (!(roomNumber instanceof Long) || roomNumber < 0) {
			throw new IllegalArgumentException("Room number can be digit more 0");
		}
		roomList.add(new Room(roomNumber));
	}

	public void addEvent(Long id, String date, Long timeInterval, String author, Long numberRoom,
			SimpleDateFormat format) throws AttributeNotFoundException, IllegalArgumentException {
		if (id < 0L) {
			throw new IllegalArgumentException("Room number can be digit more 0");
		}
		if (timeInterval < 1 || timeInterval > 4) {
			throw new IllegalArgumentException("Time interval between 1 and 4");
		}
		try {
			format.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format");
		}

		Room roomBuf = new Room(numberRoom);
		if (!roomList.contains(roomBuf)) {
			throw new AttributeNotFoundException("No room with id " + numberRoom);
		}

		for (Event event : eventList) {
			if (event.getId() == id) {
				throw new IllegalArgumentException("Id already exist");
			}
			if (event.getDate().equals(date) && event.getNumberRoom() == numberRoom
					&& event.getTimeInterval() == timeInterval) {
				throw new IllegalArgumentException("The hall already occupied");
			}
		}

		eventList.add(new Event(id, timeInterval, date, author, numberRoom));

	}

	public List<Event> filterDate(String date) throws AttributeNotFoundException {
		List<Event> takenEvents = new ArrayList<>();
		for (Event event : eventList)
			if (event.getDate().equals(date))
				takenEvents.add(event);
		if (takenEvents.isEmpty())
			throw new AttributeNotFoundException("No one events");
		return takenEvents;
	}

	public List<Event> filterAuthor(String author) throws AttributeNotFoundException {
		List<Event> takenEvents = new ArrayList<>();
		for (Event event : eventList)
			if (event.getAuthor().equals(author))
				takenEvents.add(event);
		if (takenEvents.isEmpty())
			throw new AttributeNotFoundException("No one events");
		return takenEvents;
	}

	public List<Event> filterNumberRoom(Long numberRoom) throws AttributeNotFoundException {
		List<Event> takenEvents = new ArrayList<>();
		for (Event event : eventList)
			if (event.getNumberRoom() == numberRoom)
				takenEvents.add(event);
		if (takenEvents.isEmpty())
			throw new AttributeNotFoundException("No one events");
		return takenEvents;
	}

	public boolean updateEvent(Long id, String date, Long timeInterval, String author, Long numberRoom,
			SimpleDateFormat format) throws AttributeNotFoundException {
		if (id < 0L) {
			throw new IllegalArgumentException("Room number can be digit more 0");
		}
		if (timeInterval < 1 || timeInterval > 4) {
			throw new IllegalArgumentException("Time interval between 1 and 4");
		}
		try {
			format.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format");
		}

		Room roomBuf = new Room(numberRoom);
		if (!roomList.contains(roomBuf)) {
			throw new AttributeNotFoundException("No room with id " + numberRoom);
		}

		for (Event event : eventList)
			if (event.getId() == id && event.getAuthor() == author) {
				event.setDate(date);
				event.setTimeInterval(timeInterval);
				event.setNumberRoom(numberRoom);
				return true;
			}
		throw new AttributeNotFoundException("No one your events");

	}

	public boolean deleteEvent(String username, Long id) throws AccessDeniedException {
		for (Event event : eventList) {
			if (event.getId() == id) {
				if (!event.getAuthor().equals(username)) {
					throw new AccessDeniedException("Access denied, you are not author");
				} else {
					eventList.remove(event);
				}
			}
		}

		return true;
	}

}
