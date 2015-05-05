package net.thelightmc.commands.sub;

import net.thelightmc.commands.SkyCommand;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.manager.GameManager;
import net.thelightmc.manager.PlayerManager;
import org.bukkit.command.CommandSender;

public class CmdJoin extends SkyCommand {
    public CmdJoin() {
        this.description = "Joins the next available match.";
        this.usage = "/sw join";
    }
    @Override
    protected void execute(CommandSender sender, String[] args) {
        GamePlayer player = PlayerManager.getPlayer(sender);
        GameManager.getInstance().getAvailable().addPlayer(player);
    }

    @Override
    protected String getCommand() {
        return "join";
    }

    @Override
    protected String getPermission() {
        return "join";
    }

    @Override
    protected boolean allowConsoleSender() {
        return false;
    }
}
