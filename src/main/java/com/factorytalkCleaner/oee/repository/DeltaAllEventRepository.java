package com.factorytalkCleaner.oee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.factorytalkCleaner.oee.entity.DeltaAllEvent;

@Repository
public interface DeltaAllEventRepository extends JpaRepository<DeltaAllEvent, UUID> {
	// Como estamos gravando dados processados, os métodos padrão do JpaRepository
	// (.save, .findAll) bastam por enquanto
}