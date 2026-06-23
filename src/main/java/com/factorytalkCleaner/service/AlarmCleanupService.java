package com.factorytalkCleaner.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.factorytalkCleaner.entity.AllEvent;
import com.factorytalkCleaner.repository.AllEventRepository;

@Service
public class AlarmCleanupService {

	// Logger profissional para monitoramento no terminal do servidor
	private static final Logger log = LoggerFactory.getLogger(AlarmCleanupService.class);

	private final AllEventRepository repository;

	// O Spring Boot injeta a lista inteira separada por vírgula direto aqui!
	@Value("${factorytalk.cleaner.target-messages}")
	private List<String> mensagensAlvo;

	// Injeção de dependência moderna por Construtor
	public AlarmCleanupService(AllEventRepository repository) {
		this.repository = repository;
	}

	/**
	 * Método principal que coordena a deleção em lotes de forma controlada e
	 * segura. * @param batchSize Quantidade máxima de linhas a serem apagadas por
	 * transação (ex: 5000).
	 * 
	 * @return JSON estruturado em String com o resumo da higienização efetuada.
	 */
	public String executarLimpezaCompleta(int batchSize) {
		log.info("Iniciando rotina de higienização controlada em lote para alarmes alvo configurados...");

		int totalRegistrosApagados = 0;
		int linhasApagadasNoLote;

		do {
			// Executa a query utilizando ROWLOCK e repassando a lista injetada do
			// properties
			linhasApagadasNoLote = repository.deletarAlarmesAntigosEmLote(batchSize, mensagensAlvo);
			totalRegistrosApagados += linhasApagadasNoLote;

			if (linhasApagadasNoLote > 0) {
				log.info("Lote concluído: {} registros apagados. Acumulado nesta sessão: {}", linhasApagadasNoLote,
						totalRegistrosApagados);

				// Pequena pausa estratégica (100ms) para aliviar o processador e o I/O do SQL
				// Server
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					log.error("A rotina de limpeza em segundo plano foi interrompida abruptamente.", e);
					break;
				}
			}
		} while (linhasApagadasNoLote > 0);

		log.info("Higienização concluída! Foram removidos um total de {} registros de ruído da base.",
				totalRegistrosApagados);

		return """
				{
					"status": "Sucesso",
					"mensagem": "Limpeza total de alarmes configurados concluída com sucesso.",
					"tamanho_do_lote_utilizado": %d,
					"total_linhas_removidas": %d
				}
				""".formatted(batchSize, totalRegistrosApagados);
	}

	/**
	 * AGENDAMENTO AUTOMÁTICO (@Scheduled) Executa todos os dias pontualmente às
	 * 03:00 da madrugada.
	 */
	@Scheduled(cron = "0 0 3 * * ?")
	public void agendamentoAutomaticoMadrugada() {
		int tamanhoDoLoteSeguro = 5000;
		log.info("Agendamento acionado automaticamente via CRON às 03:00.");
		executarLimpezaCompleta(tamanhoDoLoteSeguro);
	}

	public String analisar() {
		long totalRegistrosEncontrados = 0;
		// StringBuilder armazena o texto dinamicamente para construir o relatório do
		// navegador
		StringBuilder relatorio = new StringBuilder();

		relatorio.append("--- RELATÓRIO DE ANÁLISE DE ALARMES ---\n\n");

		for (String msg : mensagensAlvo) {
			String padraoRefinado = msg.trim();

			long contagemMensagem = repository.countByMessageLike(padraoRefinado);
			totalRegistrosEncontrados += contagemMensagem;

			// Printa no log (mantém a saúde do terminal) e monta o relatório do navegador
			System.out.println("Padrão [" + padraoRefinado + "]: Encontrados " + contagemMensagem + " registros.");
			relatorio.append("Padrão [").append(padraoRefinado).append("]: Encontrados ").append(contagemMensagem)
					.append(" registros.\n");
		}

		relatorio.append("\n=========================================\n");
		relatorio.append("Total geral elegível para limpeza: ").append(totalRegistrosEncontrados).append(" registros.");

		// Retorna a string prontinha para quem chamou o método
		return relatorio.toString();
	}

	public List<AllEvent> listarAmostragem() {
		log.info("Buscando amostragem dinâmica usando Pageable...");

		// Página 0, com tamanho limite de 50.000 registros
		Pageable paginacao = PageRequest.of(0, 50000);

		return repository.show(paginacao);
	}
}