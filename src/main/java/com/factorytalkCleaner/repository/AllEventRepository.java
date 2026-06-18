package com.factorytalkCleaner.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.factorytalkCleaner.entity.AllEvent;

import jakarta.transaction.Transactional;

public interface AllEventRepository extends JpaRepository<AllEvent, UUID>, AllEventRepositoryCustom {

	@Query(value = """
			SELECT TOP 21000
			    EventID,
			    EventType,
			    SourceName,
			    Message,
			    Severity,
			    Active,
			    Acked,
			    EventTimeStamp
			FROM Alarmes.dbo.AllEvent
			""", nativeQuery = true)
	List<AllEvent> show();

	long countByMessage(String message);

	@Modifying
	@Transactional
	@Query(value = """
			DELETE TOP (10)
			FROM Alarmes.dbo.AllEvent
			WHERE Message =
			    'Alarm fault: Alarm input quality is bad'
			""", nativeQuery = true)
	int deleteTop10Bad();

	@Modifying
	@Transactional
	@Query(value = """
			DELETE TOP (10)
			FROM Alarmes.dbo.AllEvent
			WHERE Message =
			    'Alarm fault cleared: Alarm input quality is good'
			""", nativeQuery = true)
	int deleteTop10Good();
}