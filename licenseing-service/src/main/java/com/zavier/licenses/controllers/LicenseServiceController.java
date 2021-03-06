package com.zavier.licenses.controllers;

import com.zavier.licenses.model.License;
import com.zavier.licenses.service.LicenseService;
import com.zavier.licenses.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    private final LicenseService licenseService;

    public LicenseServiceController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/")
    public List<License> getLicenseByOrg(@PathVariable("organizationId") String organizationId) {
        log.info("userContext:{}", UserContextHolder.getContext().toString());
        final List<License> licensesByOrg = licenseService.getLicensesByOrg(organizationId);
        return licensesByOrg;
    }

    @GetMapping(value = "/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {
        return new License()
                .setLicenseId(licenseId)
                .setProductName("Teleco")
                .setLicenseType("Seat")
                .setOrganizationId("TestOrg");
    }

    @GetMapping(value = "/{licenseId}/{clientType}")
    public License getLicenseWithClient(@PathVariable("organizationId") String organizationId,
                                        @PathVariable("licenseId") String licenseId,
                                        @PathVariable("clientType") String clientType) {
        return licenseService.getLicense(organizationId, licenseId, clientType);
    }
}
