package com.factorytalkCleaner.repository;

import java.util.List;
import com.factorytalkCleaner.entity.AllEvent;
import org.springframework.data.domain.Pageable; // Certifique-se deste import

public interface AllEventRepositoryCustom {

	List<AllEvent> show(Pageable pageable);

	int deletarAlarmesAntigosEmLote(int batchSize, List<String> mensagensAlvo);
}