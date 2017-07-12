package me.Tailo.AutoJumpAndRun.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;

import me.Tailo.AutoJumpAndRun.Utils.ColorMenuManager;
import me.Tailo.AutoJumpAndRun.Utils.ConfigManager;
import me.Tailo.AutoJumpAndRun.Utils.LocationManager;
import me.Tailo.AutoJumpAndRun.Utils.NMSUtils;
import me.Tailo.AutoJumpAndRun.Utils.SoundManager;
import me.Tailo.AutoJumpAndRun.Utils.SoundManager.Sound;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class JnR {

	static boolean error;
	
	Player p;
	public Block to;
	ArrayList<Block> from = new ArrayList<>();
	public byte color;
	boolean first;
	BukkitRunnable run;
	int count;
	int prelevel;
	float preexp;
	
	private static HashMap<Player, JnR> getjnr = new HashMap<>();
	
	public JnR(Player p) {
		
		if(getjnr.containsKey(p)) {
			getjnr.get(p).remove();
		}
		
		this.p = p;
		this.prelevel = p.getLevel();
		this.preexp = p.getExp();
		
		color = ConfigManager.colors.get(new Random().nextInt(ConfigManager.colors.size()));
				
		startNew();
		getjnr.put(p, this);
		
		ColorMenuManager.giveColorMenu(p);
		
	}
	
	public static JnR getJnR(Player p) {
		
		return getjnr.get(p);
		
	}
	
	@SuppressWarnings("deprecation")
	private void startNew() {
		
		Block b;
		
		int i = 0;
		
		do {
			
			int y = random(ConfigManager.minstartheight, ConfigManager.maxstartheight);
			int x = random(-ConfigManager.startradius, ConfigManager.startradius);
			int z = random(-ConfigManager.startradius, ConfigManager.startradius);
			Location bloc = p.getLocation().clone().add(x, y, z);
			
			b = bloc.getBlock();
			
			i++;
			
			if(i == 100) {
				remove();
				return;
			}
			
		} while (b.getType() != Material.AIR || b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.AIR || b.getLocation().clone().add(0, 2, 0).getBlock().getType() != Material.AIR);
		
		b.setTypeId(ConfigManager.fromblockid);
		b.setData(color);
		p.teleport(b.getLocation().clone().add(0.5, 1, 0.5));
		
		to = b;
		from.add(p.getLocation().clone().add(0, 1000, 0).getBlock());
		from.add(b);
		
		first = true;
		
		placeNewBlock();
		
		if(ConfigManager.xpbarcount) {
			
			count = 0;
			
			p.setExp(0);
			p.setLevel(count);
			
		}

		
	}
	
	public boolean isNewBlock() {
		
		return p.getLocation().subtract(0, 1, 0).getBlock().equals(to);
		
	}
	
	public boolean isUnderBlock() {
		
		if(to != null) {
			return to.getY() - p.getLocation().getY() > ConfigManager.removedistance;
		}
		
		return false;
		
	}
	
	public void remove() {
		
		if(to != null) to.setType(Material.AIR);
		for(Block b : from) {
			b.setType(Material.AIR);
		}
		
		if(run != null) run.cancel();
		
		sendActionBar(p, "");
		
		if(ConfigManager.usespawn && LocationManager.getSpawn() != null) p.teleport(LocationManager.getSpawn());
		
		SoundManager.playSound(p, Sound.FALL);
		
		if(getjnr.containsKey(p)) getjnr.remove(p);
		
		if(ConfigManager.xpbarcount) {
			
			p.setLevel(prelevel);
			p.setExp(preexp);
			
		}
		
		if(ConfigManager.colormenu) {
			
			ColorMenuManager.removeColorMenu(p);
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void placeNewBlock() {
		
		Block b;
		
		int i = 0;
		
		do {
			
			b = to.getLocation().clone().add(getRandomLoc()).getBlock();
			
			i++;
			
			if(i == 100) {
				remove();
				return;
			}
			
		} while (b.getType() != Material.AIR || b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.AIR || b.getLocation().clone().add(0, 2, 0).getBlock().getType() != Material.AIR);
		
		if(ConfigManager.countdown > 0) {
			
			if(run != null) run.cancel();
			
			run = new BukkitRunnable() {
				
				int i = 50;
				
				@Override
				public void run() {
					
					int elapsed = 50 - i;
					int left = i;
					
					String str = ConfigManager.elapsedtimecolor;
					
					while (elapsed > 0) {
						
						str = str + "|";
						
						elapsed --;
					}
					
					str = str + ConfigManager.lefttimecolor;
					
					while (left > 0) {
						
						str = str + "|";
						
						left --;
					}
					
					sendActionBar(p, str);
					
					if(i == 0) {
						
						cancel();
						remove();
						
					}
					
					i --;
					
				}
			};
			run.runTaskTimer(main.instance, 0, (long) (0.4 * ConfigManager.countdown));
			
		}
		
		from.add(to);
		
		SoundManager.playSound(p, Sound.SPAWN);
		
		to.setTypeId(ConfigManager.fromblockid);
		to.setData(color);
		
		to = b;
		
		to.setTypeId(ConfigManager.toblockid);
		to.setData(color);
		
		from.get(0).setType(Material.AIR);
		from.remove(0);
		if(first) {
			first = false;
			from.remove(0);
		}
		
		if(ConfigManager.xpbarcount) {
			
			count++;
			
			p.setExp(0);
			p.setLevel(count);
			
		}
		
	}
	
	private Location getRandomLoc() {
		
		int y = random(1, 100) > ConfigManager.chanceyup ? 0 : 1;
		int max = y == 1 ? ConfigManager.maxdistancey1 : ConfigManager.maxdistancenormal;
		int min = y == 1 ? ConfigManager.mindistancey1 : ConfigManager.mindistancenormal;
		
		Location loc;
		
		int i = 0;
		
		do {
			
			int x = random(-max, max);
			int z = random(-max, max);
			
			z = z == 0 && x == 0 ? random(0, 1) == 1 ? max : -max : z;
			
			loc = new Location(p.getWorld(), x, y, z);
			
			i++;
			
			if(i == 100) {
				return loc;
			}
			
		} while (new Location(p.getWorld(), 0, 0, 0).distance(loc) < min);
		
		return loc;
		
	}
	
	private int random(int min, int max) {
		
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
		
	}
	
	private static void sendActionBar(Player p, String message) {
		
		try {
			
			Object chat = NMSUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + message + "\"}" });
			
			Object packet = NMSUtils.getNMSClass("PacketPlayOutChat").newInstance();
			NMSUtils.setValue(packet, "a", chat);
			
			if(NMSUtils.isNewChat()) {				
				Object chatmessagetype = NMSUtils.getNMSClass("ChatMessageType").getEnumConstants()[2];				
				NMSUtils.setValue(packet, "b", chatmessagetype);
			} else {
				NMSUtils.setValue(packet, "b", (byte) 2);
			}
			
			NMSUtils.sendPacket(p, packet);
			
		} catch (Exception e) {			
			if(!error) {
				Bukkit.getLogger().log(Level.WARNING, "[AutoJumpAndRun] The version " + NMSUtils.version + " is currently not working for showing the countdown so the countdown won't be shown.");				
				error = true;				
			}			
		}
		
	}
	
}
