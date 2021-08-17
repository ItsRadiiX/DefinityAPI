package com.itsradiix.commons;

import java.util.logging.Logger;

public class DefinityAPIUtils {

	private static ClassLoader classLoader;
	private static Logger logger;

	public static void setClassLoader(ClassLoader classLoader) {
		DefinityAPIUtils.classLoader = classLoader;
	}

	public static void setLogger(Logger logger) {
		DefinityAPIUtils.logger = logger;
	}

	public static ClassLoader getClassLoader() {
		return classLoader;
	}

	public static Logger getLogger() {
		return logger;
	}
}
