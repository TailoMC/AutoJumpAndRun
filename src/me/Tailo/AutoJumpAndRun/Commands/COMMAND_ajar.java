package me.Tailo.AutoJumpAndRun.Commands;

import me.Tailo.AutoJumpAndRun.System.main;
import me.Tailo.AutoJumpAndRun.Utils.ConfigManager;
import me.Tailo.AutoJumpAndRun.Utils.LocationManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class COMMAND_ajar implements CommandExecutor {

	private main plugin;

	public COMMAND_ajar(main main) {
		this.plugin = main;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.isOp()) {
				
				if(args.length == 1) {
					
					if(args[0].equalsIgnoreCase("setspawn")) {
						
						LocationManager.setSpawn(p);
						
					} else if(args[0].equalsIgnoreCase("reload")) {
						
						ConfigManager.loadConfig(main.instance.getConfig(), main.instance.getDataFolder());
						
						p.sendMessage(main.prefix + "§aThe config was successfully reloaded!");
						
					} else {
						p.sendMessage(plugin.prefix + "§7/ajar setspawn §e - Set spawnlocation");
						p.sendMessage(plugin.prefix + "§7/ajar reload §e - Reload config");
					}
					
				} else {
					p.sendMessage(plugin.prefix + "§7/ajar setspawn §e - Set spawnlocation");
					p.sendMessage(plugin.prefix + "§7/ajar reload §e - Reload config");
				}
				
			} else {
				p.sendMessage(plugin.prefix + "§cNo permission!");
			}
			
		} else {
			sender.sendMessage("[AutoJumpAndRun] This command can only be executed by a player.");
		}
		
		return true;
	}

}
