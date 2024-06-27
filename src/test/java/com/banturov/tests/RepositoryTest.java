package com.banturov.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

import com.banturov.entity.Event;
import com.banturov.entity.Room;
import com.banturov.exceptions.AlreadyExistException;
import com.banturov.exceptions.EntityNotExistException;
import com.banturov.repository.EventRepository;
import com.banturov.repository.UserRepository;

@DisplayName("EventRepository test")
@TestInstance(Lifecycle.PER_CLASS)
public class RepositoryTest {

	Room room;
	EventRepository repo;
	Event event;
	SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);

	@BeforeAll
	void init() {
		repo = new EventRepository();
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
		void testAddNewRoom() throws IllegalArgumentException, AlreadyExistException, EntityNotExistException {

			assertThrows(IllegalArgumentException.class, () -> repo.addRoom(-1L), "Error, added invalid number room");

			room = new Room(11L);
			Set<Room> roomList1 = new HashSet<>();
			roomList1.add(room);

			repo.addRoom(11L);
			assertThatThrownBy(() -> repo.addRoom(11L)).isInstanceOf(AlreadyExistException.class)
					.withFailMessage("Room already exist");
			Set<Room> roomList2 = new HashSet<>();
			roomList2 = repo.showRoom();
			assertThat(roomList1.size()).isEqualTo(roomList1.size()).withFailMessage("Error, wrong size listRoom");
			assertThat(roomList1.contains(room)).isEqualTo(roomList2.contains(room))
					.withFailMessage("Error, rooms are not equals");
		}

		@Tag("Add")
		@Test
		void testAddNewEvent() throws IllegalArgumentException, AlreadyExistException, EntityNotExistException {
			repo = new EventRepository();
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
			assertThrows(EntityNotExistException.class,
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
		void testFilterDate() throws IllegalArgumentException, AlreadyExistException, EntityNotExistException {
			repo = new EventRepository();
			repo.addRoom(1L);
			repo.addRoom(2L);
			repo.addEvent(1L, "01-01-2001", 1L, "Kola", 1L, format);
			repo.addEvent(2L, "01-01-2001", 2L, "Kola", 1L, format);
			repo.addEvent(3L, "02-01-2001", 1L, "Buka", 2L, format);
			repo.addEvent(4L, "03-01-2001", 1L, "Tuka", 2L, format);
			assertThat(repo.filterDate("01-01-2001").size()).isEqualTo(2)
					.withFailMessage("The filter should have returned two values");
			assertThat(repo.filterDate("02-01-2001").size()).isEqualTo(1)
					.withFailMessage("The filter should have returned one values");
		}

		@Tag("Filter")
		@Test
		void testFilterAuthor() throws EntityNotExistException, IllegalArgumentException, AlreadyExistException {
			repo = new EventRepository();
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
		void testFilterNumberRoom() throws EntityNotExistException, IllegalArgumentException, AlreadyExistException {
			repo = new EventRepository();
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
	public void testUpdate() throws IllegalArgumentException, AlreadyExistException, EntityNotExistException {
		repo = new EventRepository();
		repo.addRoom(1L);
		repo.addEvent(1L, "01-01-2001", 1L, "Kola", 1L, format);
		assertThrows(IllegalArgumentException.class, () -> repo.updateEvent(-1L, "11-11-2024", 4L, "Kola", 10L, format),
				"Error, wrong id event lees than 0");
		assertThrows(IllegalArgumentException.class, () -> repo.updateEvent(1L, "11-11-2024", 10L, "Kola", 10L, format),
				"Error, wrong tome interval more than 4");
		assertThrows(IllegalArgumentException.class, () -> repo.updateEvent(1L, "11-11-2024", 0L, "Kola", 10L, format),
				"Error, wrong tome interval less than 1");
		assertThrows(IllegalArgumentException.class, () -> repo.updateEvent(1L, "11-2024", 2L, "Kola", 10L, format),
				"Error, invalid date format");
		assertThrows(EntityNotExistException.class, () -> repo.updateEvent(1L, "11-11-2024", 2L, "Kola", 2000L, format),
				"Error, room is  not exist");

	}

	@Test
	public void testDelete()
			throws IllegalArgumentException, AlreadyExistException, EntityNotExistException, AccessDeniedException {
		repo = new EventRepository();
		repo.addRoom(1L);
		repo.addRoom(2L);
		repo.addEvent(1L, "01-01-2001", 1L, "Kola", 1L, format);
		repo.addEvent(2L, "01-01-2001", 2L, "Kola", 1L, format);
		repo.addEvent(3L, "02-01-2001", 1L, "Buka", 2L, format);
		repo.addEvent(4L, "03-01-2001", 1L, "Tuka", 2L, format);
		assertThatThrownBy(() -> repo.deleteEvent("Kuka", 1L)).isInstanceOf(AccessDeniedException.class)
				.withFailMessage("Error, delete not yours event");
		assertThat(repo.deleteEvent("Kola", 1L).contains(new Event(1L, 1L, "01-01-2001", "Kola", 1L))).isTrue()
				.withFailMessage("Must return object Event");
	}

	@Test
	public void testUserRepository() throws AlreadyExistException {

		UserRepository repoUser = Mockito.mock(UserRepository.class);

		repoUser.addUser("Pumba", "122333");
		repoUser.addUser("Simba", "333221");
		Mockito.verify(repoUser).addUser("Pumba", "122333");
		Mockito.verify(repoUser).addUser("Simba", "333221");
		Mockito.when(repoUser.checkUserExist("Admin", "admin")).thenReturn(true);
		assertThat(repoUser.checkUserExist("Admin", "admin")).isTrue();
	}

}
