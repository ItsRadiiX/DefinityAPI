package com.itsradiix.bungee;

import com.itsradiix.commons.DefinityAPIUtils;
import net.md_5.bungee.api.plugin.Plugin;

public class DefinityAPIBungee {

	public static Plugin plugin;

	public DefinityAPIBungee(Plugin plugin){
		DefinityAPIBungee.plugin = plugin;
		DefinityAPIUtils.setClassLoader(plugin.getClass().getClassLoader());
		DefinityAPIUtils.setLogger(plugin.getLogger());
	}
}
