package me.Tailo.AutoJumpAndRun.Listener;

import me.Tailo.AutoJumpAndRun.System.JnR;
import me.Tailo.AutoJumpAndRun.System.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvent_Listener implements Listener {

	private main plugin;

	public PlayerQuitEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		JnR jnr = JnR.getJnR(p);
		
		if(jnr != null) {
			
			jnr.remove();
			
		}
		
	}

}
