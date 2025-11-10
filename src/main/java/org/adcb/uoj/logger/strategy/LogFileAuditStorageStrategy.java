package org.adcb.uoj.logger.strategy;

import org.adcb.uoj.logger.entity.AuditLog;
import org.adcb.uoj.logger.entity.ExceptionLog;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogFileAuditStorageStrategy implements AuditStorageStrategy {

    private static final Logger logger = LoggerFactory.getLogger(LogFileAuditStorageStrategy.class);

    @Override
    public void save(AuditLog log) {
        logger.info("AUDIT_LOG => {}", log);
    }

    @Override
    public void save(ExceptionLog log) {
        logger.info("Exception_LOG => {}", log);
    }
}
