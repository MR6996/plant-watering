package com.randazzo.mario.plantWatering.security;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

import com.randazzo.mario.plantWatering.security.model.User;


@ApplicationScoped
public class IdentityManagementConfiguration implements Serializable {

	private static final long serialVersionUID = 555627760122406519L;

	public void configureIdentityManagement(@Observes SecurityConfigurationEvent event) {
        SecurityConfigurationBuilder builder = event.getBuilder();

        
        builder
        	.idmConfig()
        		.named("default.config")
        			.stores()
        				.jpa()
        					.supportType(User.class)
        					.supportAllFeatures();
    }
}