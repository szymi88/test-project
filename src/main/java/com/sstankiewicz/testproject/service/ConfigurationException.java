package com.sstankiewicz.testproject.service;

class ConfigurationException extends RuntimeException {
	ConfigurationException() {
		super("Invalid github proxy configuration (git.repos.api.pattern)");
	}
}
