package com.se.DatabaseConnectionHelper;

import org.mindrot.jbcrypt.BCrypt;

import com.se.Interfaces.IHashService;

class HashService implements IHashService{

    @Override
    public String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public Boolean isMatch(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }
    
}
