package com.zavier.licenses.clients;

import com.zavier.licenses.model.Organization;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * README中方式1： 使用DiscoveryClient获取通过Eureka注册的所有的服务，选择一个调用
 */
@Component
public class OrganizationDiscoveryClient {
    private final DiscoveryClient discoveryClient;

    private final OrganizationRestTemplate organizationRestTemplate;

    public OrganizationDiscoveryClient(DiscoveryClient discoveryClient, OrganizationRestTemplate organizationRestTemplate) {
        this.discoveryClient = discoveryClient;
        this.organizationRestTemplate = organizationRestTemplate;
    }

    public Organization getOrganization(String organizationId) {
        final RestTemplate restTemplate = new RestTemplate();
        final List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
        if (instances.size() == 0) {
            return null;
        }
        final String serviceUri = String.format("%s/v1/organizations/%s", instances.get(0).getUri().toASCIIString(), organizationId);
        final ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);

        // 另一种调用方式，此种方式 spring cloud sleuth 会自动传递关联ID到下一个系统
        organizationRestTemplate.getOrganization(organizationId);

        return restExchange.getBody();
    }
}
