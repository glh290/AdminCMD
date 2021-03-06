/**
 * **********************************************************************
 * This file is part of AdminCmd.
 *
 * AdminCmd is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * AdminCmd is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * AdminCmd. If not, see <http://www.gnu.org/licenses/>.
 * **********************************************************************
 */
package be.Balor.Manager.Commands.Player;

import be.Balor.Manager.Commands.CommandArgs;
import be.Balor.Manager.Exceptions.ActionNotPermitedException;
import be.Balor.Manager.Exceptions.PlayerNotFound;
import be.Balor.Manager.LocaleManager;
import be.Balor.Player.BannedPlayer;
import be.Balor.Player.IBan;
import be.Balor.Tools.CommandUtils.Immunity;
import be.Balor.Tools.CommandUtils.Users;
import be.Balor.bukkit.AdminCmd.ACHelper;
import be.Balor.bukkit.AdminCmd.ACPluginManager;
import be.Balor.bukkit.AdminCmd.LocaleHelper;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

/**
 * @author Balor (aka Antoine Aflalo)
 *
 */
public class UnBan extends PlayerCommand {

        /**
         *
         */
        public UnBan() {
                permNode = "admincmd.player.ban";
                cmdName = "bal_unban";
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * be.Balor.Manager.ACCommands#execute(org.bukkit.command.CommandSender,
         * java.lang.String[])
         */
        @Override
        public void execute(final CommandSender sender, final CommandArgs args)
                throws ActionNotPermitedException, PlayerNotFound {
                final String unban = args.getString(0);

                final OfflinePlayer op = ACPluginManager.getServer().getOfflinePlayer(unban);

                final IBan ban = ACHelper.getInstance().getBan(op.getUniqueId().toString());
                if (ban != null) {
                        if (ban instanceof BannedPlayer
                                && !Immunity.checkImmunity(sender, args, 0)) {
                                LocaleManager.sI18n(sender, "insufficientLvl");
                                return;
                        }
                        ACHelper.getInstance().unBanPlayer(ban);
                        final String unbanMsg = LocaleManager.I18n("unban", "player", unban);
                        if (unbanMsg != null) {
                                Users.broadcastMessage(unbanMsg);
                        }
                } else {
                        final HashMap<String, String> replace = new HashMap<String, String>();
                        replace.put("ban", unban);
                        LocaleHelper.NO_BAN_FOUND.sendLocale(sender, replace);
                }

        }

        /*
         * (non-Javadoc)
         * 
         * @see be.Balor.Manager.ACCommands#argsCheck(java.lang.String[])
         */
        @Override
        public boolean argsCheck(final String... args) {
                return args != null && args.length >= 1;
        }

}
