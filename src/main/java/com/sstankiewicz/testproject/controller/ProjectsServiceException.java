package com.sstankiewicz.testproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class ProjectsServiceException extends RuntimeException {
    ProjectsServiceException(RuntimeException e) {
        super(e);
    }
}