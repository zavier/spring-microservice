package com.zavier.organizationservice.controller;

import com.zavier.organizationservice.model.Organization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/organizations")
public class OrganizationController {

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        System.out.println("organizationId:" + organizationId);
        final Organization organization = new Organization();
        organization.setName("org1");
        return organization;
    }
}
