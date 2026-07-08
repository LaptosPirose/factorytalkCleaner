package com.factorytalkCleaner.oee.view;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factorytalkCleaner.oee.service.OeeProcessingService;

@RestController
@RequestMapping("/oee/deltas")
public class DeltaAllEventController {

	private final OeeProcessingService oeeProcessingService;

	public DeltaAllEventController(OeeProcessingService oeeProcessingService) {
		this.oeeProcessingService = oeeProcessingService;
	}

	@PostMapping("/processar")
	public String dispararProcessamento() {
		int totalProcessado = oeeProcessingService.processarDeltasOee();
		return "Processamento de OEE executado. Total de períodos de paradas (deltas) computados: " + totalProcessado;
	}
}