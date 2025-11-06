package com.adcb.audit_logger.controller;

import com.adcb.audit_logger.service.AuditLogAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuditController {

    @Autowired
    AuditLogAsyncService auditLogAsyncService;

    @GetMapping("/echo")
    public ResponseEntity<Map<String, Object>> echo()

    {
        auditLogAsyncService.saveAudit("test","test","test",200,"test","test","test");
        return ResponseEntity.ok(Map.of("message", "ok"));
    }


}
