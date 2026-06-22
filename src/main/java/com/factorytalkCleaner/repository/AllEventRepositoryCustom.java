package com.factorytalkCleaner.repository;

import java.util.List;

import com.factorytalkCleaner.entity.AllEvent;

public interface AllEventRepositoryCustom {

	List<AllEvent> show();

	int deleteTopBad(int quantidade);

	int deleteTopGood(int quantidade);

}