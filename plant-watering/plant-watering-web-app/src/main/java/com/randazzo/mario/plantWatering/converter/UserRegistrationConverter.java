package com.randazzo.mario.plantWatering.converter;

import javax.ejb.Stateless;

import com.randazzo.mario.plantWatering.converter.annotation.UserRegistrationType;
import com.randazzo.mario.plantWatering.dto.security.UserRegistrationDTO;
import com.randazzo.mario.plantWatering.security.model.UserRegistration;

/**
 * Implementation of a {@link Converter} for {@link UserRegistration} data type.
 * 
 * @author Mario Randazzo
 *
 * @see UserRegistrationType
 *
 */
@Stateless
@UserRegistrationType
public class UserRegistrationConverter implements Converter<UserRegistration, UserRegistrationDTO> {

	private static final long serialVersionUID = 2971263191521916895L;

	@Override
	public UserRegistrationDTO entityToDto(UserRegistration entity) {
		if(entity == null) return null;
		
		UserRegistrationDTO dto = new UserRegistrationDTO();
		dto.setEmail(entity.getEmail());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setPassword(entity.getPassword());
		dto.setPasswordConfirmation(entity.getPasswordConfirmation());
		return dto;
	}

	@Override
	public UserRegistration dtoToEntity(UserRegistrationDTO dto) {
		if(dto == null) return null;
		
		UserRegistration entity = new UserRegistration();
		entity.setEmail(dto.getEmail());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setPassword(dto.getPassword());
		entity.setPasswordConfirmation(dto.getPasswordConfirmation());
		return entity;
	}

}
