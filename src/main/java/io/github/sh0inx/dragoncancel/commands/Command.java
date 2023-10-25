package io.github.sh0inx.dragoncancel.commands;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import io.github.sh0inx.dragoncancel.DragonCancel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class Command {

    public final List<String> aliases;
    public final String description;
    public final String syntax;
    public final String permission;

    public Command() {
        this.aliases = Collections.emptyList();
        this.description = "";
        this.syntax = "";
        this.permission = "";
    }

    public Command(List<String> aliases, String description, String syntax, String permission) {
        this.aliases = aliases;
        this.description = description;
        this.syntax = syntax;
        this.permission = permission;
    }

    public boolean execute(CommandSender sender, String[] arguments, DragonCancel dragonCancelInstance) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(IridiumColorAPI.process(
                    "hey, you gotta be a player to do that"
            ));
            return false;
        }
        return execute(sender, arguments, dragonCancelInstance);
    }

    public boolean hasPermission(CommandSender commandSender, DragonCancel dragonCancelInstance) {
        return commandSender.hasPermission(permission) || permission.equalsIgnoreCase("");
    }

    public List<String> onTabComplete(CommandSender commandSender, String[] args, DragonCancel dragonCancelInstance) {
        if (commandSender instanceof Player) {
            return onTabComplete(commandSender, args, dragonCancelInstance);
        }
        return Collections.emptyList();
    }

    public List<String> onTabComplete(String[] args, DragonCancel dragonCancelInstance) {
        return Collections.emptyList();
    }

}
