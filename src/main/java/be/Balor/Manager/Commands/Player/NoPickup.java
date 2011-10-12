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
package be.Balor.Manager.Commands.Player;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import be.Balor.Manager.Commands.CommandArgs;
import be.Balor.Manager.Commands.CoreCommand;
import be.Balor.Player.ACPlayer;
import be.Balor.Tools.Type;
import be.Balor.Tools.Utils;

/**
 * @author Balor (aka Antoine Aflalo)
 *
 */
public class NoPickup extends CoreCommand {

	/**
	 *
	 */
	public NoPickup() {
		permNode = "admincmd.player.nopickup";
		cmdName = "bal_np";
		other = true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * be.Balor.Manager.ACCommands#execute(org.bukkit.command.CommandSender,
	 * java.lang.String[])
	 */
	@Override
	public void execute(CommandSender sender, CommandArgs args) {
		Player player = Utils.getUser(sender, args, permNode);
		if (player != null) {
			HashMap<String, String> replace = new HashMap<String, String>();
			replace.put("player", Utils.getPlayerName(player));
			ACPlayer acp = ACPlayer.getPlayer(player.getName());
			if (acp.hasPower(Type.NO_PICKUP)) {
				acp.removePower(Type.NO_PICKUP);
				Utils.sI18n(player, "npDisabled");
				if (!player.equals(sender))
					Utils.sI18n(sender, "npDisabledTarget", replace);
			} else {
				acp.setPower(Type.NO_PICKUP);
				Utils.sI18n(player, "npEnabled");
				if (!player.equals(sender))
					Utils.sI18n(sender, "npEnabledTarget", replace);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see be.Balor.Manager.ACCommands#argsCheck(java.lang.String[])
	 */
	@Override
	public boolean argsCheck(String... args) {
		return args != null;
	}

}
