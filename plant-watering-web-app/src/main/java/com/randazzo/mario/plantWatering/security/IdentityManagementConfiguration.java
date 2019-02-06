package com.randazzo.mario.plantWatering.security;

import javax.enterprise.event.Observes;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;


/**
 * <p>
 * A simple CDI observer for the
 * {@link org.picketlink.event.SecurityConfigurationEvent}.
 * </p>
 *
 * <p>
 * The event is fired during application startup and allows you to provide any
 * configuration to PicketLink before it is initialized.
 * </p>
 *
 * <p>
 * All the configuration to PicketLink Identity Management is provided from this
 * bean.
 * </p>
 *
 * @author Pedro Igor
 */
public class IdentityManagementConfiguration {

	public void configureIdentityManagement(@Observes SecurityConfigurationEvent event) {
        SecurityConfigurationBuilder builder = event.getBuilder();

        
        builder.idmConfig().named("default.config").stores().jpa().supportAllFeatures();
    }
}