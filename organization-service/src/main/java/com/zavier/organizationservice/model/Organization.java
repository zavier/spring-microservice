package com.zavier.organizationservice.model;

import lombok.Data;

@Data
public class Organization {
    private String id;
    private String name;

    @Override
    public String toString() {
        return "Organization{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
