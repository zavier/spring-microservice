package com.zavier.organizationservice.service;

import com.zavier.organizationservice.events.source.SimpleSourceBean;
import com.zavier.organizationservice.model.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class OrganizationService {

    private final SimpleSourceBean simpleSourceBean;

    public OrganizationService(SimpleSourceBean simpleSourceBean) {
        this.simpleSourceBean = simpleSourceBean;
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());
        //todo 保存到数据库
        log.info("saveOrg:{}", org.toString());
        simpleSourceBean.publishOrgChange("SAVE", org.getId());
    }
}
