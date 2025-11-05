package com.adcb.audit_logger.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "api_audit_logs")
public class ApiAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID apiAuditLogId;

    @Column(name = "trace_id")
    private String traceId;

    @Column(name = "span_id")
    private String spanId;

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "identifier", length = 15)
    private String identifier;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @Column(name = "request_method")
    private String requestMethod;

    @Column(name = "request_url")
    private String requestUrl;

    @Column(name = "request_headers", length = 4000)
    private String requestHeaders;

    @Column(name = "request_body", columnDefinition = "TEXT")
    private String requestBody;

    @Column(name = "request_timestamp")
    private Instant requestTimestamp;

    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;

    @Column(name = "response_status")
    private Integer responseStatus;

    @Column(name = "response_headers", length = 4000)
    private String responseHeaders;

    @Column(name = "response_timestamp")
    private Instant responseTimestamp;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    public ApiAuditLog() {

    }

    public ApiAuditLog(
            String traceId,
            String spanId,
            String applicationId,
            String identifier,
            String ipAddress,
            String userAgent,
            String requestMethod,
            String requestUrl,
            String requestHeaders,
            String requestBody,
            Instant requestTimestamp,
            String responseBody,
            Integer responseStatus,
            String responseHeaders,
            Instant responseTimestamp) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.applicationId = applicationId;
        this.identifier = identifier;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
        this.requestTimestamp = requestTimestamp;
        this.responseBody = responseBody;
        this.responseStatus = responseStatus;
        this.responseHeaders = responseHeaders;
        this.responseTimestamp = responseTimestamp;
        this.createdAt = Instant.now();
    }
}
