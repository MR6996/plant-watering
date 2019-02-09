package com.randazzo.mario.plantWatering.security;

import static com.randazzo.mario.plantWatering.security.model.IdentityModelManager.findByLoginName;
import static org.picketlink.idm.model.basic.BasicModel.getRole;
import static org.picketlink.idm.model.basic.BasicModel.grantRole;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.picketlink.event.PartitionManagerCreateEvent;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.config.SecurityConfigurationException;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.Attribute;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.Role;

import com.randazzo.mario.plantWatering.security.model.ApplicationRole;
import com.randazzo.mario.plantWatering.security.model.User;


@Stateless
public class SecurityInitializer {

    public static final String KEYSTORE_FILE_PATH = "/keystore.jks";

    private KeyStore keyStore;

    public void configureDefaultPartition(@Observes PartitionManagerCreateEvent event) {
        PartitionManager partitionManager = event.getPartitionManager();

        createDefaultPartition(partitionManager);
        createDefaultRoles(partitionManager);
        createAdminAccount(partitionManager);
    }

    private void createDefaultRoles(PartitionManager partitionManager) {
        IdentityManager identityManager = partitionManager.createIdentityManager();

        createRole(ApplicationRole.ADMINISTRATOR, identityManager);
        createRole(ApplicationRole.USER, identityManager);
    }

    private void createDefaultPartition(PartitionManager partitionManager) {
        Realm partition = partitionManager.getPartition(Realm.class, Realm.DEFAULT_REALM);

        if (partition == null) {
            try {
                partition = new Realm(Realm.DEFAULT_REALM);

                partition.setAttribute(new Attribute<byte[]>("PublicKey", getPublicKey()));
                partition.setAttribute(new Attribute<byte[]>("PrivateKey", getPrivateKey()));

                partitionManager.add(partition);
            } catch (Exception e) {
                throw new SecurityConfigurationException("Could not create default partition.", e);
            }
        }
    }

    public static Role createRole(String roleName, IdentityManager identityManager) {
        Role role = getRole(identityManager, roleName);

        if (role == null) {
            role = new Role(roleName);
            identityManager.add(role);
        }

        return role;
    }

    private byte[] getPrivateKey() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        return getKeyStore().getKey("servercert", "test123".toCharArray()).getEncoded();
    }

    private byte[] getPublicKey() throws KeyStoreException {
        return getKeyStore().getCertificate("servercert").getPublicKey().getEncoded();
    }

    private KeyStore getKeyStore() {
        if (this.keyStore == null) {
            try {
                this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                getKeyStore().load(getClass().getResourceAsStream(KEYSTORE_FILE_PATH), "store123".toCharArray());
            } catch (Exception e) {
                throw new SecurityException("Could not load key store.", e);
            }
        }

        return this.keyStore;
    }

    public void createAdminAccount(PartitionManager partitionManager) {
        IdentityManager identityManager = partitionManager.createIdentityManager();
        String email = "admin@picketlink.org";

        // if admin exists dont create again
        if(findByLoginName(email, identityManager) != null) {
            return;
        }


        User admin = new User(email);

        identityManager.add(admin);

        identityManager.updateCredential(admin, new Password("admin"));

        Role adminRole = getRole(identityManager, ApplicationRole.ADMINISTRATOR);

        grantRole(partitionManager.createRelationshipManager(), admin, adminRole);
    }
}