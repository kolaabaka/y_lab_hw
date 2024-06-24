package com.banturov.events;

import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeNotFoundException;

import com.banturov.exceptions.AlreadyExistException;

/**
 * class for interaction with database
 */
public class Repository {

	/**
	 * tables in database event -> room ManyToOne foreign key event.numberRoom
	 */
	List<Event> eventList = new ArrayList<>();
	List<Room> roomList = new ArrayList<>();

	/**
	 * Show all saved halls
	 * 
	 * @return - all saved halls
	 * @throws AttributeNotFoundException - throw if roomList is empty
	 */
	public List<Room> showRoom() throws AttributeNotFoundException {
		if (roomList.isEmpty()) {
			throw new AttributeNotFoundException("No one room created");
		}
		return roomList;
	}

	/**
	 * Show all saved events
	 * 
	 * @return - all saved events
	 * @throws AttributeNotFoundException - throw if eventList is empty
	 */
	public List<Event> showEvent() throws AttributeNotFoundException {
		if (eventList.isEmpty()) {
			throw new AttributeNotFoundException("No one event created");
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
	 * @throws AttributeNotFoundException - throw if select room is not exist
	 * @throws IllegalArgumentException   - throw if incorrect validation
	 */
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

	/**
	 * Show all events on select date
	 * 
	 * @param date - select date
	 * @return - list of elements satisfying the condition
	 * @throws AttributeNotFoundException - throw if no elements satisfying the
	 *                                    condition
	 */
	public List<Event> filterDate(String date) throws AttributeNotFoundException {
		List<Event> takenEvents = new ArrayList<>();
		for (Event event : eventList)
			if (event.getDate().equals(date))
				takenEvents.add(event);
		if (takenEvents.isEmpty())
			throw new AttributeNotFoundException("No one events");
		return takenEvents;
	}

	/**
	 * Show all events of selected author
	 * 
	 * @param author - select author
	 * @return - list of elements satisfying the condition
	 * @throws AttributeNotFoundException - throw if no elements satisfying the
	 *                                    condition
	 */
	public List<Event> filterAuthor(String author) throws AttributeNotFoundException {
		List<Event> takenEvents = new ArrayList<>();
		for (Event event : eventList)
			if (event.getAuthor().equals(author))
				takenEvents.add(event);
		if (takenEvents.isEmpty())
			throw new AttributeNotFoundException("No one events");
		return takenEvents;
	}

	/**
	 * Show all events in selected hall
	 * 
	 * @param numberRoom - select number of hall
	 * @return - list of elements satisfying the condition
	 * @throws AttributeNotFoundException - throw if no elements satisfying the
	 *                                    condition
	 */
	public List<Event> filterNumberRoom(Long numberRoom) throws AttributeNotFoundException {
		List<Event> takenEvents = new ArrayList<>();
		for (Event event : eventList)
			if (event.getNumberRoom() == numberRoom)
				takenEvents.add(event);
		if (takenEvents.isEmpty())
			throw new AttributeNotFoundException("No one events");
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
	 * @throws AttributeNotFoundException - throw if user did not make no one event
	 */
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

	/**
	 * 
	 * @param username - unique name id
	 * @param id       - event unique id
	 * @return - deleted element
	 * @throws AccessDeniedException - throw if user try delete not his own event
	 */
	public List<Event> deleteEvent(String username, Long id) throws AccessDeniedException {
		List eventListBuf = new ArrayList<>();
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
