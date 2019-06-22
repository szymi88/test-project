package com.sstankiewicz.testproject.service.exception;

public class ConfigurationException extends RuntimeException {
	public ConfigurationException() {
		super("Invalid github proxy configuration (git.repos.api.pattern)");
	}
}
