package com.randazzo.mario.plantWatering.service;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import org.picketlink.authorization.annotations.LoggedIn;

@Path("/private/user")
@Stateless
@LoggedIn
public class UserREST {

}
