package com.itsradiix.spigot.commands;

import com.itsradiix.spigot.DefinityAPISpigot;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static com.itsradiix.spigot.commands.Command.commands;
import static com.itsradiix.spigot.commands.Command.filteredCommands;

public final class CommandBuilder {

	private static final JavaPlugin plugin = DefinityAPISpigot.getJavaPlugin();

	private String name = "";
	private String description = "";
	private String syntax = "/" + name;
	private String permission = "";
	private boolean allowConsole = false;
	private boolean hideConsoleUsage = false;
	private BiConsumer<CommandSender, String[]> perform = (sender, args) -> {};
	private BiFunction<CommandSender, String[], List<String>> tabListArgs = (sender, args) -> new ArrayList<>();

	public CommandBuilder name(String name) {
		this.name = name;
		return this;
	}

	public CommandBuilder description(String description) {
		this.description = description;
		return this;
	}

	public CommandBuilder syntax(String syntax) {
		this.syntax = syntax;
		return this;
	}

	public CommandBuilder permission(String permission) {
		this.permission = permission;
		return this;
	}

	public CommandBuilder allowConsole(){
		this.allowConsole = true;
		return this;
	}

	public CommandBuilder hideConsoleUsage(){
		this.hideConsoleUsage = true;
		return this;
	}

	public CommandBuilder perform(BiConsumer<CommandSender, String[]> perform){
		this.perform = perform;
		return this;
	}

	public CommandBuilder tabListArguments(BiFunction<CommandSender, String[], List<String>> tabListArgs){
		this.tabListArgs = tabListArgs;
		return this;
	}

	public Command build(){
		Command command = new Command(name, description, syntax, permission, allowConsole, hideConsoleUsage, perform, tabListArgs);
		commands.add(command);
		if (command.hideConsoleUsage){
			filteredCommands.add("/" + name.toLowerCase());
			filteredCommands.add("/" + name.toLowerCase() + " ");
		}
		plugin.getCommand(name).setExecutor(command);
		plugin.getCommand(name).setTabCompleter(command);
		return command;
	}
}
