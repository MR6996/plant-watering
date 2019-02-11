
package com.randazzo.mario.plantWatering.security;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

@ApplicationScoped
public class HttpSecurityConfiguration {

    public void onInit(@Observes SecurityConfigurationEvent event) {
        SecurityConfigurationBuilder builder = event.getBuilder();

        builder
            .identity()
                .stateless()
            .http()
                .forPath("/private/*")
                    .authenticateWith()
                        .token()
                    .cors()
                        .allowAll();
    }

}
