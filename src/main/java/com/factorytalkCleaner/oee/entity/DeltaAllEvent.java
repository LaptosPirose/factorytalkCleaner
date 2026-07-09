package com.factorytalkCleaner.oee.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "DeltaAllEvent", schema = "dbo")
public class DeltaAllEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "OriginalEventID")
	private UUID originalEventID;

	@Column(name = "SourceName", nullable = false)
	private String sourceName;

	@Column(name = "Message", nullable = false, length = 1024)
	private String message;

	@Column(name = "StartTime", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "EndTime")
	private LocalDateTime endTime;

	@Column(name = "DurationSeconds")
	private Long durationSeconds;

	@Column(name = "Category", length = 100)
	private String category;

	public DeltaAllEvent() {
	}

	public DeltaAllEvent(UUID originalEventID, String sourceName, String message, LocalDateTime startTime,
			LocalDateTime endTime, Long durationSeconds) {
		this.originalEventID = originalEventID;
		this.sourceName = sourceName;
		this.message = message;
		this.startTime = startTime;
		this.endTime = endTime;
		this.durationSeconds = durationSeconds;
		this.category = "UNCATEGORIZED";
	}

	// Getters e Setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getOriginalEventID() {
		return originalEventID;
	}

	public void setOriginalEventID(UUID originalEventID) {
		this.originalEventID = originalEventID;
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

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Long getDurationSeconds() {
		return durationSeconds;
	}

	public void setDurationSeconds(Long durationSeconds) {
		this.durationSeconds = durationSeconds;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}