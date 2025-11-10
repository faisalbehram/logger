package org.adcb.uoj.logger.service;


import org.adcb.uoj.logger.entity.AuditLog;
import org.adcb.uoj.logger.entity.ExceptionLog;
import org.adcb.uoj.logger.strategy.AuditStorageStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
class AuditLogAsyncServiceImpl implements AuditLogAsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AuditLogAsyncServiceImpl.class);

    private final AuditStorageStrategy storageStrategy;

    public AuditLogAsyncServiceImpl(AuditStorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }
    @Async
    @Override
    public void saveAudit(String uri, String requestData, String responseData, int status, String header, String pathVariable, String methodType) {
        AuditLog log = new AuditLog();
        log.setCreatedAt(Instant.now());
        try {
            log.setEndPoint(uri);
            log.setMethodType(methodType); // optional helper
            log.setRequestData(requestData);
            log.setResponseData(responseData);
            log.setStatus(status);
            log.setHeader(header);
            log.setPathVariable(pathVariable);
            if(responseData != null){
                log.setResponseTimestamp(Instant.now());
            }
            storageStrategy.save(log);
        } catch (Exception e) {
            logger.info(e.getMessage());
            log.setExceptionData(e.toString());
            storageStrategy.save(log);

        }
    }

    @Override
    public void saveExceptionLog(String uri, String exceptionData, int status) {
        ExceptionLog log = new ExceptionLog();
        log.setCreatedAt(Instant.now());
        try {
            log.setEndPoint(uri);
            log.setExceptionData(exceptionData);
            log.setStatus(status);

            storageStrategy.save(log);
        } catch (Exception e) {
            logger.info(e.getMessage());
            storageStrategy.save(log);

        }
    }
}
