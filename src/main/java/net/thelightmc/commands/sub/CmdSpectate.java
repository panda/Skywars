package net.thelightmc.commands.sub;

import net.thelightmc.Language;
import net.thelightmc.commands.SkyCommand;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSpectate extends SkyCommand {

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Language.PlayerNotFound.getMsg());
                return;
            }
            GamePlayer gamePlayer = PlayerManager.getPlayer(target);
            if (gamePlayer.getGame() == null) {
                sender.sendMessage(Language.PlayerNoGame.getMsg());
                return;
            }
            target.sendMessage(Language.PlayerNowSpectating.getMsg().replace("{Player}", sender.getName()));
            sender.sendMessage(Language.SpectateModeEnabled.getMsg());
            Player p = (Player) sender;
            p.setGameMode(GameMode.SPECTATOR);
            p.teleport(target.getLocation());
        } else {
            Player player = (Player) sender;
            /*
            if (player.getGameMode() == GameMode.SPECTATOR) {
                player.teleport(new Location(null,10,10,10)) // Won't work yet Need to get a way to get locs from file
            }
            */
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    @Override
    protected String getCommand() {
        return "spectate";
    }

    @Override
    protected String getPermission() {
        return "spectate";
    }

    @Override
    protected boolean allowConsoleSender() {
        return false;
    }
}
