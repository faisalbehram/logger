package org.adcb.uoj.logger.strategy;

import org.adcb.uoj.logger.entity.AuditLog;
import org.adcb.uoj.logger.entity.ExceptionLog;

public interface AuditStorageStrategy {
    void save(AuditLog log);
    void save(ExceptionLog log);
}
