package org.adcb.uoj.logger.strategy;

import org.adcb.uoj.logger.entity.AuditLog;
import org.adcb.uoj.logger.entity.ExceptionLog;
import org.adcb.uoj.logger.repository.AuditLogRepository;
import org.adcb.uoj.logger.repository.ExceptionLogRepository;

public class DatabaseAuditStorageStrategy implements AuditStorageStrategy {

    private final AuditLogRepository repository;
    private final ExceptionLogRepository exceptionLogRepository;

    public DatabaseAuditStorageStrategy(AuditLogRepository repository, ExceptionLogRepository exceptionLogRepository) {
        this.repository = repository;
        this.exceptionLogRepository = exceptionLogRepository;
    }

    @Override
    public void save(AuditLog log) {
        repository.save(log);
    }

    @Override
    public void save(ExceptionLog log) {
        exceptionLogRepository.save(log);
    }
}
