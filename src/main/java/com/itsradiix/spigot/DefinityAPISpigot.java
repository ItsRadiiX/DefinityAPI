package com.itsradiix.spigot;

import com.itsradiix.commons.DefinityAPIUtils;
import com.itsradiix.spigot.menus.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DefinityAPISpigot {

	private static JavaPlugin javaPlugin;

	public DefinityAPISpigot(JavaPlugin javaPlugin){
		DefinityAPISpigot.javaPlugin = javaPlugin;
		DefinityAPIUtils.setClassLoader(javaPlugin.getClass().getClassLoader());
		DefinityAPIUtils.setLogger(javaPlugin.getLogger());
		MenuManager.setup(javaPlugin.getServer(), javaPlugin);
	}

	public static JavaPlugin getJavaPlugin() {
		return javaPlugin;
	}
}
