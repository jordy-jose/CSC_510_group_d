package com.myTrackerApplication.DatabaseConnectionHelper;

import com.myTrackerApplication.Interfaces.IHashService;
import org.mindrot.BCrypt;

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
