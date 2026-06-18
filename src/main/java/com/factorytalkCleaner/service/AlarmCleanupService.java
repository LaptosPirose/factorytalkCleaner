package com.factorytalkCleaner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.factorytalkCleaner.repository.AllEventRepository;

@Service
public class AlarmCleanupService {

	private static final String QUALITY_BAD = "Alarm fault: Alarm input quality is bad";

	private static final String QUALITY_GOOD = "Alarm fault cleared: Alarm input quality is good";

	@Autowired
	private AllEventRepository repository;

	public String analisar() {

		long bad = repository.countByMessage(QUALITY_BAD);

		long good = repository.countByMessage(QUALITY_GOOD);

		long total = bad + good;

		return """
				Resultado da análise

				BAD: %d
				GOOD: %d
				TOTAL: %d
				""".formatted(bad, good, total);
	}

	public String apagar10() {

		int badRemovidos = repository.deleteTop10Bad();
		int goodRemovidos = repository.deleteTop10Good();

		int total = badRemovidos + goodRemovidos;

		return """
				Limpeza executada

				BAD removidos: %d
				GOOD removidos: %d
				TOTAL removido: %d
				""".formatted(badRemovidos, goodRemovidos, total);
	}

	public String apagar(int quantidade) {

		int badRemovidos = repository.deleteTopBad(quantidade);

		int goodRemovidos = repository.deleteTopGood(quantidade);

		int total = badRemovidos + goodRemovidos;

		return """
				Limpeza executada

				BAD removidos: %d
				GOOD removidos: %d
				TOTAL removido: %d
				""".formatted(badRemovidos, goodRemovidos, total);
	}

}