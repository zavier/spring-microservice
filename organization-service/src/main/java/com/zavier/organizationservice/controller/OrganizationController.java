package com.zavier.organizationservice.controller;

import com.zavier.organizationservice.model.Organization;
import com.zavier.organizationservice.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        log.info("organizationId:" + organizationId);
        final Organization organization = new Organization();
        organization.setName("org1");
        return organization;
    }

    @PutMapping("/{organizationId}")
    public Organization saveOrganization(@RequestBody Organization organization) {
        organizationService.saveOrg(organization);
        return organization;
    }
}
