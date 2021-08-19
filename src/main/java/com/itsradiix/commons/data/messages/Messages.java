package com.itsradiix.commons.data.messages;

import com.itsradiix.commons.DefinityAPIUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Messages {

	private static Messages instance;

	private final ClassLoader classLoader = DefinityAPIUtils.getClassLoader();
	private final Logger logger = DefinityAPIUtils.getLogger();
	private DataSource source;

	public Map<Integer, Language> languageMap = new HashMap<>();

	public Messages(DataSource source){
		instance = this;
	}

	public void setMessage(int id, String var, String msg){
		languageMap.get(id).getMessages().put(var, msg);
	}

	public String getMessage(int id, String msg){
		return ColorTranslator.translateColorCodes(languageMap.get(id).getMessages().get(msg));
	}

	public String getMessage(int id, String msg, String def){
		String message = languageMap.get(id).getMessages().get(msg);
		if (message == null || message.isBlank()){
			message = def;
		}
		return ColorTranslator.translateColorCodes(message);

	}

	public static int getLanguageID(){
		return 1;
	}

	public static Messages getInstance() {
		return instance;
	}

}
