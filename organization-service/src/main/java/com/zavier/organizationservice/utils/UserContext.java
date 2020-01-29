package com.zavier.organizationservice.utils;

import lombok.Data;

@Data
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String USER_ID = "tmx-user-id";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String ORG_ID = "tmx-org-id";

    private String correlationId;

    private String userId;

    private String authToken;

    private String orgId;

    @Override
    public String toString() {
        return "UserContext{" +
                "correlationId='" + correlationId + '\'' +
                ", userId='" + userId + '\'' +
                ", authToken='" + authToken + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
