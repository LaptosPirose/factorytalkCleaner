package com.factorytalkCleaner.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.factorytalkCleaner.service.AlarmCleanupService;

@RestController
public class AlarmCleanupController {

	@Autowired
	private AlarmCleanupService service;

	@GetMapping("/cleanup/teste")
	public String teste() {
		return service.analisar();
	}

	@GetMapping("/cleanup/apagar10")
	public String apagar10() {
		return service.apagar10();
	}

	@GetMapping("/cleanup/apagar/{quantidade}")
	public String apagar(@PathVariable int quantidade) {
		return service.apagar(quantidade);
	}

}