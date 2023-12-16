package com.whatsapp.backend.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<String> HomeControllerTest() {
		return new ResponseEntity<String>("Welcome to Whatsapp Clone Web App", HttpStatus.OK);
	}

}
