package org.adcb.uoj.logger.service;


public interface AuditLogAsyncService {

    public void saveAudit(String uri, String requestData,String responseData, int status, String header, String pathVariable, String methodType) ;

    public void saveExceptionLog(String uri, String exceptionData,int status) ;

}
