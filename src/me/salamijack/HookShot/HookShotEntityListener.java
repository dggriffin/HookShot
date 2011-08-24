package me.salamijack.HookShot;

import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
	
	public void onEntityDamage(EntityDamageEvent event)
	{
		if(event instanceof EntityDamageByEntityEvent)
		{
			EntityDamageByEntityEvent eventb = (EntityDamageByEntityEvent)event;
			if(eventb.getDamager() instanceof Player && ((Player) eventb.getDamager()).getItemInHand().getTypeId() == 262)
			{
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
