package com.factorytalkCleaner.view;

import org.springframework.web.bind.annotation.GetMapping;
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
	public String dispararAnalise() {
		// Recebe o relatório detalhado do Service
		String resultadoAnalise = alarmCleanupService.analisar();

		// A tag <pre> faz o navegador respeitar o espaçamento e os \n do texto do
		// relatório
		return "<pre>" + resultadoAnalise + "</pre>";
	}

	@GetMapping("/executar")
	public String dispararLimpezaManual(@RequestParam(defaultValue = "5000") int lote) {
		// Apenas repassa a String gerada e devolvida pelo Service
		return alarmCleanupService.executarLimpezaCompleta(lote);
	}
}
