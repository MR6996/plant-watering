package com.randazzo.mario.plantWatering.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.randazzo.mario.plantWatering.model.Plant;

@Stateless
public class PlantDAO {

	@PersistenceContext
	EntityManager em;

	public void save(Plant p) {
		em.persist(p);
	}

	public void update(Plant p) {
		em.merge(p);	
	}
	
	public Plant findByIdPersonEmail(Long id, String personEmail) {
		List<Plant> results = em.createQuery("Select p From plant p Where p.id = :id And p.person.email = :email", Plant.class)
				 				.setParameter("id", id)
				 				.setParameter("email", personEmail)
				 				.getResultList();
		
		if(results.size() > 0) return results.get(0);
		
		return null;
	}

	public List<Plant> findByPersonEmail(String personEmail) {
		return  em.createQuery("Select p From plant p Where p.person.email = :email", Plant.class)
				 .setParameter("email", personEmail)
				 .getResultList();
		
	}

}
