package com.adcb.audit_logger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuditController {

    @GetMapping("/echo")
    public ResponseEntity<Map<String, Object>> echo() {
        return ResponseEntity.ok(Map.of("message", "ok"));
    }


}
