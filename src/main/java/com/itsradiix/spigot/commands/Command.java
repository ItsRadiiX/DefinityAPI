package com.itsradiix.spigot.commands;

import com.itsradiix.commons.data.messages.Messages;
import com.itsradiix.commons.DefinityAPIUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Command implements CommandExecutor, TabExecutor {

	// Important static variables
	protected static final Messages messages = Messages.getInstance();
	protected static final ClassLoader classLoader = DefinityAPIUtils.getClassLoader();
	public static final List<Command> commands = new ArrayList<>();
	public static final List<String> filteredCommands = new ArrayList<>();

	// Important Command variables
	protected String name;
	protected String description;
	protected String syntax;
	protected String permission;
	protected boolean allowConsole;
	protected boolean hideConsoleUsage;
	protected BiConsumer<CommandSender, String[]> perform;
	protected BiFunction<CommandSender, String[], List<String>> tabListArgs;
	protected List<SubCommand> subCommands = new ArrayList<>();

	/**
	 * Default Command Constructor
	 * @param name Command name
	 * @param description Command description
	 * @param syntax Command syntax
	 * @param permission Command permission
	 * @param allowConsole Is Console allowed to use Command
	 * @param hideConsoleUsage Hide Command in Console
	 * @param perform Command perform runnable
	 * @param tabListArgs Command tabListArgs runnable
	 */
	public Command(String name, String description, String syntax, String permission, boolean allowConsole, boolean hideConsoleUsage, BiConsumer<CommandSender, String[]> perform, BiFunction<CommandSender, String[], List<String>> tabListArgs){
		this.name = name;
		this.description = description;
		this.syntax = syntax;
		this.permission = permission;
		this.allowConsole = allowConsole;
		this.hideConsoleUsage = hideConsoleUsage;
		this.perform = perform;
		this.tabListArgs = tabListArgs;
	}

	/**
	 * Add SubCommand to Command
	 * @param subCommand SubCommand
	 */
	public void addSubCommand(SubCommand subCommand){
		subCommand.setBaseCommand(this);
		subCommands.add(subCommand);
		if (subCommand.isHideConsoleUsage()){
			filteredCommands.add("/" + subCommand.getBaseCommand().name.toLowerCase() + " " + subCommand.getName().toLowerCase());
			filteredCommands.add("/" + subCommand.getBaseCommand().name.toLowerCase() + " " + subCommand.getName().toLowerCase() + " ");
		}
	}

	/**
	 * Perform the command runnable
	 * @param sender CommandSender of issued command.
	 * @param args Arguments of issued command.
	 */
	private void perform(CommandSender sender, String[] args){
		perform.accept(sender, args);
	}

	/**
	 * Send SubArgs minus the subcommand name.
	 * This is used to send args to SubCommand.
	 * @param args Arguments of issued command.
	 * @return String[] returns Array of arguments.
	 */
	private String[] sendSubArgs(String[] args){
		if (args.length > 1) {
			return Arrays.copyOfRange(args, 1, args.length);
		} else {
			return new String[0];
		}
	}

	/**
	 * Get all Command which are filtered from console as array
	 * @return String[] returns Array of filtered Commands
	 */
	private static String[] getFilteredCommandsArray() {
		String[] tmp = new String[filteredCommands.size()];
		filteredCommands.toArray(tmp);
		return tmp;
	}

	/**
	 * @return List returns filtered commands list
	 */
	public static List<String> getFilteredCommands() {
		return filteredCommands;
	}

	/**
	 * @return List return commands list
	 */
	public static List<Command> getCommands() {
		return commands;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (sender.hasPermission(permission)){
			if (!subCommands.isEmpty() && args.length > 0){
				for (SubCommand s : subCommands){
					if (args[0].equalsIgnoreCase(s.getName())){
						if (!(sender instanceof Player)){
							if (!s.isAllowConsole()){
								sender.sendMessage(messages.getMessage(Messages.getLanguageID(),"noConsole", "&cConsole is not allowed to use this command!"));
								return true;
							}
						}
						if (sender.hasPermission(s.getPermission())){
							s.perform(sender, sendSubArgs(args));
						} else {
							sender.sendMessage(messages.getMessage(Messages.getLanguageID(),"noPermission", "&cYou do not have permission to use this command!"));
						}
						return true;
					}
				}
			}
			if (!(sender instanceof Player)){
				if (!allowConsole){
					sender.sendMessage(messages.getMessage(Messages.getLanguageID(),"noConsole", "&cConsole is not allowed to use this command!"));
					return true;
				}
			}
			perform(sender, args);
		} else {
			sender.sendMessage(messages.getMessage(Messages.getLanguageID(),"noPermission", "&cYou do not have permission to use this command!"));
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		List<String> subArgs = new ArrayList<>(tabListArgs.apply(sender, args));
		if (!subCommands.isEmpty()) {
			if (args.length < 2) {
				for (SubCommand s : subCommands) {
					if (sender.hasPermission(s.getPermission()) && !s.isHideTabComplete()) {
						if (s.getName().toLowerCase().startsWith(args[0].toLowerCase())){
							subArgs.add(s.getName());
						}
					}
				}
				return subArgs;
			} else {
				for (SubCommand s : subCommands){
					if (args[0].equalsIgnoreCase(s.getName()) && sender.hasPermission(s.getPermission()) && !s.isHideTabComplete()){
						return s.getTabListArgs(sender, sendSubArgs(args));
					}
				}
			}
		}
		return subArgs;
	}


}
