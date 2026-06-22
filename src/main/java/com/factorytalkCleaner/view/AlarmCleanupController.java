package com.factorytalkCleaner.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factorytalkCleaner.service.AlarmCleanupService;

@RestController
@RequestMapping("/limpeza")
public class AlarmCleanupController {

	private final AlarmCleanupService alarmCleanupService;

	public AlarmCleanupController(AlarmCleanupService alarmCleanupService) {
		this.alarmCleanupService = alarmCleanupService;
	}

	@GetMapping("/analisar")
	public String analisar() {
		return alarmCleanupService.analisar();
	}

	@GetMapping("/apagar/{quantidade}")
	public String apagar(@PathVariable int quantidade) {
		return alarmCleanupService.apagar(quantidade);
	}
}
