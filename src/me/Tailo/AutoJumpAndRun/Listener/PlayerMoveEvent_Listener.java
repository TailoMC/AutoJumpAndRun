package me.Tailo.AutoJumpAndRun.Listener;

import me.Tailo.AutoJumpAndRun.System.JnR;
import me.Tailo.AutoJumpAndRun.System.main;
import me.Tailo.AutoJumpAndRun.Utils.ConfigManager;
import me.Tailo.AutoJumpAndRun.Utils.LocationManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveEvent_Listener implements Listener {

	private main plugin;

	public PlayerMoveEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(LocationManager.isWorld(p)) {
			if(ConfigManager.startblockid == -1 || p.getLocation().getBlock().getTypeId() == ConfigManager.startblockid) {
				if(ConfigManager.startblock1downid == -1 || p.getLocation().subtract(0, 1, 0).getBlock().getTypeId() == ConfigManager.startblock1downid) {
					new JnR(p);
				}
			}
		}
		
		JnR jnr = JnR.getJnR(p);
		
		if(jnr != null) {
			
			if(jnr.isNewBlock()) {
				if(jnr.to.getY() < ConfigManager.maxheight) {
					jnr.placeNewBlock();
				} else {
					jnr.remove();
				}
			}
			
			if(jnr.isUnderBlock()) {
				jnr.remove();
			}
			
		}
		
	}

}
