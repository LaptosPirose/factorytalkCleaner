package com.factorytalkCleaner.cleaner.repository;

import java.util.List;

import org.springframework.data.domain.Pageable; // Certifique-se deste import

import com.factorytalkCleaner.cleaner.entity.AllEvent;

public interface AllEventRepositoryCustom {

	List<AllEvent> show(Pageable pageable);

	int deletarAlarmesAntigosEmLote(int batchSize, List<String> mensagensAlvo);
}