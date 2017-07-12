package me.Tailo.AutoJumpAndRun.Utils;

import java.util.HashMap;

import me.Tailo.AutoJumpAndRun.System.JnR;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorMenuManager {

	private static HashMap<String, ItemStack> preitem = new HashMap<>();
	
	public static void giveColorMenu(Player p) {
		
		preitem.put(p.getName(), p.getInventory().getItem(ConfigManager.colormenuslot - 1));
		
		@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(ConfigManager.fromblockid, 1, JnR.getJnR(p).color);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(ConfigManager.colormenuname);
		item.setItemMeta(itemmeta);
		
		p.getInventory().setItem(ConfigManager.colormenuslot - 1, item);
		
	}
	
	public static void openColorMenu(Player p) {
		
		int lines = 0;
		while(lines * 9 < ConfigManager.colors.size() - 1) {
			lines++;
		}
		
		Inventory inv = Bukkit.createInventory(null, lines * 9, ConfigManager.colormenuname);
		
		int i = 0;
		
		for(byte color : ConfigManager.colors) {
			
			@SuppressWarnings("deprecation")
			ItemStack item = new ItemStack(ConfigManager.fromblockid, 1, color);
			inv.setItem(i, item);
			
			i++;
			
		}
		
		p.openInventory(inv);
		
	}
	
	public static void removeColorMenu(Player p) {
		
		p.getInventory().setItem(ConfigManager.colormenuslot - 1, preitem.get(p.getName()));
		preitem.remove(p.getName());
		
		p.closeInventory();
		
	}
	
}
