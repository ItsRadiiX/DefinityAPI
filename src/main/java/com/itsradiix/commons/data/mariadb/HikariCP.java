package com.itsradiix.commons.data.mariadb;

import com.itsradiix.commons.DefinityAPIUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HikariCP {

	private final ClassLoader classLoader = DefinityAPIUtils.getClassLoader();
	private final Logger logger = DefinityAPIUtils.getLogger();
	private DataSource source;

	public HikariCP(SQLSettings settings) throws SQLException, IOException {
		setSettings(settings);
		testDataSource(source);
		initDB();
	}

	public void setSettings(SQLSettings settings){
		Properties props = new Properties();

		props.setProperty("dataSourceClassName", "org.mariadb.jdbc.MariaDbDataSource");
		props.setProperty("dataSource.serverName", settings.getHost());
		props.setProperty("dataSource.portNumber", settings.getPort());
		props.setProperty("dataSource.user", settings.getUser());
		props.setProperty("dataSource.password", settings.getPassword());
		props.setProperty("dataSource.databaseName", settings.getDatabase());

		HikariConfig config = new HikariConfig(props);

		config.setMaximumPoolSize(settings.getMaxConnections());

		source = new HikariDataSource(config);
	}

	public void testDataSource(DataSource source) throws SQLException {
		try (Connection conn = source.getConnection()) {
			if (!conn.isValid(1000)){
				throw new SQLException("Could not establish database connection.");
			}
		}
	}

	public DataSource getDataSource() {
		return source;
	}

	private void initDB() throws SQLException, IOException {
		String setup;
		try (InputStream in = classLoader.getResourceAsStream("setup.sql")){
			setup = new String(in.readAllBytes());
		} catch (NullPointerException | IOException e) {
			logger.log(Level.SEVERE, "Could not read DefinityAPI Database setup file.", e);
			throw e;
		}

		String[] queries = setup.split(";");
		for(String query : queries) {
			if (query.isBlank()) continue;
			try (Connection conn = source.getConnection();
			     PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.execute();
			}
		}

		logger.info("DefinityAPI Database setup complete.");
	}
}
