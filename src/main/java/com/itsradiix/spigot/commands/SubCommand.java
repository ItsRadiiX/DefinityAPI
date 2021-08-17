package com.itsradiix.spigot.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * SubCommand class for creating Spigot SubCommands.
 * To create a new SubCommand, use the Builder class.
 */
public class SubCommand {


	// Important Command variables
	protected Command baseCommand;
	protected String name;
	protected String description;
	protected String syntax;
	protected String permission;
	protected boolean allowConsole;
	protected boolean hideConsoleUsage;
	protected boolean hideTabComplete;
	protected BiConsumer<CommandSender, String[]> perform;
	protected BiFunction<CommandSender, String[], List<String>> tabListArgs;

	/**
	 * Default SubCommand Constructor
	 * @param name SubCommand name
	 * @param description SubCommand description
	 * @param syntax SubCommand syntax
	 * @param permission SubCommand permission
	 * @param allowConsole Is Console allowed to use SubCommand
	 * @param hideConsoleUsage Hide SubCommand in Console
	 * @param perform SubCommand perform runnable
	 * @param tabListArgs SubCommand tabListArgs runnable
	 */
	public SubCommand(String name, String description, String syntax, String permission, boolean allowConsole, boolean hideConsoleUsage, boolean hideTabComplete, BiConsumer<CommandSender, String[]> perform, BiFunction<CommandSender, String[], List<String>> tabListArgs){
		this.name = name;
		this.description = description;
		this.syntax = syntax;
		this.permission = permission;
		this.allowConsole = allowConsole;
		this.hideConsoleUsage = hideConsoleUsage;
		this.hideTabComplete = hideTabComplete;
		this.perform = perform;
		this.tabListArgs = tabListArgs;
	}

	/**
	 * Set Base Command of SubCommand
	 * @param baseCommand
	 */
	public void setBaseCommand(Command baseCommand) {
		this.baseCommand = baseCommand;
	}

	/**
	 * @return Command returns base command
	 */
	public Command getBaseCommand() {
		return baseCommand;
	}

	/**
	 * @return String returns SubCommand name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return String returns SubCommand description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return String returns SubCommand syntax
	 */
	public String getSyntax() {
		return syntax;
	}

	/**
	 * @return String returns SubCommand permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @return boolean returns if Console is allowed to use SubCommand
	 */
	public boolean isAllowConsole() {
		return allowConsole;
	}

	/**
	 * @return boolean returns if SubCommand is hidden from console
	 */
	public boolean isHideConsoleUsage() {
		return hideConsoleUsage;
	}

	/**
	 * @return boolean returns if SubCommand is hidden from Tab Complete
	 */
	public boolean isHideTabComplete() {
		return hideTabComplete;
	}

	/**
	 * Perform the command runnable
	 * @param sender CommandSender of issued command.
	 * @param args Arguments of issued command.
	 */
	public void perform(CommandSender sender, String[] args){
		perform.accept(sender, args);
	}

	/**
	 * Get tab list args and filter them based on args input
	 * @param args Arguments of issued command.
	 * @return String[] returns Array of arguments.
	 */
	public List<String> getTabListArgs(CommandSender sender, String[] args){
		List<String> tmp = new ArrayList<>();
		List<String> tabArgs = tabListArgs.apply(sender, args);
		for (String s : tabArgs){
			if (s.toLowerCase().startsWith(args[(args.length-1)].toLowerCase())){
				tmp.add(s);
			}
		}
		return tmp;
	}
}
