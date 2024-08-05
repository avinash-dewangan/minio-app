package com.avi.minio.service;

import java.io.InputStream;

public interface DocumentManagementService {
    public void saveCertificate(String applyRefNo, InputStream fileData);
    public InputStream getCertificate(String applyRefNo);

    void saveDocument(String appAnnexId, InputStream fileData, String contentType, String moduleName);

    public InputStream getDocument(String moduleName, String appAnnexId);

}
