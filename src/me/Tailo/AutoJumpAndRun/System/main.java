package me.Tailo.AutoJumpAndRun.System;

import me.Tailo.AutoJumpAndRun.Commands.COMMAND_ajar;
import me.Tailo.AutoJumpAndRun.Listener.BlockPlaceEvent_Listener;
import me.Tailo.AutoJumpAndRun.Listener.InventoryClickEvent_Listener;
import me.Tailo.AutoJumpAndRun.Listener.PlayerInteractEvent_Listener;
import me.Tailo.AutoJumpAndRun.Listener.PlayerMoveEvent_Listener;
import me.Tailo.AutoJumpAndRun.Listener.PlayerQuitEvent_Listener;
import me.Tailo.AutoJumpAndRun.Utils.ConfigManager;
import me.Tailo.AutoJumpAndRun.Utils.LocationManager;
import me.Tailo.AutoJumpAndRun.Utils.NMSUtils;
import me.Tailo.AutoJumpAndRun.Utils.SoundManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	
	public static String prefix = "§7[§eAutoJumpAndRun§7] ";
	
	public static main instance;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		new PlayerMoveEvent_Listener(this);
		new PlayerQuitEvent_Listener(this);
		new PlayerInteractEvent_Listener(this);
		new InventoryClickEvent_Listener(this);
		new BlockPlaceEvent_Listener(this);
		
		getCommand("ajar").setExecutor(new COMMAND_ajar(this));
		
		new NMSUtils();
		
		saveDefaultConfig();
		ConfigManager.loadConfig(getConfig(), getDataFolder());
		
		SoundManager.loadSounds();
		
		LocationManager.loadSpawn();
		
	}
	
	@Override
	public void onDisable() {
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			
			JnR jnr = JnR.getJnR(players);
			
			if(jnr != null) {
				
				jnr.remove();
				
			}
			
		}
		
	}
	
}
