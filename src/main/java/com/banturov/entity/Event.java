package com.banturov.entity;

import java.util.Objects;

/**
 * Entity for events in halls
 */
public class Event {

	private Long id;
	private Long timeInterval;
	private String date;
	private String author;
	private Long numberRoom;

	/**
	 * 
	 * @param id           - primary key
	 * @param timeInterval - The time interval is from one to four
	 * @param date
	 * @param author       - user identity
	 * @param numberRoom   - hall identity
	 */
	public Event(Long id, Long timeInterval, String date, String author, Long numberRoom) {
		this.id = id;
		this.timeInterval = timeInterval;
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

	public Long getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(Long timeInterval) {
		this.timeInterval = timeInterval;
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
		return Objects.hash(author, date, id, numberRoom, timeInterval);
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
				&& Objects.equals(numberRoom, other.numberRoom) && Objects.equals(timeInterval, other.timeInterval);
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", timeInterval=" + timeInterval + ", date=" + date + ", author=" + author
				+ ", numberRoom=" + numberRoom + "]\n";
	}

}
