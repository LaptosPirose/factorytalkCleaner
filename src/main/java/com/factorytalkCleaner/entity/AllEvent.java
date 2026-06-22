package com.factorytalkCleaner.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AllEvent", schema = "dbo")
public class AllEvent {

	@Id
	@Column(name = "EventID")
	private UUID eventID;

	@Column(name = "SourceName")
	private String sourceName;

	@Column(name = "Message")
	private String message;

	@Column(name = "Active")
	private Boolean active;

	@Column(name = "EventTimeStamp")
	private LocalDateTime eventTimeStamp;

	// GETTERS E SETTERS

	public UUID getEventID() {
		return eventID;
	}

	public void setEventID(UUID eventID) {
		this.eventID = eventID;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDateTime getEventTimeStamp() {
		return eventTimeStamp;
	}

	public void setEventTimeStamp(LocalDateTime eventTimeStamp) {
		this.eventTimeStamp = eventTimeStamp;
	}
}