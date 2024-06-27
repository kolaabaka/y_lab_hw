package com.banturov.repository;

import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.banturov.entity.Event;
import com.banturov.entity.Room;
import com.banturov.exceptions.AlreadyExistException;
import com.banturov.exceptions.EntityNotExistException;

/**
 * class for interaction with database
 */
public class EventRepository {

	/**
	 * tables in database event -> room ManyToOne foreign key event.numberRoom
	 */
	Set<Event> eventList = new HashSet<>();
	Set<Room> roomList = new HashSet<>();

	/**
	 * Show all saved halls
	 * 
	 * @return - all saved halls
	 * @throws EntityNotExistException - throw if roomList is empty
	 */
	public Set<Room> showRoom() throws EntityNotExistException {
		if (roomList.isEmpty()) {
			throw new EntityNotExistException("No one room created");
		}
		return roomList;
	}

	/**
	 * Show all saved events
	 * 
	 * @return - all saved events
	 * @throws EntityNotExistException - throw if eventList is empty
	 */
	public Set<Event> showEvent() throws EntityNotExistException {
		if (eventList.isEmpty()) {
			throw new EntityNotExistException("No one event created");
		}
		return eventList;
	}

	/**
	 * Add room in storage eventList
	 * 
	 * @param roomNumber - the number of the hall where the event is taking place
	 * @throws AlreadyExistException    - throw if hall already exist in roomList
	 * @throws IllegalArgumentException - throw if room number is not a natural
	 *                                  number
	 */
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

	/**
	 * Add room in storage roomList
	 * 
	 * @param id           - event unique id
	 * @param date         - date of event
	 * @param timeInterval - time interval in day
	 * @param author       - user id user.name
	 * @param numberRoom   - number room in roomList
	 * @param format       - accepts the date format 'dd-mm-yyyy'
	 * @throws EntityNotExistException  - throw if select room is not exist
	 * @throws IllegalArgumentException - throw if incorrect validation
	 */
	public void addEvent(Long id, String date, Long timeInterval, String author, Long numberRoom,
			SimpleDateFormat format) throws IllegalArgumentException, EntityNotExistException {
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
			throw new EntityNotExistException("No room with id " + numberRoom);
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

	/**
	 * Show all events on select date
	 * 
	 * @param date - select date
	 * @return - list of elements satisfying the condition
	 * @throws EntityNotExistException - throw if no elements satisfying the
	 *                                 condition
	 */
	public List<Event> filterDate(String date) throws EntityNotExistException {
		List<Event> takenEvents = new ArrayList<>();
		for (Event event : eventList)
			if (event.getDate().equals(date))
				takenEvents.add(event);
		if (takenEvents.isEmpty())
			throw new EntityNotExistException("No one events");
		return takenEvents;
	}

	/**
	 * Show all events of selected author
	 * 
	 * @param author - select author
	 * @return - list of elements satisfying the condition
	 * @throws EntityNotExistException - throw if no elements satisfying the
	 *                                 condition
	 */
	public List<Event> filterAuthor(String author) throws EntityNotExistException {
		List<Event> takenEvents = new ArrayList<>();
		for (Event event : eventList)
			if (event.getAuthor().equals(author))
				takenEvents.add(event);
		if (takenEvents.isEmpty())
			throw new EntityNotExistException("No one events");
		return takenEvents;
	}

	/**
	 * Show all events in selected hall
	 * 
	 * @param numberRoom - select number of hall
	 * @return - list of elements satisfying the condition
	 * @throws EntityNotExistException - throw if no elements satisfying the
	 *                                 condition
	 */
	public List<Event> filterNumberRoom(Long numberRoom) throws EntityNotExistException {
		List<Event> takenEvents = new ArrayList<>();
		for (Event event : eventList)
			if (event.getNumberRoom() == numberRoom)
				takenEvents.add(event);
		if (takenEvents.isEmpty())
			throw new EntityNotExistException("No one events");
		return takenEvents;
	}

	/**
	 * 
	 * @param id           - event unique id
	 * @param date         - date of event
	 * @param timeInterval - time interval in day
	 * @param author       - user id user.name
	 * @param numberRoom   - number room in roomList
	 * @param format       - accepts the date format 'dd-mm-yyyy'
	 * @return - true if update was successful
	 * @throws EntityNotExistException - throw if user did not make no one event
	 */
	public boolean updateEvent(Long id, String date, Long timeInterval, String author, Long numberRoom,
			SimpleDateFormat format) throws EntityNotExistException {
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
			throw new EntityNotExistException("No room with id " + numberRoom);
		}

		for (Event event : eventList)
			if (event.getId() == id && event.getAuthor() == author) {
				event.setDate(date);
				event.setTimeInterval(timeInterval);
				event.setNumberRoom(numberRoom);
				return true;
			}
		throw new EntityNotExistException("No one your events");

	}

	/**
	 * 
	 * @param username - unique name id
	 * @param id       - event unique id
	 * @return - deleted element
	 * @throws AccessDeniedException - throw if user try delete not his own event
	 */
	public List<Event> deleteEvent(String username, Long id) throws AccessDeniedException {
		List<Event> eventListBuf = new ArrayList<>();
		for (Event event : eventList) {
			if (event.getId() == id) {
				if (!event.getAuthor().equals(username)) {
					throw new AccessDeniedException("Access denied, you are not author");
				} else {
					eventListBuf.add(event);
				}
			}
		}
		eventList.removeAll(eventListBuf);
		return eventListBuf;
	}

}
