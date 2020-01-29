package com.zavier.licenses.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zavier.licenses.clients.OrganizationDiscoveryClient;
import com.zavier.licenses.config.ServiceConfig;
import com.zavier.licenses.model.License;
import com.zavier.licenses.model.Organization;
import com.zavier.licenses.repository.LicenseRepository;
import com.zavier.licenses.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @HystrixCommand(commandProperties =
            {
                    // 超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000"),
                    // 断路器
                    // 断路前10s内必须发生的调用次数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    // 达到调用次数后，需要达到的失败的百分比
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    // 断路后，测试调用的间隔时间
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    // 监视调用问题的窗口大小，默认为10s
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    // 在滚动窗口中收集统计信息的次数（将每个窗口的数据分到这个指定数量的桶中）
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5"),

            },
            fallbackMethod = "buildFallbackLicenseList",
            // 独立线程池配置
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )
    public List<License> getLicensesByOrg(String organizationId) {
        log.info("userContext:{}", UserContextHolder.getContext().toString());
        // 随机超时，测试 Hystrix
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> licenses = new ArrayList<>();
        final License license = new License()
                .setLicenseId("000000")
                .setOrganizationId(organizationId)
                .setProductName("Sorry no licensing information currently available");
        licenses.add(license);
        return licenses;
    }

    // 随机休眠，1/3 概率
    private void randomlyRunLong() {
        final Random random = new Random();
        final int randomNum = random.nextInt(3) + 1;
        if (randomNum == 3) {
            sleep();
        }
    }

    private void sleep() {
        log.info("start sleep");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
