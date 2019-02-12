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
		Measure newM = new Measure();
		newM.setPlant(m.getPlant());
		newM.setExternalHumidity(m.getExternalHumidity());
		newM.setExternalTemperature(m.getExternalTemperature());
		em.persist(newM);
	}
	
	public List<Measure> findByPlantId(Long id) {
		return em.createQuery("Select m From measure m, plant Where m.plant.id = :plantId", Measure.class)
				.setParameter("plantId", id)
				.getResultList();
	}

	
}
