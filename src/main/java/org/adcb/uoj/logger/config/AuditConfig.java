package org.adcb.uoj.logger.config;

import org.adcb.uoj.logger.repository.AuditLogRepository;
import org.adcb.uoj.logger.repository.ExceptionLogRepository;
import org.adcb.uoj.logger.strategy.AuditStorageStrategy;
import org.adcb.uoj.logger.strategy.DatabaseAuditStorageStrategy;
import org.adcb.uoj.logger.strategy.LogFileAuditStorageStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditConfig {

    @Bean
    @ConditionalOnProperty(name = "audit.storage", havingValue = "db")
    public AuditStorageStrategy dbStrategy(AuditLogRepository repository, ExceptionLogRepository exceptionLogRepository) {
        return new DatabaseAuditStorageStrategy(repository,exceptionLogRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "audit.storage", havingValue = "log")
    public AuditStorageStrategy logStrategy() {
        return new LogFileAuditStorageStrategy();
    }

    // 👇 fallback when property missing or invalid
    @Bean
    @ConditionalOnMissingBean(AuditStorageStrategy.class)
    public AuditStorageStrategy defaultStrategy() {
        return new LogFileAuditStorageStrategy();
    }
}
