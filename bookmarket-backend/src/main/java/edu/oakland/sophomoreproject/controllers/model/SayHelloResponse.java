package edu.oakland.sophomoreproject.controllers.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * These "@..." things are called annotations. They are a way to add metadata to your code.
 * I'm using a library called Lombok which reads these annotations and generates a bunch of code on top of the class
 * that I would've had to write by hand. It's a huge time saver. Also keeps the code consistent and clean
 */

@Builder(setterPrefix = "with")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class SayHelloResponse {
	private Date timestamp;
	private String message;
}
