package com.factorytalkCleaner.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.factorytalkCleaner.entity.AllEvent;

public interface AllEventRepository extends JpaRepository<AllEvent, UUID>, AllEventRepositoryCustom {

	long countByMessageLike(String mensagens);
}