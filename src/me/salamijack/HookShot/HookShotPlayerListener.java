package me.salamijack.HookShot;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
//import org.bukkit.Player;
import org.bukkit.block.Block;
import org.bukkit.block.NoteBlock;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;



public class HookShotPlayerListener extends PlayerListener
{
	
	public Arrow zipline = null;
	public boolean isHooking = false;
    private final HookShot plugin;
    public Server server = null;
    public LinkedList<playerProfile> climbingPlayers = new LinkedList<playerProfile>();
 
    
    public HookShotPlayerListener(HookShot instance) 
    {
        plugin = instance;
       /*
      server =  plugin.getServer();
      Player[] arr = server.getOnlinePlayers();
      for(int x = 0; x <arr.length; x++)
      {
    	  onlinePlayers.add(arr[x]);
      }

        
    */    
        
    }
   
    public void onPlayerInteract(PlayerInteractEvent event)
    {
    
    	Player player = event.getPlayer();
    	
    	Action action = event.getAction();
    	Block target = event.getClickedBlock();
    	//player.sendMessage(ChatColor.GREEN + "useHookShot enabled");
    	World current = player.getWorld();
    	ItemStack is = player.getItemInHand();
    	Location loc = player.getLocation();
    	
  if(HookShotPermissions.getInstance().use(player) || HookShotPermissions.getInstance().permissionsEnabled == false)
  {
	  
  
    if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
    {
    	
    
    	if(is.getTypeId() == plugin.hookItem)
    	{
    		playerProfile me = new playerProfile(player.getLocation(), player);
        	climbingPlayers.add(me);
        	
    		player.sendMessage(ChatColor.GREEN + "Hook Fired!");
    		
    		if(player.getItemInHand().getAmount() > 1)
    		{
    			player.getItemInHand().setAmount(player.getItemInHand().getAmount() -1);
    		}
    		else
    		{
    			player.setItemInHand(null);
    		}
    		
    		
    		//player.updateInventory();
    		zipline = player.shootArrow();
    		
    		
    	
    		loc = player.getLocation();
    	}
    	
    	if(is.getTypeId() == plugin.pullItem)
    	{
    		zipIn(player, loc, zipline);
    		zipline = null;
    	}
    	
		
    	
    	
    }
    
  }
}
    
    public void zipIn(Player player, Location loc, Arrow zip)
    {
    	
    	Location initial = null;
    	Location hit = null;
    	playerProfile current = null;
    	ItemStack ammo = null;
    	
    	if(player.getItemInHand().getTypeId() == plugin.pullItem && zip != null)
		{
    		Location ploc = player.getLocation();
        	Location aloc = zip.getLocation();
        	
        	
    		if(ploc.getX() + 15 < aloc.getX() || ploc.getY() + 15 < aloc.getY() || ploc.getZ() + 15 < aloc.getZ() || ploc.getY() - 15 > aloc.getY() || ploc.getX() - 15 > aloc.getX() || ploc.getZ() - 15 > aloc.getZ())
    		{
    			player.sendMessage(ChatColor.RED + "The Hook is too far away! Shoot again!");
    		
    		}
    		else
    		{
    			
    			for(int x = 0; x < climbingPlayers.size(); x++)
    			{
    				if (player.getName().equals(climbingPlayers.get(x).getPlayer().getName()))
    				{
    					current = climbingPlayers.get(x);
    					initial = climbingPlayers.get(x).getInitial();
    					
    					climbingPlayers.get(x).setHit(aloc);
    					hit = climbingPlayers.get(x).getHit();
    				}
    			}
    		
    			
    			player.teleport(initial);
    			Snowball ride = player.throwSnowball();
    		
    			ride.setPassenger(player);
    			waiting(.6);
    	
    		
    		
    			Block block = aloc.getBlock();
    			block.setType(Material.DIRT);
    			
    			
    			Location pl = hit;
    			pl.setYaw(pl.getYaw()*-1);
    			pl.setPitch(player.getLocation().getPitch());
    			pl.setY(pl.getY() + 2);
    			pl.setZ(pl.getZ() + .3);
    			pl.setX(pl.getX() + .3);
    			player.teleport(pl);
    			player.teleport(pl);
    			player.teleport(pl);
    			player.teleport(pl);
    			player.teleport(pl);
    			player.teleport(pl);
    			current.setBlock(block);
    			current.setHasBlock(true);
    	
    			player.sendMessage(ChatColor.LIGHT_PURPLE + "You pull yourself to the hook!");
    			zip= null;
    		
    			Inventory inven = player.getInventory();
    			ItemStack[] isarr = inven.getContents();
    			int index = inven.first(plugin.pullCost);
    			ammo = inven.getItem(index);
    			
    				
    					if(ammo.getAmount() > 1 && plugin.hookCost == true)
    	        		{
    	        			ammo.setAmount(ammo.getAmount()-1);
    	        			inven.setItem(index,ammo);
    	        			player.updateInventory();
    	        			
    	        		}
    	        		else if (plugin.hookCost == true)
    	        		{
    	        			ammo = null;
    	        			inven.setItem(index, ammo);
    	        			player.updateInventory();
    	        		}
    				
    			
    			
    			
    			
    			//climbingPlayers.remove(current);
    		}
		}
    	else if(zip == null)
		{
			player.sendMessage(ChatColor.RED + "No hook to pull to!");
		}
	}
    

    
    
    public void onPlayerMove (PlayerMoveEvent event)
    {
    	Player player = event.getPlayer();
    	playerProfile current;
    	
    	for(int x = 0; x < climbingPlayers.size(); x++)
		{
			if (player.getName().equals(climbingPlayers.get(x).getPlayer().getName()))
			{
				
				current = climbingPlayers.get(x);
				if(player.isSneaking() == false && current.hasBlock() == true)
				{
					current.getBlock().setType(Material.AIR);
					climbingPlayers.remove(current);
				}
			}
		}
    		
    	
    }
    

    public static void waiting (double n){
        
        long t0, t1;

        t0 =  System.currentTimeMillis();

        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (n * 1000));
    }
}
 			
    			

	


class playerProfile
{
	
	public Location hookShot;
	public Location firstHit;
	public Player player;
	public boolean hasblock;
	public Block stand;
	
	public playerProfile(Location hook,Player name)
	{
		hookShot = hook;
		
		player = name;
	}
	
	public Location getInitial()
	{
		return hookShot;
	}
	
	public Location getHit()
	{
		return firstHit;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	public void setHit(Location loc)
	{
		firstHit = loc;
	}
	
	public void setHasBlock(boolean bol)
	{
		hasblock = bol;
	}
	
	public boolean hasBlock()
	{
		return hasblock;
	}
	
	public void setBlock(Block block)
	{
		stand = block;
	}
	
	public Block getBlock()
	{
		return stand;
	}
}
    		
    		
    	
			
	
