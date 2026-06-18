package com.factorytalkCleaner.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@GetMapping("")
	public String sayHello() {
		return "<h1> Hello First__!</h1>";
	}
}
