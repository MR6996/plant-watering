package com.randazzo.mario.plantWatering.security.authentication;

import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Token;
import org.picketlink.idm.credential.storage.TokenCredentialStorage;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.json.jose.JWSBuilder;

@Stateless
public class JWSTokenProvider implements Token.Provider<JWSToken> {

	@Inject
	PartitionManager partitionManager;

	@Override
	public JWSToken issue(Account account) {
		TokenCredentialStorage tokenStorage = getIdentityManager().retrieveCurrentCredential(account,
				TokenCredentialStorage.class);
		JWSToken token;

		if (tokenStorage == null) {
			JWSBuilder builder = new JWSBuilder();
			byte[] privateKey = getPrivateKey();

			builder
				.id(UUID.randomUUID().toString())
				.rsa256(privateKey)
				.issuer(account.getPartition().getName())
				.issuedAt(getCurrentTime())
				.subject(account.getId())
				.expiration(getCurrentTime() + (5*60))
				.notBefore(getCurrentTime());

			token = new JWSToken(builder.build().encode());
			getIdentityManager().updateCredential(account, token);
		} else {
			token = new JWSToken(tokenStorage.getToken());
		}

		return token;
	}

	@Override
	public JWSToken renew(Account account, JWSToken renewToken) {
		return issue(account);
	}

	@Override
	public void invalidate(Account account) {
		getIdentityManager().removeCredential(account, TokenCredentialStorage.class);
	}

	@Override
	public Class<JWSToken> getTokenType() {
		return JWSToken.class;
	}

	private byte[] getPrivateKey() {
		return getPartition().<byte[]>getAttribute("PrivateKey").getValue();
	}

	private int getCurrentTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	private Realm getPartition() {
		return partitionManager.getPartition(Realm.class, Realm.DEFAULT_REALM);
	}

	private IdentityManager getIdentityManager() {
		return this.partitionManager.createIdentityManager(getPartition());
	}

}
