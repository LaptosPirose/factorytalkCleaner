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
		// sintaxe
		if (mensagensAlvo == null || mensagensAlvo.isEmpty()) {
			return 0;
		}

		// StringBuilder para construir as cláusulas: (Message LIKE :msg0 OR Message
		// LIKE :msg1 ...)
		StringBuilder whereClause = new StringBuilder("(");
		for (int i = 0; i < mensagensAlvo.size(); i++) {
			whereClause.append("Message LIKE :msg").append(i);
			if (i < mensagensAlvo.size() - 1) {
				whereClause.append(" OR ");
			}
		}
		whereClause.append(")");

		// Monta a Query final dinâmica usando as cláusulas LIKE montadas acima
		String sql = "DELETE TOP (:batchSize) FROM Alarmes.dbo.AllEvent WITH (ROWLOCK) WHERE " + whereClause.toString();

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("batchSize", batchSize);

		// Vincula cada parâmetro da lista com seu respectivo índice (:msg0, :msg1...)
		for (int i = 0; i < mensagensAlvo.size(); i++) {
			query.setParameter("msg" + i, mensagensAlvo.get(i).trim());
		}

		return query.executeUpdate();
	}
}