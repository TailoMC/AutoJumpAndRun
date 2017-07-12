package me.Tailo.AutoJumpAndRun.Listener;

import me.Tailo.AutoJumpAndRun.System.main;
import me.Tailo.AutoJumpAndRun.Utils.ConfigManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceEvent_Listener implements Listener {

	private main plugin;

	public BlockPlaceEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		if(ConfigManager.colormenu && e.getItemInHand().getItemMeta().getDisplayName() != null && e.getItemInHand().getItemMeta().getDisplayName().equals(ConfigManager.colormenuname)) {
			e.setCancelled(true);			
		}
		
	}

}
