package com.randazzo.mario.plantWatering.security.model;

import java.util.List;

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

import com.randazzo.mario.plantWatering.model.Person;
import com.randazzo.mario.plantWatering.security.authentication.JWSToken;


/**
 * This class provides an abstraction point to the Identity Management operations 
 * required by the application.
 * 
 * @author Mario Randazzo
 *
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

        Person newPerson = new Person();
        newPerson.setEmail(request.getEmail());
        newPerson.setFirstName(request.getFirstName());
        newPerson.setLastName(request.getLastName());
        
        User newUser = new User();
        newUser.setLoginName(request.getEmail());
        newUser.setPerson(newPerson);
        
        this.identityManager.add(newUser);
        updatePassword(newUser, request.getPassword());

        return newUser;
    }
    
    public Token activateAccount(User user) {
    	user.setEnabled(true);
    	this.identityManager.update(user);
    	return issueToken(user);
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

    public User findByLoginName(String loginName) {
        return findByLoginName(loginName, this.identityManager);
    }

    private Token issueToken(Account account) {
        return this.tokenProvider.issue(account);
    }
}