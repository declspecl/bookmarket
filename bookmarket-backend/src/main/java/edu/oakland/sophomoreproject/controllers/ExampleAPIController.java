package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.components.SampleComponent;
import edu.oakland.sophomoreproject.controllers.model.SayHelloRequest;
import edu.oakland.sophomoreproject.controllers.model.SayHelloResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

/**
 * This is going to be deleted
 * I'm just including a quick example of a controller to show how it works
 */

/**
 * The @Controller annotation tells the API framework called Spring Boot that this class has API endpoints.
 * It will then generate code to make API endpoints based on the methods in this class and their annotations
 */

@Log4j2
@Controller
public class ExampleAPIController {
	private String sampleBean;
	private SampleComponent sampleComponent;

	@Autowired
	public ExampleAPIController(
			SampleComponent sampleComponent,
			@Qualifier String sampleBean
	) {
		this.sampleComponent = sampleComponent;
		this.sampleBean = sampleBean;
	}

	@GetMapping("/example")
	public ResponseEntity<String> exampleApi() {
		log.info("Got request to /example");

		return ResponseEntity.ok("Hello, World!");
	}

	@PostMapping("/hello")
	public ResponseEntity<SayHelloResponse> sayHelloApi(@RequestBody SayHelloRequest request) {
		log.info("Got request to /hello with request: {}", request);

		String name = request.getName();
		Date now = new Date();

		SayHelloResponse response = SayHelloResponse.builder()
				.withMessage("Hello, " + name + "!")
				.withTimestamp(now)
				.build();

		log.info("Returning response from /hello: {}", response);

		return ResponseEntity.ok(response);
	}
}
