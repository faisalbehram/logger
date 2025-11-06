package com.adcb.audit_logger.service;


import com.adcb.audit_logger.entity.AuditLog;
import com.adcb.audit_logger.repository.AuditLogRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
class AuditLogAsyncServiceImpl implements AuditLogAsyncService {


    private final AuditLogRepository repository;

    public AuditLogAsyncServiceImpl (AuditLogRepository repository){
        this.repository=repository;
    }

    @Async
    @Override
    public void saveAudit(String uri, String requestData, String responseData, int status, String header, String pathVariable, String methodType) {
        AuditLog log = new AuditLog();
        log.setCreatedAt(Instant.now());
        try {
            log.setEndPoint(methodType);
            log.setMethodType(uri); // optional helper
            log.setRequestData(requestData);
            log.setResponseData(responseData);
            log.setStatus(status);
            log.setHeader(header);
            log.setPathVariable(pathVariable);
            if(responseData != null){
                log.setResponseTimestamp(Instant.now());
            }
            repository.save(log);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Audit log failed: " + e.getMessage());
            log.setExceptionData(e.toString());
            repository.save(log);

        }
    }
}
