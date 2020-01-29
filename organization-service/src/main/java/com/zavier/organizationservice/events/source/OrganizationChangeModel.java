package com.zavier.organizationservice.events.source;

import lombok.Data;

@Data
public class OrganizationChangeModel {

    private String typeName;

    private String action;

    private String orgId;

    private String correlationId;

    public OrganizationChangeModel(String typeName, String action, String orgId, String correlationId) {
        this.typeName = typeName;
        this.action = action;
        this.orgId = orgId;
        this.correlationId = correlationId;
    }
}
