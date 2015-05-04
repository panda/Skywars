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
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a&m--------------------&r&6 [Skywars] &a&m--------------------"));
        sender.sendMessage(ChatColor.GOLD + "Score: " + ChatColor.AQUA + gamePlayer.getStat(Stats.Score,args));
        sender.sendMessage(ChatColor.GOLD + "Games: " + ChatColor.AQUA + gamePlayer.getStat(Stats.Matches,args));
        sender.sendMessage(ChatColor.GOLD + "Wins: " + ChatColor.AQUA+ gamePlayer.getStat(Stats.Wins,args));
        sender.sendMessage(ChatColor.GOLD + "Kills: " + ChatColor.AQUA+ gamePlayer.getStat(Stats.Kills,args));
        sender.sendMessage(ChatColor.GOLD + "Deaths: " + ChatColor.AQUA+ gamePlayer.getStat(Stats.Deaths,args));
        sender.sendMessage(ChatColor.GOLD + "Last Played: " + ChatColor.AQUA + gamePlayer.getStat(Stats.LastGame,args));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a&m--------------------------------------------------"));
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
