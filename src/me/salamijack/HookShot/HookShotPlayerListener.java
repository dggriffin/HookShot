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
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.getspout.spoutapi.sound.Music;
import org.getspout.spoutapi.sound.SoundEffect;
import org.getspout.spoutapi.SpoutManager;



public class HookShotPlayerListener extends PlayerListener
{
	
	//public Arrow zipline = null;
	public boolean isHooking = false;
    private final HookShot plugin;
    public Server server = null;
    public static LinkedList<playerProfile> climbingPlayers = new LinkedList<playerProfile>();
    
 
    
   
    
    
    public HookShotPlayerListener(HookShot instance) 
    {
        plugin = instance;
   
        
    }
    
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	
    }
    
    public void onPlayerQuit(PlayerQuitEvent event)
    {
    	
    }
   
    public void onPlayerInteract(PlayerInteractEvent event)
    {
    
    	Player player = event.getPlayer();
    	Boolean found = false;
    	Action action = event.getAction();
    	Block target = event.getClickedBlock();
    	playerProfile me = null;
    	World current = player.getWorld();
    	ItemStack is = player.getItemInHand();
    	Location loc = player.getLocation();
    	Location locheck = player.getLocation();
    
    	
  if(HookShotPermissions.getInstance().use(player) || HookShotPermissions.getInstance().permissionsEnabled == false || player.hasPermission("hookshot.use"))
    
    //if( player.hasPermission("hookshot.use"))
    	
    {
	  
	 locheck.setY(locheck.getY()-1);
	 Block flycheck = locheck.getBlock();
    
	//if((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && flycheck.getType() != Material.AIR)
    	
    if((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK))
    {
    	
    
    //FIRING THE ARROW, AND CHECKING TO SEE IF THE PLAYER IS CLIMBING
    	
    	if(is.getTypeId() == plugin.hookItem)	
    	{
    		for(int x = 0; x < climbingPlayers.size(); x++)
    		{
        		if (player.getName().equals(climbingPlayers.get(x).getPlayer().getName()))
    			{
        			//player.sendMessage(ChatColor.RED + "This player is already climbing..");
        			//climbingPlayers.get(x).getBlock()
        			
        			found = true;
        			
        			me = climbingPlayers.get(x);
                
                }
        		
    		}
    		
    		if(found == false)
    		{
    			 me = new playerProfile(player.getLocation(), player);
    			
            	climbingPlayers.add(me);
            	
    		}
        	
    		player.sendMessage(ChatColor.GREEN + "Hook Fired!");
    		
    		if(player.getItemInHand().getAmount() > 1)
    		{
    			player.getItemInHand().setAmount(player.getItemInHand().getAmount() -1);
    		}
    		else if (player.getItemInHand().getAmount() == 1)
    		{
    			player.setItemInHand(null);
    		}
    		
    //ARROW FIRED, SAVES DATA TO PLAYERPROFILE
    		
    		Arrow zipline = player.shootArrow();
    		me.setArrow(zipline);
    		loc = player.getLocation();
    		me.setInitial(loc);
    	}
    	
    	
    //PULLING THE PLAYER TOWARDS THE LOCATION, INITIATING MOVEMENT
    	
    	
    	if(is.getTypeId() == plugin.pullItem)
    	{
    		
    		
    		for(int x = 0; x < climbingPlayers.size(); x++)
    		{
        		if (player.getName().equals(climbingPlayers.get(x).getPlayer().getName()))
    			{
        			
        			me = climbingPlayers.get(x);
        			
    			}
        		
    		}
    		
    		
    		if(me.hasBlock())
    		{
    			me.getBlock().setType(Material.AIR);
    		}
    		
    		
    		//HAS THE ARROW STOPPED MOVING?
    		
    		if(me.arrowHit == true)
    		{
    			zipIn(player, loc, me.getArrow());
    		}
    		
    		else{
    			player.sendMessage(ChatColor.RED + "You pull before the hook makes contact and it breaks! ");
    			
    		}
            
    	
    		me.setArrow(null);
        			
    	}
        		
    }
   }
    	
}
  

    // METHOD THAT CONTROLS THE PLAYERS ACTUAL MOVEMENT/TELEPORTING
    
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
        	
        	
    		if(ploc.getX() + 25 < aloc.getX() || ploc.getY() + 25 < aloc.getY() || ploc.getZ() + 25 < aloc.getZ() || ploc.getY() - 25 > aloc.getY() || ploc.getX() - 25 > aloc.getX() || ploc.getZ() - 25 > aloc.getZ())
    		{
    			player.sendMessage(ChatColor.RED + "The Hook is too far away! Shoot again!");
    		
    		}
    		else
    		{
    			
    			for(int x = 0; x < climbingPlayers.size(); x++)
    			{
    				if (player.getName().equals(climbingPlayers.get(x).getPlayer().getName()))
    				{
    					//player.sendMessage(ChatColor.RED + "This method works, (the one that checks the linkedlist for reoccuring players.");
    					
    					current = climbingPlayers.get(x);
    					initial = climbingPlayers.get(x).getInitial();
    					//initial = ploc;
    					
    					climbingPlayers.get(x).setHit(aloc);
    					hit = climbingPlayers.get(x).getHit();
    				
    				}
    			}
    		
    			
    			player.teleport(initial);
    			Snowball ride = player.throwSnowball();
    		
    			ride.setPassenger(player);
    		
    			
    			
    			//STANDARD DISTANCE FORMULA TO FIND OUT HOW FAR THE HOOK LOCATION IS FROM THE INITIAL FIRING POINT
    			double distance = Math.sqrt(Math.pow(hit.getX() - initial.getX(),2) + Math.pow(hit.getY() - initial.getY(),2) + Math.pow(hit.getZ() - initial.getZ(),2));
    		
    			
    			//The explanation behind the waiting method is simple. If the listener is set to wait for a given time, 
    			//it simulates the pulling effect without factoring in the odd physics projectiles have when one sets an entity
    			//to ride on it. Also, the server seems to have less "moved wrongly" issues if the user has pulled themselves
    			//almost precisely to the point where they are teleporting before the wait is over.
    			
    			if(distance > 18)
    			{
    				waiting(1.2);
    				//player.sendMessage("Extra Long");
    						
    			}
    			else if(distance > 12)
    			{
    				waiting(1);
    				//player.sendMessage("Long");
    			}
    			else if(distance > 6)
    			{
    				waiting(.8);
    				//player.sendMessage("Medium");
    			}
    			
    			else if (distance < 6)
    			{
    				waiting(.6);
    				//player.sendMessage("Short");
    			}
    			//waiting(1);
    	
    		
    		
    			Block block = aloc.getBlock();
    			int idCheck = block.getTypeId();
    			
    			
    			Location aloc2 = aloc;
    			aloc2.setY(aloc.getY()-.5);
    			Block block2 = aloc2.getBlock();
    			int idCheck2 = block2.getTypeId();
    			
    			if((idCheck != 324 && idCheck != 330 && idCheck != 44) && (idCheck2 != 324 && idCheck2 != 330 && idCheck2 != 44))
    			{
    				//player.sendMessage("Herp Derp");
    				block.setType(Material.GLASS);

        			current.setBlock(block);
        			current.setHasBlock(true);
    			}
    			
    			
    			
    			//Location pl = hit;
    			Location pl = block.getLocation();
    			//pl.setYaw(pl.getYaw()*-1);
    			pl.setYaw(hit.getYaw()*-1);
    			pl.setPitch(player.getLocation().getPitch());
    			pl.setY(pl.getY() + 2);
    			
    			if(current.getInitial().getX() > current.getHit().getX())
    			{
    				pl.setX(pl.getX() + .4);
    			}
    			
    			if(current.getInitial().getZ() > current.getHit().getZ())
    			{
    				pl.setZ(pl.getZ() + .4);
    			}
    			//pl.setZ(pl.getZ() + .3);
    			//pl.setX(pl.getX() + .3);
    			
    			player.teleport(pl);
    			player.teleport(pl);
    			player.teleport(pl);
    			player.teleport(pl);
    			
    			
    			player.setFallDistance(0);
    			
    			
    			//current.setBlock(block);
    			//current.setHasBlock(true);
    			
    			
    			
    			
    			
    			//FOR POTENTIAL SPOUT USE IN THE FUTURE
    			
    			
    			/*if(player instanceof SpoutPlayer)
    			{
    				SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
    				SpoutManager.getSoundManager().playSoundEffect(sPlayer, SoundEffect.GHAST_SCREAM);
    			}
    			 */	
    		
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
    

    
//EVENT THAT SPURS DIRT BLOCK DELETION  
    
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
					
					for( int y = 0; y < current.playerBlocks.size(); y++)
					{
						current.playerBlocks.get(y).setType(Material.AIR);
						
					}
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
 			
    			

	
//PLAYER PROFILE CLASS

class playerProfile
{
	
	public Location hookShot;
	public Location firstHit;
	public Player player;
	public boolean hasblock;
	public Block stand;
	public Arrow myArrow;
	public LinkedList<Block> playerBlocks = new LinkedList<Block>();
	public boolean arrowHit;
	
	public playerProfile(Location hook,Player name)
	{
		hookShot = hook;
		
		player = name;
	}
	
	public void setArrowHit(boolean b) {

		arrowHit = b;
		
	}

	public Arrow getArrow()
	{
		return myArrow;
	}
	
	public void setArrow(Arrow my)
	{
		myArrow = my;
	}
	
	public Location getInitial()
	{
		return hookShot;
	}
	
	public  void setInitial(Location loc)
	{
		hookShot = loc;
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
		playerBlocks.add(block);
	}

	
	public Block getBlock()
	{
		return stand;
	}
	
	public void removeFirst()
	{
		playerBlocks.get(0).setType(Material.AIR);
		playerBlocks.removeFirst();
	}
}
    		
    		
    	
			
	
