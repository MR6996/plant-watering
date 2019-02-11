
package com.randazzo.mario.plantWatering.security.service;

import static com.randazzo.mario.plantWatering.security.model.ApplicationRole.USER;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.picketlink.idm.credential.Token;

import com.randazzo.mario.plantWatering.security.model.IdentityModelManager;
import com.randazzo.mario.plantWatering.security.model.User;
import com.randazzo.mario.plantWatering.security.model.UserRegistration;
import com.randazzo.mario.plantWatering.utils.MessageBuilder;


/**
 * <p>
 * RESTFul endpoint responsible for:
 * </p>
 *
 * <ul>
 * <li>Create a new user account and send a notification with the activation
 * code.</li>
 * <li>Activate a previously created account based on a activation code..</li>
 * </ul>
 *
 * <p>
 * After a successful registration, an account is always disabled. In order to
 * enable the account and be able to log in, the activation code must be used to
 * invoke the <code>enableAccount</code> resource.
 * </p>
 *
 * @author Pedro Igor
 */
@Stateless
@Path("/register")
public class RegistrationService {

	@Inject
	private IdentityModelManager identityModelManager;


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMember(UserRegistration request) {
		if (!request.getPassword().equals(request.getPasswordConfirmation())) {
			return MessageBuilder.badRequest().message("Password mismatch.").build();
		}

		MessageBuilder message;

		try {
			// if there is no user with the provided e-mail, perform registration
			if (this.identityModelManager.findByLoginName(request.getEmail()) == null) {
				User newUser = this.identityModelManager.createAccount(request);
				this.identityModelManager.grantRole(newUser, USER);
				String activationCode = newUser.getActivationCode();

				message = MessageBuilder.ok().activationCode(activationCode);
			} else {
				message = MessageBuilder.badRequest().message("This username is already in use. Try another one.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = MessageBuilder.badRequest().message(e.getCause() + ": " + e.getMessage());
		}

		return message.build();
	}

	@POST
	@Path("/activation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateAccount(String activationCode) {
		MessageBuilder message;

		try {
			Token token = this.identityModelManager.activateAccount(activationCode);
			message = MessageBuilder.ok().token(token.getToken());
		} catch (Exception e) {
			message = MessageBuilder.badRequest().message(e.getMessage());
		}

		return message.build();
	}

}