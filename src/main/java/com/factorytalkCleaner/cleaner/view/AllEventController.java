package com.factorytalkCleaner.cleaner.view;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factorytalkCleaner.cleaner.entity.AllEvent;
import com.factorytalkCleaner.cleaner.service.AlarmCleanupService;

@RestController
@RequestMapping("/show")
public class AllEventController {

	private final AlarmCleanupService alarmCleanupService;

	public AllEventController(AlarmCleanupService alarmCleanupService) {
		this.alarmCleanupService = alarmCleanupService;
	}

	@GetMapping("/events")
	public List<AllEvent> obterEventos() {
		return alarmCleanupService.listarAmostragem();
	}
}