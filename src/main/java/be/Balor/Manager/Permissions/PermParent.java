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
package be.Balor.Manager.Permissions;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import be.Balor.bukkit.AdminCmd.ACPluginManager;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class PermParent {
	protected Map<String, Boolean> children = new HashMap<String, Boolean>();
	protected String permName = "";
	protected String compareName = "";
	protected PermissionDefault def;

	public PermParent(String perm, String compare, PermissionDefault def) {
		this.permName = perm;
		this.compareName = compare;
		this.def = def;
	}

	public PermParent(String perm) {
		this(perm, perm.substring(0, perm.length() - 1), PermissionDefault.OP);
	}

	/**
	 * Add a Permission Child.
	 * 
	 * @param name
	 * @param bool
	 */
	public void addChild(String name, boolean bool) {
		children.put(name, bool);
	}

	public void addChild(String name) {
		addChild(name, true);
	}

	/**
	 * @return the compareName
	 */
	public String getCompareName() {
		return compareName;
	}

	/**
	 * @return the permName
	 */
	public String getPermName() {
		return permName;
	}

	public void registerBukkitPerm() {
		Permission perm = ACPluginManager.getServer().getPluginManager().getPermission(permName);
		if (perm == null)
			ACPluginManager.getServer().getPluginManager()
					.addPermission(new Permission(permName, def, children));
		else {
			perm.getChildren().putAll(children);
		}
	}

}
