package net.thelightmc.commands;

import net.thelightmc.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;

public class CommandHandler implements CommandExecutor {
    private static ArrayList<SkyCommand> commands = new ArrayList<>();
    public static void addCommand(SkyCommand sCommand) {
        commands.add(sCommand);
    }
    public static void addCommands(SkyCommand... sCommands) {
        for (SkyCommand sCommand : sCommands) {
            addCommand(sCommand);
        }
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 0) {
            for (SkyCommand sCommand : commands) {
                if (sCommand.getCommand().equalsIgnoreCase(strings[0]) || sCommand.getAliases().contains(strings[0].toLowerCase())) {
                    if (!sCommand.allowConsoleSender() && commandSender instanceof ConsoleCommandSender) {
                        return false;
                    }
                    if (!sCommand.defaultPermission().hasPermission(commandSender,sCommand.getPermission())) {
                        commandSender.sendMessage(Language.NoPermission.getMsg());
                        return false;
                    }
                    sCommand.execute(commandSender, strings);
                    return true;
                }
            }
        }
        return false;
    }
}
