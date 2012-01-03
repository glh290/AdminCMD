/************************************************************************
 * This file is part of AdminCmd.									
 *																		
 * AdminCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by	
 * the Free Software Foundation, either version 3 of the License, or		
 * (at your option) any later version.									
 *																		
 * AdminCmd is distributed in the hope that it will be useful,	
 * but WITHOUT ANY WARRANTY; without even the implied warranty of		
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the			
 * GNU General Public License for more details.							
 *																		
 * You should have received a copy of the GNU General Public License
 * along with AdminCmd.  If not, see <http://www.gnu.org/licenses/>.
 ************************************************************************/
package be.Balor.bukkit.AdminCmd;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.google.common.collect.MapMaker;

import be.Balor.Manager.CommandManager;
import be.Balor.Manager.Commands.CoreCommand;
import be.Balor.Tools.Debug.ACLogger;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class ACPluginManager {
	private final static ACPluginManager instance = new ACPluginManager();
	private ConcurrentMap<String, AbstractAdminCmdPlugin> pluginInstances = new MapMaker()
			.makeMap();
	private static Server server = null;

	private ACPluginManager() {
	}

	/**
	 * @return the instance
	 */
	protected static ACPluginManager getInstance() {
		return instance;
	}

	/**
	 * @return the server
	 */
	public static Server getServer() {
		return server;
	}

	/**
	 * @param server
	 *            the server to set
	 */
	public static void setServer(Server server) {
		ACPluginManager.server = server;
	}

	/**
	 * Get registered plugin
	 * 
	 * @param name
	 *            name of the addon
	 * @return the addon or null if not registered
	 */
	protected AbstractAdminCmdPlugin getPlugin(String name) {
		return pluginInstances.get(name);
	}

	public static AbstractAdminCmdPlugin getPluginInstance(String name) {
		return getInstance().getPlugin(name);
	}

	/**
	 * Register a AdminCmd addon
	 * 
	 * @param addon
	 */
	protected void registerPlugin(AbstractAdminCmdPlugin addon)
			throws IllegalArgumentException {
		if (!pluginInstances.containsKey(addon.getName()))
			pluginInstances.put(addon.getName(), addon);
		else
			throw new IllegalArgumentException("Plugin " + addon.getName()
					+ " Already registered.");
	}

	public static void registerACPlugin(AbstractAdminCmdPlugin addon)
			throws IllegalArgumentException {
		getInstance().registerPlugin(addon);
	}

	/**
	 * Unregister an AdminCmd addon
	 * 
	 * @param addon
	 */
	protected void unRegisterPlugin(AbstractAdminCmdPlugin addon) {
		pluginInstances.remove(addon.getName());
	}

	public static void unRegisterACPlugin(Plugin addon) {
		if (addon instanceof AbstractAdminCmdPlugin)
			getInstance().unRegisterPlugin((AbstractAdminCmdPlugin) addon);
	}

	/**
	 * Get Bukkit Scheduler
	 * 
	 * @return
	 */
	public static BukkitScheduler getScheduler() {
		return server.getScheduler();
	}

	/**
	 * Schedule a SyncTask
	 * 
	 * @param task
	 * @return
	 */
	public static int scheduleSyncTask(Runnable task) {
		return server.getScheduler().scheduleSyncDelayedTask(
				instance.getPlugin("Core"), task);
	}

	/**
	 * Register a Plugin Command
	 * 
	 * @param clazz
	 */
	public static void registerCommand(Class<? extends CoreCommand> clazz)
			throws IllegalArgumentException {
		CommandManager.getInstance().registerCommand(clazz);
	}

	void stopChildrenPlugins() {
		ACLogger.info("Disabling all AdminCmd's plugins");
		for (Entry<String, AbstractAdminCmdPlugin> plugin : pluginInstances
				.entrySet())
			if (plugin.getValue().isEnabled())
				server.getPluginManager().disablePlugin(plugin.getValue());
	}

}
