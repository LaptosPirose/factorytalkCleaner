package com.factorytalkCleaner.cleaner.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.factorytalkCleaner.cleaner.entity.AllEvent;

public interface AllEventRepository extends JpaRepository<AllEvent, UUID>, AllEventRepositoryCustom {

	long countByMessageLike(String mensagens);
}