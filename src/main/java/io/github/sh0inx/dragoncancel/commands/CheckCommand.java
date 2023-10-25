package io.github.sh0inx.dragoncancel.commands;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import io.github.sh0inx.dragoncancel.DragonCancel;

import io.github.sh0inx.heart.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.World;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CheckCommand extends Command implements CommandExecutor {

    public CheckCommand() {
        super(Collections.singletonList("check"), "Checks whether a world is dragon cancelled.", "/dc check", "");
    }

    public boolean execute(CommandSender sender, String[] args) {
        String worldName = args[0];
        List<World> worldList = Bukkit.getWorlds();

        World selectedWorld = null;
        for(World world : worldList) {
            if(worldName == world.getName()) {
                selectedWorld = world;
            }
        }

        if(selectedWorld == null) {
            sender.sendMessage(IridiumColorAPI.process(DragonCancel.getInstance().getConfigManager().getMessages().commandPrefix()
                    + " didn't find \"" + selectedWorld.getName() + "\", try again"
            ));
            return false;
        }

        if (selectedWorld.getEnvironment() != World.Environment.THE_END) {
            sender.sendMessage(IridiumColorAPI.process(DragonCancel.getInstance().getConfigManager().getMessages().commandPrefix()
                    + DragonCancel.getInstance().getConfigManager().getMessages().highlightColor
                    + "\"" + selectedWorld.getName() + "\" - "
                    + "&d&lnot an end world"
            ));
        }

        if(!selectedWorld.getEnderDragonBattle().hasBeenPreviouslyKilled()) {

            sender.sendMessage(IridiumColorAPI.process(DragonCancel.getInstance().getConfigManager().getMessages().commandPrefix()
                    + DragonCancel.getInstance().getConfigManager().getMessages().highlightColor
                    + "\"" + selectedWorld.getName() + "\" - "
                    + "&e&lpopulated"
            ));
        }

        for(Map.Entry<String, Boolean> world : DragonCancel.getInstance().getWorldsToCancel().entrySet()) {
            if(selectedWorld.getName() == world.getKey()) {
                sender.sendMessage(IridiumColorAPI.process(DragonCancel.getInstance().getConfigManager().getMessages().commandPrefix()
                        + DragonCancel.getInstance().getConfigManager().getMessages().highlightColor
                        + "\"" + selectedWorld.getName() + "\" - "
                        + "&c&l#cancelled"
                ));
            }
        }

        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return true;
    }
}