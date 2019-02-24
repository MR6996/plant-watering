package com.randazzo.mario.plantWatering.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.randazzo.mario.plantWatering.model.Person;
import com.randazzo.mario.plantWatering.model.Plant;

/**
 * DAO for {@link Plant} data type.
 * 
 * @author Mario Randazzo
 *
 */
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Plant> criteriaQuery = cb.createQuery(Plant.class);
		Root<Plant> root = criteriaQuery.from(Plant.class);
		Join<Plant, Person> personRoot = root.join("person");
		criteriaQuery
			.select(root)
			.where(
					cb.and(
							cb.equal(root.get("id"), id), 
							cb.equal(personRoot.get("email"), personEmail)
						  )
				  );
		
		TypedQuery<Plant> query = em.createQuery(criteriaQuery);
		List<Plant> results = query.getResultList();

		if(results.size() > 0) return results.get(0);
		
		return null;
	}

	public List<Plant> findByPersonEmail(String personEmail) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Plant> criteriaQuery = cb.createQuery(Plant.class);
		Root<Plant> root = criteriaQuery.from(Plant.class);
		Join<Plant, Person> personRoot = root.join("person");
		criteriaQuery
			.select(root)
			.where(
					cb.and(
							cb.equal(personRoot.get("email"), personEmail)
						  )
				  );
		
		TypedQuery<Plant> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}

}
