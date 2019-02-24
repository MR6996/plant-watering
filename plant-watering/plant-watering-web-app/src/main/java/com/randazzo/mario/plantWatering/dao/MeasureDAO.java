package com.randazzo.mario.plantWatering.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.randazzo.mario.plantWatering.model.Measure;

@Stateless
public class MeasureDAO {

	@PersistenceContext
	EntityManager em;

	public void save(Measure m) {
		em.persist(m);
	}
	
}
