package com.factorytalkCleaner.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.factorytalkCleaner.entity.AllEvent;
import com.factorytalkCleaner.repository.AllEventRepository;

@Service
public class AlarmCleanupService {

	private static final String QUALITY_BAD = "Alarm fault: Alarm input quality is bad";
	private static final String QUALITY_GOOD = "Alarm fault cleared: Alarm input quality is good";

	// Criamos o Logger profissional para monitorizar as limpezas no terminal
	private static final Logger log = LoggerFactory.getLogger(AlarmCleanupService.class);

	private final AllEventRepository repository;

	public AlarmCleanupService(AllEventRepository repository) {
		this.repository = repository;
	}

	public List<AllEvent> listarAmostragem() {
		return repository.show();
	}

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

	/**
	 * Método principal que executa a deleção em lotes de forma controlada. Pode ser
	 * disparado manualmente pelo Controller ou automaticamente pelo @Scheduled.
	 * 
	 * @param batchSize Quantidade de linhas por lote (ex: 5000).
	 * @return O total de registros apagados acumulados.
	 */
	public String executarLimpezaCompleta(int batchSize) {
		log.info("Iniciando rotina de higienização TOTAL de alarmes de Quality...");

		int totalRegistrosApagados = 0;
		int linhasApagadasNoLote;

		do {
			linhasApagadasNoLote = repository.deletarAlarmesAntigosEmLote(batchSize);
			totalRegistrosApagados += linhasApagadasNoLote;

			if (linhasApagadasNoLote > 0) {
				log.info("Lote concluído: {} registros de qualidade apagados. Acumulado: {}", linhasApagadasNoLote,
						totalRegistrosApagados);

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					log.error("A rotina de limpeza foi interrompida.", e);
					break;
				}
			}
		} while (linhasApagadasNoLote > 0);

		log.info("Limpeza total concluída! Foram removidos {} registros de ruído.", totalRegistrosApagados);

		return """
					"status": "Sucesso",
					"mensagem": "Limpeza total de alarmes de qualidade concluída via Service.",
					"tamanho_do_lote_utilizado": %d,
					"total_linhas_removidas": %d
				""".formatted(batchSize, totalRegistrosApagados);
	}

	/**
	 * AGENDAMENTO AUTOMÁTICO (@Scheduled) * Configuração usando expressão CRON para
	 * rodar de forma 100% invisível na madrugada. Exemplo abaixo: Executa todos os
	 * dias às 03:00 da manhã. * Importante: Para que este agendamento funcione,
	 * certifique-se de adicionar a anotação @EnableScheduling na classe principal
	 * (FactorytalkCleanerApplication.java).
	 */
	@Scheduled(cron = "0 0 3 * * ?")
	public void agendamentoAutomaticoMadrugada() {
		int tamanhoDoLoteSeguro = 5000;
		log.info("Cron disparado automaticamente na madrugada para limpeza total.");
		executarLimpezaCompleta(tamanhoDoLoteSeguro);
	}

}