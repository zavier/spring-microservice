package com.zavier.licenses.clients;

import com.zavier.licenses.model.Organization;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * README中方式2： 使用带有 Ribbon 功能的 Spring RestTemplate
 */
@Component
public class OrganizationRestTemplate {

    private final RestTemplate restTemplate;

    public OrganizationRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {
        final ResponseEntity<Organization> restExchange = restTemplate.exchange("http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId);
        return restExchange.getBody();
    }
}
