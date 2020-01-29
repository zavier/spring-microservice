package com.zavier.organizationservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // delete 需要 admin 角色
                .antMatchers(HttpMethod.DELETE, "/v1/organizations/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();
    }
}
