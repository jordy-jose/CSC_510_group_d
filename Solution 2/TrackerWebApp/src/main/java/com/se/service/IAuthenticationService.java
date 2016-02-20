package com.se.service;

import com.se.controller.Credentials;

import javax.naming.AuthenticationException;

/**
 * Created by drrakeshkumarsharma on 15/02/16.
 */
public interface IAuthenticationService {
    /**
     * Authenticates a user against its credentials
     *
     * @param userCredentials login and password
     * @return user's identity
     * @throws AuthenticationException raised if authentication failed.
     */
    String authenticate(Credentials userCredentials) throws AuthenticationException;
}
