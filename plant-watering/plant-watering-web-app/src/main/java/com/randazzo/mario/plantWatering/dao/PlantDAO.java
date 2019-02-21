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

	private static final long serialVersionUID = 7658496890054544163L;

	@PersistenceContext
	EntityManager em;

	public void save(Plant p) {
		em.persist(p);
	}

	public boolean update(Plant p) {
		if(em.find(Plant.class, p.getId()) != null) {
			em.merge(p);
			return true;
		}
		
		return false;
	}
	
	public Plant findById(Long id) {
		return em.find(Plant.class, id);
	}

	public List<Plant> findAll() {
		return em.createQuery("from plant p", Plant.class).getResultList();
	}

}
