package me.Tailo.AutoJumpAndRun.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

	public static List<Byte> colors;
	public static int toblockid;
	public static int fromblockid;
	public static int startradius;
	public static int maxstartheight;
	public static int minstartheight;
	public static int maxdistancenormal;
	public static int maxdistancey1;
	public static int mindistancenormal;
	public static int mindistancey1;
	public static int removedistance;
	public static int chanceyup;
	public static int startblockid;
	public static int startblock1downid;
	public static int countdown;
	public static int maxheight;
	public static int colormenuslot;
	public static String fallsound;
	public static String spawnsound;
	public static String elapsedtimecolor;
	public static String lefttimecolor;
	public static String colormenuname;
	public static String colorpicksound;
	public static String world;
	public static boolean usespawn;
	public static boolean xpbarcount;
	public static boolean colormenu;
	
	public static void loadConfig(FileConfiguration cfg, File file) {
		
		saveDefaultSounds(cfg, file.listFiles()[0]);
		
		colors = cfg.getByteList("blockcolors");
		toblockid = cfg.getInt("toblockid");
		fromblockid = cfg.getInt("fromblockid");
		startradius = cfg.getInt("startradius");
		maxstartheight = cfg.getInt("maxstartheight");
		minstartheight = cfg.getInt("minstartheight");
		maxdistancenormal = cfg.getInt("maxdistancenormal");
		maxdistancey1 = cfg.getInt("maxdistancey1");
		removedistance = cfg.getInt("removedistance");
		chanceyup = cfg.getInt("chanceyup");
		mindistancenormal = cfg.getInt("mindistancenormal");
		mindistancey1 = cfg.getInt("mindistancey1");
		startblockid = cfg.getInt("startblockid");
		startblock1downid = cfg.getInt("startblock1downid");
		fallsound = cfg.getString("fallsound");
		spawnsound = cfg.getString("spawnsound");
		countdown = cfg.getInt("countdown");
		maxheight = cfg.getInt("maxheight");
		elapsedtimecolor = cfg.getString("elapsedtimecolor").replace("&", "§");
		lefttimecolor = cfg.getString("lefttimecolor").replace("&", "§");
		usespawn = cfg.getBoolean("usespawn");
		xpbarcount = cfg.getBoolean("xpbarcount");
		colormenu = cfg.getBoolean("colormenu");
		colormenuslot = cfg.getInt("colormenuslot");
		colormenuname = cfg.getString("colormenuname").replace("&", "§");
		colorpicksound = cfg.getString("colorpicksound");
		world = cfg.getString("world");
		
	}
	
	public static void saveDefaultSounds(FileConfiguration cfg, File file) {
		
		try {
			try {
				if(!cfg.contains("spawnsound")) {
					if(getVersion() > 7) {
						loadSpawnsound(file);
					}
					cfg.addDefault("spawnsound", defaultSpawnSound());
				}
				if(!cfg.contains("fallsound")) {
					if(getVersion() > 7) {
						loadFallsound(file);
					}
					cfg.addDefault("fallsound", defaultFallSound());
				}
				if(!cfg.contains("colorpicksound")) {
					if(getVersion() > 7) {
						loadColorpicksound(file);
					}
					cfg.addDefault("colorpicksound", defaultColorpickSound());
				}
			} catch(IndexOutOfBoundsException e) {
				Bukkit.getLogger().log(Level.WARNING, "[AutoJumpAndRun] Please delete the config.yml and reload the server!");
			}
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.WARNING, "[AutoJumpAndRun] Default config sounds couldn't be loaded! Please try again!");
			e.printStackTrace();
		}
		
	}
	
	private static void loadSpawnsound(File file) throws IOException {
		List<String> lines = FileUtils.readLines(file);
		lines.set(64, "spawnsound: " + defaultSpawnSound());
		FileUtils.writeLines(file, lines);
	}
	
	private static void loadFallsound(File file) throws IOException {
		List<String> lines = FileUtils.readLines(file);
		lines.set(67, "fallsound: " + defaultFallSound());
		FileUtils.writeLines(file, lines);
	}
	
	private static void loadColorpicksound(File file) throws IOException {
		List<String> lines = FileUtils.readLines(file);
		lines.set(101, "colorpicksound: " + defaultColorpickSound());
		FileUtils.writeLines(file, lines);
	}
	
	private static String defaultSpawnSound() {
		if(isUsingOldSounds()) return "CHICKEN_EGG_POP";
		return "ENTITY_CHICKEN_EGG";
	}
	
	private static String defaultFallSound() {
		if(isUsingOldSounds()) return "ANVIL_LAND";
		return "BLOCK_ANVIL_LAND";
	}
	
	private static String defaultColorpickSound() {
		if(isUsingOldSounds()) return "LEVEL_UP";
		return "ENTITY_PLAYER_LEVELUP";
	}
	
	private static int getVersion() {
		return Integer.parseInt(Bukkit.getBukkitVersion().substring(2, 4).replace(".", "").replace("-", ""));
	}
	
	private static boolean isUsingOldSounds() {		
		return getVersion() <= 8;		
	}
	
}
