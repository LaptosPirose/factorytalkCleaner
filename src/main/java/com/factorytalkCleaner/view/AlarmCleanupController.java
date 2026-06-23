package com.factorytalkCleaner.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	/**
	 * Endpoint manual para disparar a limpeza em lotes via HTTP. Exemplo de
	 * chamada: DELETE http://localhost:8085/limpeza/executar?dias=30&lote=5000
	 */
	@GetMapping("/executar")
	public String dispararLimpezaManual(@RequestParam(defaultValue = "5000") int lote) {
		// Apenas repassa a String gerada e devolvida pelo Service
		return alarmCleanupService.executarLimpezaCompleta(lote);
	}
}
