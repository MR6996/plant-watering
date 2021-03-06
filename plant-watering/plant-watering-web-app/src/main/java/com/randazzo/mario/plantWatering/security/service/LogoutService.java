package com.randazzo.mario.plantWatering.security.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.picketlink.Identity;
import org.picketlink.authorization.annotations.LoggedIn;
import org.picketlink.idm.credential.Token;
import org.picketlink.idm.model.Account;

import com.randazzo.mario.plantWatering.security.authentication.JWSToken;

/**
 * RESTFul endpoint responsible for perform logout.
 *
 * @author Mario Randazzo
 */
@Path("/private/logout")
@Stateless
public class LogoutService {

	@Inject
	private Token.Provider<JWSToken> tokenProvider;

	@Inject
	private Identity identity;

	@GET
	@LoggedIn
	public void logout() {
		Account account = this.identity.getAccount();

		this.tokenProvider.invalidate(account);

		this.identity.logout();
	}
}