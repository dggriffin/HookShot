package me.salamijack.HookShot;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;

public class HookShotEntityListener extends EntityListener{
	
	private final HookShot plugin;
	
	public HookShotEntityListener(final HookShot plugin)
	{
		this.plugin = plugin;
	}
	
	
	//Checks to make sure arrow is not currently flying
	
	 public void onProjectileHit( ProjectileHitEvent event)
	    {
	    	if( event.getEntity() instanceof Arrow)
	    	{
	    		Arrow a = (Arrow) event.getEntity();
	    		if(a.getShooter() instanceof Player)
	    		{
	    			
	    			Player player = (Player) a.getShooter();
	    			
	    			for(int x = 0; x < HookShotPlayerListener.climbingPlayers.size(); x++)
	        		{
	            		if (player.getName().equals(HookShotPlayerListener.climbingPlayers.get(x).getPlayer().getName()))
	        			{
	            			//player.sendMessage(ChatColor.RED + "Your hook hit!");
	            			//climbingPlayers.get(x).getBlock()
	            			
	            			
	            			
	            			playerProfile me = HookShotPlayerListener.climbingPlayers.get(x);
	            			me.setArrowHit(true);
	    			
	        			}
	        		}	
	    		}
	    	}
	    }
	    
	
	
	public void onEntityDamage(EntityDamageEvent event)
	{
		if(event instanceof EntityDamageByEntityEvent)
		{
			EntityDamageByEntityEvent eventb = (EntityDamageByEntityEvent)event;
			if(eventb.getDamager() instanceof Arrow && (((Player) ((Arrow) eventb.getDamager()).getShooter()).getItemInHand()).getTypeId() == 262)
			{
				event.setDamage(0);
				event.setCancelled(true);
				
		
			}
		}
		
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			ItemStack is = player.getItemInHand();
			if(is.getTypeId() == plugin.pullItem)
			{
				if( event.getCause() == DamageCause.FALL && event.getDamage() <= 1)
				{
					event.setCancelled(true);
					
				}
				if( event.getCause() == DamageCause.SUFFOCATION && event.getDamage() <= 1)
				{
					event.setCancelled(true);
					
				}
				
				if( event.getCause() == DamageCause.PROJECTILE)
					event.setCancelled(true);
			
			}
		}
	
		
		
		
		
		
	}
	
	

}
