package com.randazzo.mario.plantWatering.converter;

import javax.ejb.Stateless;

import com.randazzo.mario.plantWatering.converter.annotation.PersonType;
import com.randazzo.mario.plantWatering.dto.PersonDTO;
import com.randazzo.mario.plantWatering.model.Person;

@Stateless
@PersonType
public class PersonConverter implements Converter<Person, PersonDTO> {

	private static final long serialVersionUID = -2630673762570887829L;

	@Override
	public PersonDTO entityToDto(Person entity) {
		if(entity == null) return null;
		
		PersonDTO dto = new PersonDTO();
		dto.setEmail(entity.getEmail());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		return dto;
	}

	@Override
	public Person dtoToEntity(PersonDTO dto) {
		if(dto == null) return null;
		
		Person entity = new Person();
		entity.setEmail(dto.getEmail());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		return entity;
	}

	

}
