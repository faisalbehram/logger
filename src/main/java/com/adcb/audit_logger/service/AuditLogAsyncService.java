package com.adcb.audit_logger.service;


public interface AuditLogAsyncService {

    public void saveAudit(String uri, String requestData,String responseData, int status, String header, String pathVariable, String methodType) ;

}
