package com.zavier.licenses.utils;

import lombok.Data;

@Data
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String USER_ID = "userId";
    public static final String AUTH_TOKEN = "authToken";
    public static final String ORG_ID = "orgId";

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
