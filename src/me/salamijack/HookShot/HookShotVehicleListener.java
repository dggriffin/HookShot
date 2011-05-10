package me.salamijack.HookShot;

import java.util.logging.Logger;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleListener;

public class HookShotVehicleListener extends VehicleListener{
	
	
	private final HookShot plugin;
	private static final Logger log = Logger.getLogger("Minecraft");
    public HookShotVehicleListener(HookShot instance) 
    {
        plugin = instance;

        
        
        
    }
    
    
   public void onVehicleExit (VehicleExitEvent event)
   {
	  /* log.info("Player left vehicle");
	   LivingEntity p =  event.getExited();
	   if(HookShotPlayerListener.zipline != null)
	   {
		   p.teleport(HookShotPlayerListener.zipline.getLocation());
		   if(p instanceof Player)
		   {
			   Player player = (Player) p;
			   player.sendMessage("Test");
			   player.teleport(HookShotPlayerListener.zipline.getLocation());
			   
			   
		   }
	   }
	  
	   
   */}

}
