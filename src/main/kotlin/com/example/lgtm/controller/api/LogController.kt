package com.example.lgtm.controller.api

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class LogRequest(val level: String, val message: String)
data class LogResponse(val level: String, val message: String)

@RestController
@RequestMapping("/api/log")
class LogController {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("")
    fun writeLog(@RequestBody req: LogRequest): ResponseEntity<Any> {
        val level = req.level.uppercase()
        val message = req.message.trim()

        if (message.isBlank()) {
            return ResponseEntity
                .badRequest().body(mapOf("error" to "메시지가 비어있습니다."))
        }

        when (level) {
            "TRACE" -> log.trace(message)
            "DEBUG" -> log.debug(message)
            "INFO"  -> log.info(message)
            "WARN"  -> log.warn(message)
            "ERROR" -> log.error(message)
            else    -> return ResponseEntity.badRequest().body(mapOf("error" to "유효하지 않은 로그 레벨: $level"))
        }

        return ResponseEntity.ok(LogResponse(level = level, message = message))
    }
}
