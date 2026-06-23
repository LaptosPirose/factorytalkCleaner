package com.factorytalkCleaner.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.factorytalkCleaner.entity.AllEvent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class AllEventRepositoryImpl implements AllEventRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<AllEvent> show(Pageable pageable) {
		String sql = "SELECT * FROM Alarmes.dbo.AllEvent ORDER BY Alarmes.dbo.AllEvent.EventTimeStamp DESC";
		Query query = entityManager.createNativeQuery(sql, AllEvent.class);

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		return query.getResultList();
	}

	/**
	 * Limpeza performática em lote usando a lista dinâmica do
	 * application.properties. O uso do WITH (ROWLOCK) evita travar a tabela inteira
	 * (Table Lock) na produção da fábrica.
	 */
	@Override
	@Transactional
	public int deletarAlarmesAntigosEmLote(int batchSize, List<String> mensagensAlvo) {
		// Proteção: Se a lista vier vazia ou nula, cancela para não gerar erro de
		// sintaxe no SQL
		if (mensagensAlvo == null || mensagensAlvo.isEmpty()) {
			return 0;
		}

		// Query unificada e dinâmica utilizando o operador IN
		String sql = "DELETE TOP (:batchSize) FROM Alarmes.dbo.AllEvent WITH (ROWLOCK) WHERE Message IN (:mensagensAlvo)";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("batchSize", batchSize);
		query.setParameter("mensagensAlvo", mensagensAlvo);

		return query.executeUpdate();
	}
}