package net.thelightmc.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SkyCommand {
    protected abstract void execute(CommandSender sender,String[] args);
    protected abstract String getCommand();
    protected abstract String getPermission();
    protected abstract boolean allowConsoleSender();
    public String description;
    public String usage;
    private List<String> aliases = new ArrayList<>();

    public List<String> getAliases() {
        return aliases;
    }

    public void addAlias(String alias) {
        this.aliases.add(alias.toLowerCase().trim());
    }

    public boolean hasPermission(CommandSender commandSender, String permission) {
        return commandSender.hasPermission(permission) || commandSender.isOp();
    }
}