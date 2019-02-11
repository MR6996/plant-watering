package com.randazzo.mario.plantWatering.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.randazzo.mario.plantWatering.model.Plant;

@Stateless
@Remote
public class PlantDAO implements Serializable {

	@PersistenceContext
	EntityManager em;

	public void save(Plant p) {
		Plant newP = new Plant();
		newP.setName(p.getName());
		newP.setDescription(p.getDescription());
		em.persist(newP);
	}

	public Plant findById(Long id) {
		return em.find(Plant.class, id);
	}

	public List<Plant> findAll() {
		return em.createQuery("from plant p", Plant.class).getResultList();
	}

}
