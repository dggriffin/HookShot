package me.salamijack.HookShot;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijikokun.bukkit.Permissions.Permissions;




//Thank you mcMMO sourcecode for helping me out!!

public class HookShotPermissions {
	
	private static Permissions permissionsPlugin;
	public static boolean permissionsEnabled = false;
	private static volatile HookShotPermissions instance;
	
	public static void initialize (Server server)
	{
		Plugin test = server.getPluginManager().getPlugin("Permissions");
        if (test != null) {
            Logger log = Logger.getLogger("Minecraft");
            permissionsPlugin = ((Permissions) test);
            permissionsEnabled = true;
            log.log(Level.INFO, "[HookShot] Permissions enabled.");
        } else {
            Logger log = Logger.getLogger("Minecraft");
            //log.log(Level.SEVERE, "[HookShot] Permissions isn't loaded, there are no restrictions.");
        }
	}
	

	
	private static boolean permission(Player player, String string) {
        return permissionsPlugin.Security.permission(player, string);  
    }
	
	public boolean use(Player player)
	{
		boolean result = false;
		
		if (permissionsEnabled)
		{
			result = permission(player, "hookshot.use");
		}
		
		return result;
	}
	
	  public static HookShotPermissions getInstance() {
	    	if (instance == null) {
	    	instance = new HookShotPermissions();
	    	}
	    	return instance;
	    	}
}
