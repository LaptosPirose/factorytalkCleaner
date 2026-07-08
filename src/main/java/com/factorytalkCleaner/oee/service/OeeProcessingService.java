package com.factorytalkCleaner.oee.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.factorytalkCleaner.cleaner.entity.AllEvent;
import com.factorytalkCleaner.cleaner.repository.AllEventRepository;
import com.factorytalkCleaner.oee.entity.DeltaAllEvent;
import com.factorytalkCleaner.oee.repository.DeltaAllEventRepository;

@Service
public class OeeProcessingService {

	private static final Logger logger = LoggerFactory.getLogger(OeeProcessingService.class);

	private final AllEventRepository allEventRepository;
	private final DeltaAllEventRepository deltaAllEventRepository;

	// Injeção de dependência limpa por construtor unificando os dois repositórios
	public OeeProcessingService(AllEventRepository allEventRepository,
			DeltaAllEventRepository deltaAllEventRepository) {
		this.allEventRepository = allEventRepository;
		this.deltaAllEventRepository = deltaAllEventRepository;
	}

	/**
	 * Processa os dados brutos da fábrica e gera o histórico de tempos de parada
	 * (Deltas).
	 */
	@Transactional
	public int processarDeltasOee() {
		logger.info("Iniciando reconstrução de linha do tempo para OEE...");

		// Busca os eventos brutos ordenados cronologicamente para garantir o pareamento
		// correto
		List<AllEvent> eventosBrutos = allEventRepository.findAll();

		if (eventosBrutos.isEmpty()) {
			logger.warn("Nenhum registro bruto encontrado na tabela AllEvent para processamento.");
			return 0;
		}

		Map<String, AllEvent> alarmesAbertosMap = new HashMap<>();
		List<DeltaAllEvent> deltasParaSalvar = new ArrayList<>();

		for (AllEvent evento : eventosBrutos) {
			// Chave única para correlacionar o par abertura/fechamento do mesmo dispositivo
			String chaveAlarme = evento.getSourceName() + "_" + evento.getMessage();

			if (Boolean.TRUE.equals(evento.getActive())) {
				// Se ACTIVE = true, o alarme iniciou/maquina parou. Guardamos o estado inicial
				// na memória.
				alarmesAbertosMap.put(chaveAlarme, evento);
			} else if (Boolean.FALSE.equals(evento.getActive())) {
				// Se ACTIVE = false, o alarme limpou. Buscamos quando ele abriu para fechar a
				// janela de tempo.
				AllEvent eventoAbertura = alarmesAbertosMap.remove(chaveAlarme);

				if (eventoAbertura != null) {
					long segundosDuracao = Duration
							.between(eventoAbertura.getEventTimeStamp(), evento.getEventTimeStamp()).toSeconds();

					DeltaAllEvent delta = new DeltaAllEvent(eventoAbertura.getEventId(), eventoAbertura.getSourceName(),
							eventoAbertura.getMessage(), eventoAbertura.getEventTimeStamp(), evento.getEventTimeStamp(),
							segundosDuracao);

					deltasParaSalvar.add(delta);
				}
			}
		}

		// Salva todos os deltas industriais calculados no banco de dados em lote
		if (!deltasParaSalvar.isEmpty()) {
			deltaAllEventRepository.saveAll(deltasParaSalvar);
			logger.info("Processamento finalizado. {} deltas de paradas gerados com sucesso!", deltasParaSalvar.size());
		}

		return deltasParaSalvar.size();
	}
}