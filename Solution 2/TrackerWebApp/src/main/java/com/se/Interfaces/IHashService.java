package com.se.Interfaces;

public interface IHashService {
	String getHash(String password);
    Boolean isMatch(String password, String passwordHash);
}
