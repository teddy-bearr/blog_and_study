package com.example.validation.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ClientBadRequestException(
    message: String? = null,
) : RuntimeException(message)
