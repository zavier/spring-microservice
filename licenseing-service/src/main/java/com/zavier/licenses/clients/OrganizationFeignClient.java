package com.zavier.licenses.clients;

import com.zavier.licenses.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * README中方式1： 使用 Netflix feign 客户端
 */
@FeignClient("organizationservice")
public interface OrganizationFeignClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/v1/organizations/{organizationId}", consumes = "application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
