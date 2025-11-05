package com.adcb.audit_logger;

import com.adcb.audit_logger.filter.AuditFilter;
import com.adcb.audit_logger.service.AuditLogAsyncService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AuditLoggerAutoConfiguration {

    @Bean
    public AuditFilter auditFilter(AuditLogAsyncService auditService) {
        return new AuditFilter(auditService);
    }
}
