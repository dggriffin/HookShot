package me.salamijack.HookShot;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
//import org.bukkit.Player;
import org.bukkit.block.Block;
import org.bukkit.block.NoteBlock;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;



public class HookShotPlayerListener extends PlayerListener
{
	
	public static Arrow zipline = null;

    private final HookShot plugin;

    public HookShotPlayerListener(HookShot instance) 
    {
        plugin = instance;

        
        
        
    }
   
    public void onPlayerInteract(PlayerInteractEvent event)
    {
    	
    	Player player = event.getPlayer();
    	Action action = event.getAction();
    	Block target = event.getClickedBlock();
    	//player.sendMessage(ChatColor.GREEN + "useHookShot enabled");
    	World current = player.getWorld();
    	ItemStack is = player.getItemInHand();
    	
    if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
    {
    	
    
    	if(is.getTypeId() == 262)
    	{
    		player.sendMessage(ChatColor.GREEN + "Hook Fired!");
    		player.getItemInHand().setAmount(player.getItemInHand().getAmount() -1);
    		//player.updateInventory();
    		zipline = player.shootArrow();
    		
    	}
    	
    	if(is.getTypeId() == 287)
    	{
    		zipIn(player);
    	}
    	
		
    	
    	
    }
}
    
    public void zipIn(Player player)
    {
    	
    	
    	if(player.getItemInHand().getTypeId() == 287 && zipline != null)
		{
    		Location ploc = player.getLocation();
        	Location aloc = zipline.getLocation();
        	
    		if(ploc.getX() + 20 < aloc.getX() || ploc.getY() + 20 < aloc.getY() || ploc.getZ() + 20 < aloc.getZ() || ploc.getY() - 20 > aloc.getY())
    		{
    			player.sendMessage(ChatColor.RED + "The Hook is too far away! Shoot again or move closer!");
    			//zipline = null;
    		}
    		else
    		{
    			player.teleport(zipline);
    			player.sendMessage(ChatColor.LIGHT_PURPLE + "You pull yourself to the hook!");
    			zipline = null;
    			
    			if(player.getItemInHand().getAmount() > 1)
        		{
        			player.getItemInHand().setAmount(player.getItemInHand().getAmount() -1);
        		}
        		else
        		{
        			player.setItemInHand(null);
        		}
    		}
		}
    	else if(zipline == null)
		{
			player.sendMessage(ChatColor.RED + "No hook to pull to!");
		}
	}
}
 			
    			
    			
    		
    		
    	
			
	
