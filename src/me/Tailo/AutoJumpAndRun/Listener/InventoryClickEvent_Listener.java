package me.Tailo.AutoJumpAndRun.Listener;

import me.Tailo.AutoJumpAndRun.System.JnR;
import me.Tailo.AutoJumpAndRun.System.main;
import me.Tailo.AutoJumpAndRun.Utils.ColorMenuManager;
import me.Tailo.AutoJumpAndRun.Utils.ConfigManager;
import me.Tailo.AutoJumpAndRun.Utils.SoundManager;
import me.Tailo.AutoJumpAndRun.Utils.SoundManager.Sound;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickEvent_Listener implements Listener {

	private main plugin;

	public InventoryClickEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if(e.getCurrentItem() != null) {
			
			Player p = (Player) e.getWhoClicked();
			
			if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(ConfigManager.colormenuname)) {
				e.setCancelled(true);
			}
			
			if(e.getClickedInventory().getTitle().equals(ConfigManager.colormenuname)) {
				
				e.setCancelled(true);
				
				JnR jnr = JnR.getJnR(p);
				
				if(jnr != null) {
					
					jnr.color = e.getCurrentItem().getData().getData();
					
					SoundManager.playSound(p, Sound.COLOR_PICK);
					
					ColorMenuManager.removeColorMenu(p);
					ColorMenuManager.giveColorMenu(p);
					
					p.closeInventory();
					
				}
				
			}
			
		}
		
	}

}
