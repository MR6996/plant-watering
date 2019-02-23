package com.randazzo.mario.plantWatering.dao;

import java.util.List;

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
	
	public List<Measure> findByPlantId(Long id) {
		return em.createQuery("Select m From measure m Where m.plant.id = :plantId", Measure.class)
				.setParameter("plantId", id)
				.getResultList();
	}

	
}
