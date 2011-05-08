package me.salamijack.HookShot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class HookShot extends JavaPlugin{

	private static final Logger log = Logger.getLogger("Minecraft");
	 private final HookShotPlayerListener playerListener = new HookShotPlayerListener(this);
	    private final HookShotBlockListener blockListener = new HookShotBlockListener(this);
	    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	    public final HashMap<Player, ArrayList<Block>> HookShotUsers = new HashMap<Player, ArrayList<Block>>();
	    
	    
	    // NOTE: There should be no need to define a constructor any more for more info on moving from
	    // the old constructor see:
	    // http://forums.bukkit.org/threads/too-long-constructor.5032/

	    public void onDisable() {
	        // TODO: Place any custom disable code here

	        // NOTE: All registered events are automatically unregistered when a plugin is disabled

	        // EXAMPLE: Custom code, here we just output some info so we can check all is well
	        System.out.println("Goodbye world!");
	    }

	    public void onEnable() {
	        // TODO: Place any custom enable code here including the registration of any events

	        // Register our events
	        PluginManager pm = getServer().getPluginManager();
	        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.BLOCK_PHYSICS, blockListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.BLOCK_CANBUILD, blockListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_TOGGLE_SNEAK, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_ITEM_HELD, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
	        
	        //log.info("SALAMITEST STARTED");

	        // Register our commands
	    //    getCommand("pos").setExecutor(new SalamiTestPosCommand(this));
	      //("debug").setExecutor(new SalamiTEstDebugCommand(this));

	        // EXAMPLE: Custom code, here we just output some info so we can check all is well
	        PluginDescriptionFile pdfFile = this.getDescription();
	        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	    }

	    public boolean isDebugging(final Player player) {
	        if (debugees.containsKey(player)) {
	            return debugees.get(player);
	        } else {
	            return false;
	        }
	    }

	    public void setDebugging(final Player player, final boolean value) {
	        debugees.put(player, value);
	    }
	    
	    
	    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	    {
	    	boolean result = false;
	    	if(commandLabel.equalsIgnoreCase("SalamiTest"))
	    	{
	    		toggleSalamiTest((Player) sender);
	    		result = true;
	    	}
	    	return result;
	    	
	    }
	    
	    public void toggleSalamiTest(Player player)
	    {
	    	if(enabled(player))
	    	{
	    		this.HookShotUsers.remove(player);
	    		player.sendMessage("Salami Test Disabled");
	    		
	    	}
	    	else
	    	{
	    		this.HookShotUsers.put(player, null);
	    		player.sendMessage("Salami Test Enabled");
	    	}
	    }
	    
	    public boolean enabled(Player player)
	    {
	    	return this.HookShotUsers.containsKey(player);
	    }
	    
	}

	
