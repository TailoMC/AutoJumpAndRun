package me.Tailo.AutoJumpAndRun.Listener;

import me.Tailo.AutoJumpAndRun.System.main;
import me.Tailo.AutoJumpAndRun.Utils.ColorMenuManager;
import me.Tailo.AutoJumpAndRun.Utils.ConfigManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractEvent_Listener implements Listener {

	private main plugin;

	public PlayerInteractEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if(e.getItem() != null && e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().equals(ConfigManager.colormenuname)) { 
			
			ColorMenuManager.openColorMenu(e.getPlayer());
			
		}
		
	}

}
