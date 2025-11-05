package com.adcb.audit_logger.filter;

import com.adcb.audit_logger.entity.ApiAuditLog;
import com.adcb.audit_logger.service.AuditLogAsyncService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.stream.Collectors;


public class AuditFilter extends OncePerRequestFilter {

    private final AuditLogAsyncService auditService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuditFilter(AuditLogAsyncService auditService) {
        this.auditService = auditService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println(">>> Audit Filter Triggered: " + request.getRequestURI());

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        Instant requestTimestamp = Instant.now();
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            String responseBody = new String(
                    wrappedResponse.getContentAsByteArray(),
                    wrappedResponse.getCharacterEncoding()
            );
            wrappedResponse.copyBodyToResponse();
            saveAudit(wrappedRequest, wrappedResponse, requestTimestamp, responseBody);
        }
    }

    private void saveAudit(ContentCachingRequestWrapper req,
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
            auditService.save(log);
        } catch (Exception e) {
            logger.error("Audit logging failed", e);
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