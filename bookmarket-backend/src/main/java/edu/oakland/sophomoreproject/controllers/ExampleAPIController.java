package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.components.SampleComponent;
import edu.oakland.sophomoreproject.controllers.requests.SayHelloRequest;
import edu.oakland.sophomoreproject.controllers.responses.SayHelloResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;

@Controller
public class ExampleAPIController {
	Logger log = LogManager.getLogger(ExampleAPIController.class);

	private String sampleBean;
	private SampleComponent sampleComponent;

	@Autowired
	public ExampleAPIController(
			SampleComponent sampleComponent,
			@Qualifier("sampleBean") String sampleBean
	) {
		this.sampleComponent = sampleComponent;
		this.sampleBean = sampleBean;
	}

	@GetMapping("/api/example")
	public ResponseEntity<String> exampleApi() {
		log.info("Received an HTTP GET request to /api/example");

		return ResponseEntity.ok("Hello, World!");
	}

	@PostMapping("/api/hello")
	public ResponseEntity<SayHelloResponse> sayHelloApi(@RequestBody SayHelloRequest request) {
		log.info("Received an HTTP POST request to /api/hello with a request body: {}", request);

		String name = request.getName();
		Instant now = Instant.now();

		SayHelloResponse response = new SayHelloResponse(
				now,
				"Hello, " + name
		);

		log.info("Returning response from /api/hello: {}", response);

		return ResponseEntity.ok(response);
	}
}
