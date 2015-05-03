package net.thelightmc.commands.sub;

import net.thelightmc.commands.SkyCommand;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.core.statistics.Stats;
import net.thelightmc.manager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdScore extends SkyCommand {
    @Override
    protected void execute(CommandSender sender, String[] args) {
        GamePlayer gamePlayer = PlayerManager.getPlayer(sender);
        sender.sendMessage(ChatColor.DARK_AQUA + "Score: " + gamePlayer.getStat(Stats.Score));
        sender.sendMessage(ChatColor.DARK_AQUA + "Games: " + gamePlayer.getStat(Stats.Matches));
        sender.sendMessage(ChatColor.DARK_AQUA + "Wins: "+ gamePlayer.getStat(Stats.Wins));
        sender.sendMessage(ChatColor.DARK_AQUA + "Kills: "+ gamePlayer.getStat(Stats.Kills));
        sender.sendMessage(ChatColor.DARK_AQUA + "Deaths: "+ gamePlayer.getStat(Stats.Deaths));
    }

    @Override
    protected String getCommand() {
        return "score";
    }

    @Override
    protected String getPermission() {
        return "score";
    }

    @Override
    protected boolean allowConsoleSender() {
        return false;
        //Will support console soon
    }
}
