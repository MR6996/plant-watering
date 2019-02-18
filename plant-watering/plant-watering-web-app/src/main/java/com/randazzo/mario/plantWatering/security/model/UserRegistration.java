
package com.randazzo.mario.plantWatering.security.model;

public class UserRegistration {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirmation;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public boolean isValid() {
        return this.firstName != null && this.lastName != null && this.email != null && this.password != null && this.passwordConfirmation != null;
    }
}