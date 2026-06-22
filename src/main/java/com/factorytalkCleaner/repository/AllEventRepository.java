package com.factorytalkCleaner.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.factorytalkCleaner.entity.AllEvent;

public interface AllEventRepository extends JpaRepository<AllEvent, UUID>, AllEventRepositoryCustom {

	long countByMessage(String message);
}