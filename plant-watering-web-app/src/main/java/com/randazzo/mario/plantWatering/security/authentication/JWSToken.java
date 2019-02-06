package com.randazzo.mario.plantWatering.security.authentication;

import org.picketlink.idm.credential.AbstractToken;
import org.picketlink.json.jose.JWS;
import org.picketlink.json.jose.JWSBuilder;

public class JWSToken extends AbstractToken {

	private final JWS jws;
	
	public JWSToken(String token) {
		super(token);
		this.jws = new JWSBuilder().build(token);
	}

	@Override
	public String getSubject() {
		return this.jws.getSubject();
	}

}
