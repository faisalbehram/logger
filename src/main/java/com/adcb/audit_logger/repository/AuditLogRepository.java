package com.adcb.audit_logger.repository;

import com.adcb.audit_logger.entity.ApiAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<ApiAuditLog, UUID> {

}
