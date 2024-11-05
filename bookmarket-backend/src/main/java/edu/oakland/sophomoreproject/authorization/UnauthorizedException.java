package edu.oakland.sophomoreproject.authorization;

/// Custom exception class just so that we know specifically if an exception happened from
/// a user being unauthorized over something else
public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException(String message) {
		super(message);
	}
}
