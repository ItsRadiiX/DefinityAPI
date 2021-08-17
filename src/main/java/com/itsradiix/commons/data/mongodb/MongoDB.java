package com.itsradiix.commons.data.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.IOException;
import java.util.logging.Logger;

public class MongoDB {

	private static MongoDB instance;
	private static Logger logger;

	private MongoClient mongoClient;
	private Morphia morphia;
	private Datastore datastore;
	private MongoCollection<Document> mongoCollection;
	private MongoDatabase database;

	private final String hostName;
	private final int port;
	private final String username;
	private final String password;
	private final String databaseString;

	public MongoDB(String hostName, int port, String username, String password, String databaseString, Logger logger) throws IOException {
		instance = this;
		MongoDB.logger = logger;
		this.hostName = hostName;
		this.port = port;
		this.username = username;
		this.password = password;
		this.databaseString = databaseString;
	}

	public MongoClient getMongoClient() {
		if (mongoClient == null) {
			MongoClientOptions options = MongoClientOptions.builder()
					.connectionsPerHost(4)
					.maxConnectionIdleTime((60 * 1_000))
					.maxConnectionLifeTime((120 * 1_000))
					.build();

			ServerAddress address = new ServerAddress(hostName, port);
			MongoCredential credential = MongoCredential.createCredential(username, databaseString, password.toCharArray());
			logToConsole("&7About to connect to MongoDB @ &6" + address);
			try {
				if (username.isEmpty() || password.isEmpty()) {
					mongoClient = new MongoClient(address, options);
				} else {
					mongoClient = new MongoClient(address, credential, options);
				}
				database = mongoClient.getDatabase(databaseString);
				mongoCollection = database.getCollection("players");
				logToConsole("&aConnected to MongoDB!");
			} catch (Exception e) {
				severeToConsole("&cAn error occurred when connecting to MongoDB");
				e.fillInStackTrace();
			}
		}
		return mongoClient;
	}

	public Morphia getMorphia() {
		if (morphia == null) {
			logToConsole("&7Starting Morphia...");
			morphia = new Morphia();
		}
		return morphia;
	}

	public Datastore getDataStore() {
		if (datastore == null) {
			logToConsole("Starting DataStore on DB: " + databaseString);
			datastore = getMorphia().createDatastore(getMongoClient(), databaseString);
			datastore.ensureIndexes();
		}
		return datastore;
	}

	public void init() {
		logToConsole("&7Bootstrapping MongoDB...");
		getMongoClient();
		getMorphia();
		getDataStore();
	}

	public void close() {
		logToConsole("Closing MongoDB Connection...");
		if (mongoClient != null) {
			try {
				mongoClient.close();
				logToConsole("Nulling the connection dependency objects...");
				mongoClient = null;
				morphia = null;
				datastore = null;
			} catch (Exception e) {
				severeToConsole("An error occurred when closing the MongoDB Connection");
				e.fillInStackTrace();
			}
		} else {
			warnToConsole("Mongo object was null, couldn't close connection!");
		}
	}

	public static MongoDB getInstance() {
		return instance;
	}

	public MongoDatabase getDatabase() {
		return database;
	}

	public MongoCollection<Document> getMongoCollection() {
		return mongoCollection;
	}

	public static void logToConsole(String log){
		logger.info(log);
	}

	public static void warnToConsole(String log){
		logger.warning(log);
	}

	public static void severeToConsole(String log){
		logger.severe(log);
	}
}
