package com.factorytalkCleaner.view;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factorytalkCleaner.entity.AllEvent;
import com.factorytalkCleaner.service.AlarmCleanupService;

@RestController
@RequestMapping("/exibicao")
public class AllEventController {

	private final AlarmCleanupService alarmCleanupService;

	public AllEventController(AlarmCleanupService alarmCleanupService) {
		this.alarmCleanupService = alarmCleanupService;
	}

	@GetMapping("/eventos")
	public List<AllEvent> obterEventos() {
		return alarmCleanupService.listarAmostragem();
	}
}