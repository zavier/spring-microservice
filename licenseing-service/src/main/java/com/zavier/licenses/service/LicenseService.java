package com.zavier.licenses.service;

import com.zavier.licenses.clients.OrganizationDiscoveryClient;
import com.zavier.licenses.config.ServiceConfig;
import com.zavier.licenses.model.License;
import com.zavier.licenses.model.Organization;
import com.zavier.licenses.repository.LicenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;

    private final ServiceConfig serviceConfig;

    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    public LicenseService(LicenseRepository licenseRepository,
                          ServiceConfig serviceConfig,
                          OrganizationDiscoveryClient organizationDiscoveryClient) {
        this.licenseRepository = licenseRepository;
        this.serviceConfig = serviceConfig;
        this.organizationDiscoveryClient = organizationDiscoveryClient;
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

    public License getLicense(String organizationId, String licenseId, String clientType) {
        final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        final Organization organization = retrieveOrgInfo(organizationId, clientType);
        if (organization != null) {
            log.info("organization:{}", organization.getName());
            license.setOrganizationName(organization.getName());
        }
        return license;
    }

    public Organization retrieveOrgInfo(String organizationId, String clientType) {
        final Organization organization = organizationDiscoveryClient.getOrganization(organizationId);
        return organization;
    }
}
