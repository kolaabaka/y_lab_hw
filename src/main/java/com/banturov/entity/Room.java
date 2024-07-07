package com.banturov.entity;

import java.util.Objects;

public class Room {

	private Long id;
	private Long roomNumber;

	public Room() {
	}

	/**
	 * 
	 * @param roomNumber -unique hall id
	 */
	public Room(Long roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, roomNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		return Objects.equals(id, other.id) && Objects.equals(roomNumber, other.roomNumber);
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomNumber=" + roomNumber + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
