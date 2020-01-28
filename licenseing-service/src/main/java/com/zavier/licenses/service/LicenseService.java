package com.zavier.licenses.service;

import com.zavier.licenses.config.ServiceConfig;
import com.zavier.licenses.model.License;
import com.zavier.licenses.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;

    private final ServiceConfig serviceConfig;

    public LicenseService(LicenseRepository licenseRepository, ServiceConfig serviceConfig) {
        this.licenseRepository = licenseRepository;
        this.serviceConfig = serviceConfig;
    }

    public License getLicense(String organizationId, String licenseId) {
        final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.setComment(serviceConfig.getExampleProperty());
        return license;
    }

    public List<License> getLicensesByOrg(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }
}
