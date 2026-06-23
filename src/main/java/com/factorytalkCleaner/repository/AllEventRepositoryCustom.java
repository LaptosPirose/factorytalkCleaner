package com.factorytalkCleaner.repository;

import java.util.List;

import com.factorytalkCleaner.entity.AllEvent;

public interface AllEventRepositoryCustom {

	List<AllEvent> show();

	int deleteTopBad(int quantidade);

	int deleteTopGood(int quantidade);

	/**
	 * Remove um lote limitado de alarmes antigos utilizando dicas de bloqueio por
	 * linha (ROWLOCK) para evitar o escalonamento de bloqueio (Table Lock) no SQL
	 * Server.
	 *
	 * @param dataLimite Alarmes com EventTimeStamp anterior a esta data serão
	 *                   removidos.
	 * @param batchSize  Quantidade máxima de registos a apagar nesta execução.
	 * @return O número de linhas efetivamente eliminadas.
	 */
	int deletarAlarmesAntigosEmLote(int batchSize);

}