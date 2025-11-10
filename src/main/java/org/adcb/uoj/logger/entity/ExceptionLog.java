package org.adcb.uoj.logger.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Table(name = "exception_logs")
@Entity
public class ExceptionLog {

    @Id
    @Column(name = "exception_log_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID exceptionLogId;

    @Column(name = "end_point")
    private String endPoint;

    @Column(name = "exception_data")
    private String exceptionData;

    @Column(name = "status")
    private int status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

}
