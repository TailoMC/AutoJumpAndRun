package me.Tailo.AutoJumpAndRun.Utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import me.Tailo.AutoJumpAndRun.System.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LocationManager {

	private static Location spawn;
	
	public static void loadSpawn() {
		
		if(ConfigManager.usespawn) {
			
			try {
				
				File file = new File("plugins/AutoJumpAndRun", "locs.yml");
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				
				String world = cfg.getString("spawn.world");
				double x = cfg.getDouble("spawn.X");
				double y = cfg.getDouble("spawn.Y");
				double z = cfg.getDouble("spawn.Z");
				double yaw = cfg.getDouble("spawn.Yaw");
				double pitch = cfg.getDouble("spawn.Pitch");
				
				Location loc = new Location(Bukkit.getWorld(world), x, y, z);
				loc.setYaw((float) yaw);
				loc.setPitch((float) pitch);
				
				spawn = loc;
				
			} catch(Exception e) {
				Bukkit.getLogger().log(Level.WARNING, "[AutoJumpAndRun] No spawn was set so no spawn would be used!");
			}
			
		}
		
	}
	
	public static void setSpawn(Player p) {
		
		try {
			
			File file = new File("plugins/AutoJumpAndRun", "locs.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			
			Location loc = p.getLocation();
			
			cfg.set("spawn.world", loc.getWorld().getName());
			cfg.set("spawn.X", loc.getX());
			cfg.set("spawn.Y", loc.getY());
			cfg.set("spawn.Z", loc.getZ());
			cfg.set("spawn.Yaw", loc.getYaw());
			cfg.set("spawn.Pitch", loc.getPitch());
			
			cfg.save(file);
			spawn = loc;
			
			p.sendMessage(main.prefix + "§aYou successfully set the spawn!");
			
		} catch (IOException e) {
			p.sendMessage(main.prefix + "§cThe spawn could't be saved. Please try again.");
			e.printStackTrace();
		}
		
	}
	
	public static Location getSpawn() {
		return spawn;
	}
	
	public static boolean isWorld(Player p) {
		
		World w = Bukkit.getWorld(ConfigManager.world);
		
		if(w != null) return w == p.getWorld();
		
		return false;
		
	}
	
}
