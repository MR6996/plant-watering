
package com.randazzo.mario.plantWatering.security.service;

import static com.randazzo.mario.plantWatering.security.model.ApplicationRole.USER;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.randazzo.mario.plantWatering.converter.Converter;
import com.randazzo.mario.plantWatering.converter.annotation.UserRegistrationType;
import com.randazzo.mario.plantWatering.dto.security.UserRegistrationDTO;
import com.randazzo.mario.plantWatering.security.model.IdentityModelManager;
import com.randazzo.mario.plantWatering.security.model.User;
import com.randazzo.mario.plantWatering.security.model.UserRegistration;
import com.randazzo.mario.plantWatering.utils.MessageBuilder;


@Stateless
@Path("/register")
public class RegistrationService implements Serializable {

	private static final long serialVersionUID = 1741800718124694491L;

	@Inject
	private IdentityModelManager identityModelManager;
	
	@Inject
	@UserRegistrationType
	private Converter<UserRegistration, UserRegistrationDTO> registrationConverter;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMember(UserRegistrationDTO request) {
		UserRegistration resistrationRequest = registrationConverter.dtoToEntity(request);
		if (!request.getPassword().equals(request.getPasswordConfirmation())) {
			return MessageBuilder.badRequest().message("Password mismatch.").build();
		}

		MessageBuilder message;

		try {
			if (this.identityModelManager.findByLoginName(request.getEmail()) == null) {
				User newUser = this.identityModelManager.createAccount(resistrationRequest);
				this.identityModelManager.grantRole(newUser, USER);
				
				this.identityModelManager.activateAccount(newUser);

				message = MessageBuilder.ok().message("User registered successfully!");
			} else {
				message = MessageBuilder.badRequest().message("This username is already in use. Try another one.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = MessageBuilder.badRequest().message(e.getCause() + ": " + e.getMessage());
		}

		return message.build();
	}
}