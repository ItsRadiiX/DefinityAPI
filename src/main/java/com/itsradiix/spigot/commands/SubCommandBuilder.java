package com.itsradiix.spigot.commands;

import com.itsradiix.commons.data.messages.ColorTranslator;
import com.itsradiix.commons.data.messages.Messages;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class SubCommandBuilder {

	protected static final Messages messages = Messages.getInstance();

	protected String name = "";
	protected String description = "";
	protected String syntax = "/" + name;
	protected String permission = "";
	protected boolean allowConsole = false;
	protected boolean hideConsoleUsage = false;
	protected boolean hideTabComplete = false;
	protected BiConsumer<CommandSender, String[]> perform = (sender, args) -> {};
	protected BiFunction<CommandSender, String[], List<String>> tabListArgs = (sender, args) -> new ArrayList<>();

	public SubCommandBuilder name(String name) {
		this.name = name;
		return this;
	}

	public SubCommandBuilder description(String description) {
		this.description = description;
		return this;
	}

	public SubCommandBuilder syntax(String syntax) {
		this.syntax = syntax;
		return this;
	}

	public SubCommandBuilder permission(String permission) {
		this.permission = permission;
		return this;
	}

	public SubCommandBuilder allowConsole(){
		this.allowConsole = true;
		return this;
	}

	public SubCommandBuilder hideConsoleUsage(){
		this.hideConsoleUsage = true;
		return this;
	}

	public SubCommandBuilder hideTabComplete(){
		this.hideTabComplete = true;
		return this;
	}

	public SubCommandBuilder perform(BiConsumer<CommandSender, String[]> perform){
		this.perform = perform;
		return this;
	}

	public SubCommandBuilder tabListArguments(BiFunction<CommandSender, String[], List<String>> tabListArgs){
		this.tabListArgs = tabListArgs;
		return this;
	}

	public SubCommand build(){
		return new SubCommand(name, description, syntax, permission, allowConsole, hideConsoleUsage, hideTabComplete, perform, tabListArgs);
	}

	public SubCommand buildHelpCommand(Command baseCommand){
		return new SubCommand(
				"help",
				"Displays this help message",
				"/" + baseCommand.name + " help",
				baseCommand.permission + ".help",
				true,
				false,
				false,
				((sender, args) -> {
					sender.sendMessage(ColorTranslator.translateColorCodes(messages.getMessage(Messages.getLanguageID(),"helpHeader").replaceAll("%command_name%", baseCommand.name)));

					List<SubCommand> subCommands = baseCommand.subCommands;
					sender.sendMessage(ColorTranslator.translateColorCodes("&b" + baseCommand.syntax + " &7-&r " + baseCommand.description));
					for (SubCommand subCommand : subCommands) {
						if (sender.hasPermission(subCommand.getPermission())) {
							sender.sendMessage(ColorTranslator.translateColorCodes("&b" +
									subCommand.getSyntax() +
									" &7-&r " +
									subCommand.getDescription()));
						}
					}

					sender.sendMessage(ColorTranslator.translateColorCodes(messages.getMessage(Messages.getLanguageID(),"helpFooter") + "\n"));
					if (sender instanceof Player){
						Player player = (Player) sender;
						player.playSound(player.getLocation(), Sound.ENTITY_EGG_THROW, 0.2F, 1.0F);
					}
				}),
				((sender, args) -> new ArrayList<>()));
	}

}
