package com.itsradiix.commons.data.mariadb;

public class SQLSettings {

	private String host;
	private String port;
	private String user;
	private String password;
	private String database;
	private int maxConnections;

	public SQLSettings(String host, String port, String user, String password, String database, int maxConnections) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
		this.maxConnections = maxConnections;
	}

	public SQLSettings() {}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}
}
