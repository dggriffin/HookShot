package me.salamijack.HookShot;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

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
	}

}
