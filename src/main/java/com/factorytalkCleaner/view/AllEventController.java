package com.factorytalkCleaner.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factorytalkCleaner.repository.AllEventRepository;

@RestController
@RequestMapping("/eventos")
public class AllEventController {

	@Autowired
	private AllEventRepository repository;

	@GetMapping
	public String listar() {
		long inicio = System.currentTimeMillis();
		repository.show();
		long fim = System.currentTimeMillis();
		return "Tempo: " + (fim - inicio) + " ms";
	}
}
