package com.factorytalkCleaner.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class AllEventRepositoryImpl implements AllEventRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public int deleteTopBad(int quantidade) {

		String sql = """
				DELETE TOP (%d)
				FROM Alarmes.dbo.AllEvent
				WHERE Message =
				'Alarm fault: Alarm input quality is bad'
				""".formatted(quantidade);

		return entityManager.createNativeQuery(sql).executeUpdate();
	}

	@Override
	@Transactional
	public int deleteTopGood(int quantidade) {

		String sql = """
				DELETE TOP (%d)
				FROM Alarmes.dbo.AllEvent
				WHERE Message =
				'Alarm fault cleared: Alarm input quality is good'
				""".formatted(quantidade);

		return entityManager.createNativeQuery(sql).executeUpdate();
	}
}