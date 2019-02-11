package com.randazzo.mario.plantWatering.security.model;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.credential.Token;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.IdentityQueryBuilder;

import com.randazzo.mario.plantWatering.security.authentication.JWSToken;

/**
 * <p>This class provides an abstraction point to the Identity Management operations required by the application./p>
 *
 * <p>The main objective of this class is avoid the spread use of the <code>IdentityManager</code> by different components of
 * the application and code duplication, providing a centralized point of access for the most common operations like create/update/query users and so forth.</p>
 *
 * <p>Also it is very useful to understand how PicketLink Identity Management is being used and what is being used by the application from a IDM perspective.</p>
 *
 * <p>Please note that PicketLink IDM provides a very flexible and poweful identity model and API, from which you can extend and fulfill your own requirements.</p>
 *
 * @author Pedro Igor
 */
@Stateless
public class IdentityModelManager {

    @Inject
    private IdentityManager identityManager;

    @Inject
    private RelationshipManager relationshipManager;

    @Inject
    private Token.Provider<JWSToken> tokenProvider;

    public static User findByLoginName(String loginName, IdentityManager identityManager) {
        if (loginName == null) {
            throw new IllegalArgumentException("Invalid login name.");
        }

        IdentityQueryBuilder queryBuilder = identityManager.getQueryBuilder();
        IdentityQuery<User> query = queryBuilder.createIdentityQuery(User.class);

        query.where(queryBuilder.equal(User.USER_NAME, loginName));

        List<User> result = query.getResultList();

        if (!result.isEmpty()) {
            return result.get(0);
        }

        return null;
    }

    public User createAccount(UserRegistration request) {
        if (!request.isValid()) {
            throw new IllegalArgumentException("Insuficient information.");
        }

        User newUser = new User(request.getEmail());
        String activationCode = UUID.randomUUID().toString();
        newUser.setActivationCode(activationCode); // we set an activation code for future use.

        this.identityManager.add(newUser);

        updatePassword(newUser, request.getPassword());

        disableAccount(newUser);

        return newUser;
    }

    public void updatePassword(Account account, String password) {
        this.identityManager.updateCredential(account, new Password(password));
    }

    public void grantRole(User account, String roleName) {
        Role storedRole = BasicModel.getRole(this.identityManager, roleName);
        BasicModel.grantRole(this.relationshipManager, account, storedRole);
    }

    public boolean hasRole(User account, String roleName) {
        Role storedRole = BasicModel.getRole(this.identityManager, roleName);
        return BasicModel.hasRole(this.relationshipManager, account, storedRole);
    }

    public Token activateAccount(String activationCode) {
        User user = findUserByActivationCode(activationCode);

        if (user == null) {
            throw new IllegalArgumentException("Invalid activation code.");
        }

        user.setEnabled(true);
        user.invalidateActivationCode();

        this.identityManager.update(user);

        return issueToken(user);
    }

    public User findByLoginName(String loginName) {
        return findByLoginName(loginName, this.identityManager);
    }

    public User findUserByActivationCode(String activationCode) {
        if (activationCode == null) {
            throw new IllegalArgumentException("Invalid activation code.");
        }

        IdentityQueryBuilder queryBuilder = identityManager.getQueryBuilder();
        IdentityQuery<User> query = queryBuilder.createIdentityQuery(User.class);
        List<User> result = query
            .where(queryBuilder.equal(User.ACTIVATION_CODE, activationCode.replaceAll("\"", "")))
            .getResultList();

        if (!result.isEmpty()) {
            return result.get(0);
        }

        return null;
    }

    public void disableAccount(User user) {
        if (hasRole(user, ApplicationRole.ADMINISTRATOR)) {
            throw new IllegalArgumentException("Administrators can not be disabled.");
        }

        user.setEnabled(false);

        if (user.getId() != null) {
            issueToken(user); // we invalidate the current token and create a new one. so any token stored by clients will be no longer valid.
            this.identityManager.update(user);
        }
    }

    public void enableAccount(User user) {
        if (hasRole(user, ApplicationRole.ADMINISTRATOR)) {
            throw new IllegalArgumentException("Administrators can not be enabled.");
        }

        user.setEnabled(true);
        user.invalidateActivationCode();

        if (user.getId() != null) {
            this.identityManager.update(user);
        }


    }

    private Token issueToken(Account account) {
        return this.tokenProvider.issue(account);
    }
}