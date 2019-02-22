package com.randazzo.mario.plantWatering.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.picketlink.Identity;

import com.randazzo.mario.plantWatering.converter.Converter;
import com.randazzo.mario.plantWatering.converter.annotation.PersonType;
import com.randazzo.mario.plantWatering.dao.PersonDAO;
import com.randazzo.mario.plantWatering.dto.PersonDTO;
import com.randazzo.mario.plantWatering.model.Person;
import com.randazzo.mario.plantWatering.security.model.User;

@Path("/private/account")
@Stateless
public class AccountService {
	
	@Inject
	private PersonDAO personDao;
	
	@Inject
	@PersonType
	private Converter<Person, PersonDTO> personCoverter;
	
	@Inject
	private Identity identity;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PersonDTO getPerson() {
		User loggedUser = (User) identity.getAccount();
		return personCoverter.entityToDto(personDao.findPersonByEmail(loggedUser.getLoginName()));
	}
	
}
