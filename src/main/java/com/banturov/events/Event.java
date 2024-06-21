package com.banturov.events;

import java.util.Objects;

public class Event {

	private Long id;
	private String date;
	private String author;
	private Long numberRoom;

	public Event(Long id, String date, String author, Long numberRoom) {
		this.id = id;
		this.date = date;
		this.author = author;
		this.numberRoom = numberRoom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getNumberRoom() {
		return numberRoom;
	}

	public void setNumberRoom(Long numberRoom) {
		this.numberRoom = numberRoom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, date, id, numberRoom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(author, other.author) && Objects.equals(date, other.date) && Objects.equals(id, other.id)
				&& Objects.equals(numberRoom, other.numberRoom);
	}

	@Override
	public String toString() {
		return "||Event " + id + ", date=" + date + ", author=" + author + ", numberRoom=" + numberRoom + "||\n";
	}

}
