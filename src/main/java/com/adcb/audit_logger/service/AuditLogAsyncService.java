package com.adcb.audit_logger.service;

import com.adcb.audit_logger.entity.ApiAuditLog;
import com.adcb.audit_logger.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class AuditLogAsyncService {

    @Autowired
    private AuditLogRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Async
    public void save(ApiAuditLog log) {
        repository.save(log);
    }


    @Async
    public void saveAudit(ContentCachingRequestWrapper req,
                           ContentCachingResponseWrapper res,
                           Instant requestTimestamp,
                           String responseBody) {
        try {
            Instant responseTimestamp = Instant.now();
            String requestBody = new String(
                    req.getContentAsByteArray(),
                    req.getCharacterEncoding()
            );
            String headers = req.getHeaderNames() != null
                    ? Collections.list(req.getHeaderNames()).stream()
                    .map(h -> h + ": " + req.getHeader(h))
                    .collect(Collectors.joining("; "))
                    : "";

            String appRef = extractAppRef(requestBody);

            ApiAuditLog log = new ApiAuditLog();
            log.setTraceId("auto");
            log.setSpanId("auto");
            log.setApplicationId(appRef);
            log.setIdentifier("system");
            log.setIpAddress(req.getRemoteAddr());
            log.setUserAgent(req.getHeader(HttpHeaders.USER_AGENT));
            log.setRequestMethod(req.getMethod());
            log.setRequestUrl(req.getRequestURI());
            log.setRequestHeaders(headers);
            log.setRequestBody(requestBody);
            log.setRequestTimestamp(requestTimestamp);
            log.setResponseBody(responseBody);
            log.setResponseStatus(res.getStatus());
            log.setResponseHeaders(res.getHeaderNames().stream()
                    .map(h -> h + ": " + res.getHeader(h))
                    .collect(Collectors.joining("; ")));
            log.setResponseTimestamp(responseTimestamp);
            repository.save(log);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private String extractAppRef(String body) {
        try {
            if (body == null || body.isEmpty()) return null;

            JsonNode node = objectMapper.readTree(body);
            for (String key : new String[]{"appRefNo", "CustomerId", "agentId", "eid"}) {
                if (node.has(key)) return node.get(key).asText();
            }
        } catch (Exception ignored) {}
        return null;
    }
}
