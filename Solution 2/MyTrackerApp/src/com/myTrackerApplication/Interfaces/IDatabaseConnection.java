package com.myTrackerApplication.Interfaces;

import java.sql.Connection;

public interface IDatabaseConnection extends AutoCloseable{
	Connection getDatabaseConnection();
}
