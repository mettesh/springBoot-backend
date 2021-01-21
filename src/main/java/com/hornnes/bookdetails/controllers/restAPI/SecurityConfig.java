package com.hornnes.bookdetails.controllers.restAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

// Configuration automatically enables a Spring Security filter that authenticates requests via an incoming OAuth 2.0 token
@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {
    // used to set the identifier of your API
    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    // Used to specify which API endpoints are secured
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/restapi/public").permitAll()
                .antMatchers("/restapi/allBooks").access("#oauth2.hasScope('see:books')")
                .antMatchers("/restapi/allAuthors").access("#oauth2.hasScope('see:authors')")
                .mvcMatchers("/restapi/**").authenticated()
                .anyRequest().permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId);
    }
}
