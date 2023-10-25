package io.github.sh0inx.dragoncancel.managers;

import io.github.sh0inx.dragoncancel.DragonCancel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager extends io.github.sh0inx.heart.managers.CommandManager {
    public CommandManager(String command) {
        super(DragonCancel.getInstance(), "&9", command);
    }

    @Override
    public void registerCommands() {
        super.registerCommands();
        registerCommand(DragonCancel.getInstance().getCommands().aboutCommand);
    }

    @Override
    public void noArgsDefault(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            //Bukkit.getServer().dispatchCommand(commandSender, "is create");
        }
    }
}