package org.adcb.uoj.logger.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Table(name = "audit_logs")
@Entity
public class AuditLog {

    @Id
    @Column(name = "audit_log_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID auditLogId;

    @Column(name = "method_type")
    private String methodType;

    @Column(name = "end_point")
    private String endPoint;

    @Column( name = "path_variable")
    private String pathVariable;

    @Column( name = "request_data")
    private String requestData;

    @Column( name = "response_data")
    private String responseData;

    @Column( name = "header")
    private String header;

    @Column( name = "exception_data")
    private String exceptionData;

    @Column(name = "status")
    private int status;

    @Column(name = "response_timestamp")
    private Instant responseTimestamp;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

}
