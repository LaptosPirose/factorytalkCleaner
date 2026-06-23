package com.factorytalkCleaner.repository;

import java.util.List;

import com.factorytalkCleaner.entity.AllEvent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class AllEventRepositoryImpl implements AllEventRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String QUALITY_BAD = "Alarm fault: Alarm input quality is bad";
	private static final String QUALITY_GOOD = "Alarm fault cleared: Alarm input quality is good";

	@Override
	@SuppressWarnings("unchecked")
	public List<AllEvent> show() {
		String sql = "SELECT TOP 50000 * FROM Alarmes.dbo.AllEvent ORDER BY Alarmes.dbo.AllEvent.EventTimeStamp DESC";
		return entityManager.createNativeQuery(sql, AllEvent.class).getResultList();
	}

	@Override
	@Transactional
	public int deleteTopBad(int quantidade) {
		String sql = "DELETE TOP (:qtd) FROM Alarmes.dbo.AllEvent WHERE Message = :msg";
		return entityManager.createNativeQuery(sql).setParameter("qtd", quantidade).setParameter("msg", QUALITY_BAD)
				.executeUpdate();
	}

	@Override
	@Transactional
	public int deleteTopGood(int quantidade) {
		String sql = "DELETE TOP (:qtd) FROM Alarmes.dbo.AllEvent WHERE Message = :msg";
		return entityManager.createNativeQuery(sql).setParameter("qtd", quantidade).setParameter("msg", QUALITY_GOOD)
				.executeUpdate();
	}

	@Override
	@Transactional
	public int deletarAlarmesAntigosEmLote(int batchSize) {
		String sql = "DELETE TOP (:batchSize) FROM dbo.AllEvent WITH (ROWLOCK) " + "WHERE Message IN (:mensagensAlvo)";

		List<String> mensagensAlvo = List.of("Alarm fault: Alarm input quality is bad",
				"Alarm fault cleared: Alarm input quality is good");

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("batchSize", batchSize);
		query.setParameter("mensagensAlvo", mensagensAlvo);

		return query.executeUpdate();
	}
}