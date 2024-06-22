package com.banturov.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.management.AttributeNotFoundException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.banturov.events.Event;
import com.banturov.events.Repository;
import com.banturov.events.Room;
import com.banturov.exceptions.AlreadyExistException;

@DisplayName("Repository test")
@TestInstance(Lifecycle.PER_CLASS)
public class RepositoryTest {

	Room room;
	Repository repo;
	Event event;
	SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);

	@BeforeAll
	void init() {
		repo = new Repository();
		room = new Room();
	}

	@AfterAll
	static void finaliz() {
		System.out.println("Tests finished");
	}

	@Nested
	@Tag("Add")
	class testAdd {
		@Test
		@Tag("Add")
		void testAddNewRoom() throws IllegalArgumentException, AlreadyExistException, AttributeNotFoundException {

			assertThrows(IllegalArgumentException.class, () -> repo.addRoom(-1L), "Error, added invalid number room");

			room = new Room(11L);
			List<Room> roomList1 = new ArrayList<>();
			roomList1.add(room);

			repo.addRoom(11L);
			assertThrows(AlreadyExistException.class, () -> repo.addRoom(11L), "Error, room alredy exist");
			List<Room> roomList2 = new ArrayList<>();
			roomList2 = repo.showRoom();
			assertEquals(roomList1.size(), roomList1.size(), "Error, wrong size listRoom");
			assertTrue(roomList1.contains(room) == roomList2.contains(room), "Error, rooms are not equals");
		}

		@Tag("Add")
		@Test
		void testAddNewEvent() throws AttributeNotFoundException, IllegalArgumentException, AlreadyExistException {
			repo = new Repository();
			repo.addRoom(10L);
			repo.addEvent(1L, "22-11-2024", 1L, "Kola", 10L, format);
			assertTrue(
					repo.showEvent().size() == 1
							&& repo.showEvent().contains(new Event(1L, 1L, "22-11-2024", "Kola", 10L)),
					"Error, method addEvent doesn`t work");
			assertThrows(IllegalArgumentException.class,
					() -> repo.addEvent(-1L, "11-11-2024", 4L, "Kola", 10L, format),
					"Error, wrong id event lees than 0");
			assertThrows(IllegalArgumentException.class,
					() -> repo.addEvent(1L, "11-11-2024", 10L, "Kola", 10L, format),
					"Error, wrong tome interval more than 4");
			assertThrows(IllegalArgumentException.class, () -> repo.addEvent(1L, "11-11-2024", 0L, "Kola", 10L, format),
					"Error, wrong tome interval less than 1");
			assertThrows(IllegalArgumentException.class, () -> repo.addEvent(1L, "11-2024", 2L, "Kola", 10L, format),
					"Error, invalid date format");
			assertThrows(AttributeNotFoundException.class,
					() -> repo.addEvent(1L, "11-11-2024", 2L, "Kola", 2000L, format), "Error, room is  not exist");
			assertThrows(IllegalArgumentException.class, () -> repo.addEvent(1L, "11-11-2024", 2L, "Kola", 10L, format),
					"Error, id already exist");
			assertThrows(IllegalArgumentException.class, () -> repo.addEvent(2L, "22-11-2024", 1L, "Kola", 10L, format),
					"Error, hall already occupied");
		}

	}

	@Nested
	@Tag("Filter")
	class filterTest {

		@Tag("Filter")
		@Test
		void testFilterDate() throws IllegalArgumentException, AlreadyExistException, AttributeNotFoundException {
			repo = new Repository();
			repo.addRoom(1L);
			repo.addRoom(2L);
			repo.addEvent(1L, "01-01-2001", 1L, "Kola", 1L, format);
			repo.addEvent(2L, "01-01-2001", 2L, "Kola", 1L, format);
			repo.addEvent(3L, "02-01-2001", 1L, "Buka", 2L, format);
			repo.addEvent(4L, "03-01-2001", 1L, "Tuka", 2L, format);
			assertEquals(repo.filterDate("01-01-2001").size(), 2, "The filter should have returned two values");
			assertEquals(repo.filterDate("02-01-2001").size(), 1, "The filter should have returned one values");
		}

		@Tag("Filter")
		@Test
		void testFilterAuthor() throws AttributeNotFoundException, IllegalArgumentException, AlreadyExistException {
			repo = new Repository();
			repo.addRoom(1L);
			repo.addRoom(2L);
			repo.addEvent(1L, "01-01-2001", 1L, "Kola", 1L, format);
			repo.addEvent(2L, "01-01-2001", 2L, "Kola", 1L, format);
			repo.addEvent(3L, "02-01-2001", 1L, "Buka", 2L, format);
			repo.addEvent(4L, "03-01-2001", 1L, "Tuka", 2L, format);
			assertEquals(repo.filterAuthor("Kola").size(), 2, "The filter should have returned two values");
		}

		@Tag("Filter")
		@Test
		void testFilterNumberRoom() throws AttributeNotFoundException, IllegalArgumentException, AlreadyExistException {
			repo = new Repository();
			repo.addRoom(1L);
			repo.addRoom(2L);
			repo.addEvent(1L, "01-01-2001", 1L, "Kola", 1L, format);
			repo.addEvent(2L, "01-01-2001", 2L, "Kola", 1L, format);
			repo.addEvent(3L, "02-01-2001", 1L, "Buka", 2L, format);
			repo.addEvent(4L, "03-01-2001", 1L, "Tuka", 1L, format);
			assertEquals(repo.filterNumberRoom(1L).size(), 3, "The filter should have returned three values");
			assertEquals(repo.filterNumberRoom(2L).size(), 1, "The filter should have returned one values");
		}
	}
	
	@Test
	public void testUpdate() throws IllegalArgumentException, AlreadyExistException, AttributeNotFoundException {
		repo = new Repository();
		repo.addRoom(1L);
		repo.addEvent(1L, "01-01-2001", 1L, "Kola", 1L, format);	
		assertThrows(IllegalArgumentException.class,
				() -> repo.updateEvent(-1L, "11-11-2024", 4L, "Kola", 10L, format),
				"Error, wrong id event lees than 0");
		assertThrows(IllegalArgumentException.class,
				() -> repo.updateEvent(1L, "11-11-2024", 10L, "Kola", 10L, format),
				"Error, wrong tome interval more than 4");
		assertThrows(IllegalArgumentException.class, () -> repo.updateEvent(1L, "11-11-2024", 0L, "Kola", 10L, format),
				"Error, wrong tome interval less than 1");
		assertThrows(IllegalArgumentException.class, () -> repo.updateEvent(1L, "11-2024", 2L, "Kola", 10L, format),
				"Error, invalid date format");
		assertThrows(AttributeNotFoundException.class,
				() -> repo.updateEvent(1L, "11-11-2024", 2L, "Kola", 2000L, format), "Error, room is  not exist");
		
	}
	
	@Test
	public void testDelete() throws IllegalArgumentException, AlreadyExistException, AttributeNotFoundException, AccessDeniedException {
		repo = new Repository();
		repo.addRoom(1L);
		repo.addRoom(2L);
		repo.addEvent(1L, "01-01-2001", 1L, "Kola", 1L, format);
		repo.addEvent(2L, "01-01-2001", 2L, "Kola", 1L, format);
		repo.addEvent(3L, "02-01-2001", 1L, "Buka", 2L, format);
		repo.addEvent(4L, "03-01-2001", 1L, "Tuka", 2L, format);
		assertThrows( AccessDeniedException.class, ()-> repo.deleteEvent("Kuka", 1L), "Error, delete not yours event");
		assertTrue(repo.deleteEvent("Kola", 1L).contains(new Event(1L, 1L, "01-01-2001", "Kola", 1L)), "Error, repository doesn`t delete event");
	}
	

}
