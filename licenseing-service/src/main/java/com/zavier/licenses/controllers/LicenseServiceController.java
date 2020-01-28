package com.zavier.licenses.controllers;

import com.zavier.licenses.model.License;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    @GetMapping(value = "/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {
        return new License()
                .setLicenseId(licenseId)
                .setProductName("Teleco")
                .setLicenseType("Seat")
                .setOrganizationId("TestOrg");
    }
}
