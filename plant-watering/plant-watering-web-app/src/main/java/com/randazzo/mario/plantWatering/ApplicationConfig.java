package com.randazzo.mario.plantWatering;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *  A class extending {@link Application} and annotated with 
 *  @ApplicationPath for activate rest services.
 * 
 * @author Mario Randazzo
 *
 */
@ApplicationPath("/")
public class ApplicationConfig extends Application {
	
}
