package net.thelightmc.commands.sub;

import net.thelightmc.commands.SkyCommand;
import org.bukkit.command.CommandSender;

public class CmdHelp extends SkyCommand {

    public CmdHelp() {
        this.description = "Main help command with the tutorial";
        this.usage = "/sw help";
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        sender.sendMessage("Hi");
    }

    @Override
    protected String getCommand() {
        return "help";
    }

    @Override
    protected String getPermission() {
        return "Help";
    }

    @Override
    protected boolean allowConsoleSender() {
        return true;
    }

    @Override
    protected SkyCommand.Permission defaultPermission() {
        return Permission.Default;
    }
}