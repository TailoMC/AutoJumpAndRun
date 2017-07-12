package me.Tailo.AutoJumpAndRun.Utils;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NMSUtils {

	public static String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", " ").split(" ")[3];
	static boolean newchat;
	
	public NMSUtils() {		
		newchat = Integer.parseInt(version.split("_")[1]) > 11;		
	}
	
	public static Class<?> getNMSClass(String name) throws Exception {
		
		return Class.forName("net.minecraft.server." + version + "." + name);
		
	}
	
	public static void sendPacket(Player p, Object obj) throws Exception {
		
		Class<?> packet = getNMSClass("Packet");
		Class<?> craftplayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
		
		Object cp = craftplayer.cast(p);
		Object handle = craftplayer.getMethod("getHandle").invoke(cp);
		Object con = handle.getClass().getField("playerConnection").get(handle);
		
		con.getClass().getMethod("sendPacket", packet).invoke(con, obj);
		
	}
	
	public static boolean isNewChat() {		
		return newchat;		
	}
	
	public static void setValue(Object clazz, String field, Object value) throws Exception {
		Field f = clazz.getClass().getDeclaredField(field);
		f.setAccessible(true);
		f.set(clazz, value);
	}
	
}
