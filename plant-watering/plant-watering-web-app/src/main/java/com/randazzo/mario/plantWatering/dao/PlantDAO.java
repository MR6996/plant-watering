package com.randazzo.mario.plantWatering.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.randazzo.mario.plantWatering.model.Person;
import com.randazzo.mario.plantWatering.model.Plant;

@Stateless
@Remote
public class PlantDAO implements Serializable {

	private static final long serialVersionUID = 7658496890054544163L;

	@PersistenceContext
	EntityManager em;

	public void save(Plant p, String ownerLoginName) {
		Person owner = em.find(Person.class, ownerLoginName);
		p.setPerson(owner);
		em.persist(p);
	}

	public boolean update(Plant p) {
		if(em.find(Plant.class, p.getId()) != null) {
			em.merge(p);
			return true;
		}
		
		return false;
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
		List<Plant> results = em.createQuery("Select p From plant p Where p.person.email = :email", Plant.class)
				 .setParameter("email", personEmail)
				 .getResultList();
		
		System.out.println(results.size());
		return results;
	}

}
