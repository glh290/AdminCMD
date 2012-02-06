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

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class SpecialPermParent extends PermParent {

	/**
	 * @param perm
	 */
	SpecialPermParent() {
		super(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see be.Balor.Manager.Permissions.PermParent#registerPermission()
	 */
	@Override
	void registerPermission() {
		for (PermChild child : children)
			child.registerPermission();
	}

}
