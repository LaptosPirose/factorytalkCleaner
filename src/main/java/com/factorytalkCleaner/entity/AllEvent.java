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

	@Column(name = "EventType")
	private Integer eventType;

	@Column(name = "SourceName")
	private String sourceName;

	@Column(name = "Message")
	private String message;

	@Column(name = "Severity")
	private Integer severity;

	@Column(name = "Active")
	private Boolean active;

	@Column(name = "Acked")
	private Boolean acked;

	@Column(name = "EventTimeStamp")
	private LocalDateTime eventTimeStamp;

	// GETTERS E SETTERS

	public UUID getEventID() {
		return eventID;
	}

	public void setEventID(UUID eventID) {
		this.eventID = eventID;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
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

	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getAcked() {
		return acked;
	}

	public void setAcked(Boolean acked) {
		this.acked = acked;
	}

	public LocalDateTime getEventTimeStamp() {
		return eventTimeStamp;
	}

	public void setEventTimeStamp(LocalDateTime eventTimeStamp) {
		this.eventTimeStamp = eventTimeStamp;
	}
}