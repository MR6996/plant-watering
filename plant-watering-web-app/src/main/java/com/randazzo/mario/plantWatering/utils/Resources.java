package com.randazzo.mario.plantWatering.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.picketlink.annotations.PicketLink;

@ApplicationScoped
public class Resources {

	@Produces
	@PersistenceContext
	@PicketLink
	private EntityManager em; 
	
}