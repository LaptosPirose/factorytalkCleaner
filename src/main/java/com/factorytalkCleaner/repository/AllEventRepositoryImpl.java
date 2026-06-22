package com.factorytalkCleaner.repository;

import java.util.List;

import com.factorytalkCleaner.entity.AllEvent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class AllEventRepositoryImpl implements AllEventRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String QUALITY_BAD = "Alarm fault: Alarm input quality is bad";
	private static final String QUALITY_GOOD = "Alarm fault cleared: Alarm input quality is good";

	@Override
	@SuppressWarnings("unchecked")
	public List<AllEvent> show() {
		String sql = "SELECT * FROM Alarmes.dbo.AllEvent";
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
}