package me.Tailo.AutoJumpAndRun.Utils;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SoundManager {
	
	static org.bukkit.Sound spawn;
	static org.bukkit.Sound fall;
	static org.bukkit.Sound colorpick;
	
	public static void loadSounds() {
		
		for(org.bukkit.Sound s : org.bukkit.Sound.values()) {
			
			if(ConfigManager.spawnsound.equals(s.name())) spawn = s;
			
			if(ConfigManager.fallsound.equals(s.name())) fall = s;
			
			if(ConfigManager.colorpicksound.equals(s.name())) colorpick = s;
			
		}
		
		if(spawn == null) {			
			Bukkit.getLogger().log(Level.WARNING, "[AutoJumpAndRun] Sound '" + ConfigManager.spawnsound + "' couldn't be found for version '" + Bukkit.getBukkitVersion() + "'. The plugin will use no sound!");			
		}
		
		if(fall == null) {
			Bukkit.getLogger().log(Level.WARNING, "[AutoJumpAndRun] Sound '" + ConfigManager.fallsound + "' couldn't be found for version '" + Bukkit.getBukkitVersion() + "'. The plugin will use no sound!");
		}
		
		if(colorpick == null) {
			Bukkit.getLogger().log(Level.WARNING, "[AutoJumpAndRun] Sound '" + ConfigManager.colorpicksound + "' couldn't be found for version '" + Bukkit.getBukkitVersion() + "'. The plugin will use no sound!");
		}
		
	}
	
	public static void playSound(Player p, Sound s) {
		
		if(s == Sound.FALL && fall != null) {
			
			p.playSound(p.getLocation(), fall, 1.0F, 1.0F);
			
		}
		if(s == Sound.SPAWN && spawn != null) {
			
			p.playSound(p.getLocation(), spawn, 1.0F, 1.0F);
			
		}
		if(s == Sound.COLOR_PICK && colorpick != null) {
			
			p.playSound(p.getLocation(), colorpick, 1.0F, 1.0F);
			
		}
		
	}
	
	public static enum Sound {
		
		SPAWN, FALL, COLOR_PICK
		
	}
	
}
