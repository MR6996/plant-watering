package com.randazzo.mario.plantWatering.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.randazzo.mario.plantWatering.model.Person;

/**
 * DAO for {@link Person} data type.
 * 
 * @author Mario Randazzo
 *
 */
@Stateless
public class PersonDAO {

	@PersistenceContext
	EntityManager em;

	public Person findPersonByEmail(String email) {
		return em.find(Person.class, email);
	}  
	
}

