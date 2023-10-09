package io.github.sh0inx.dragoncancellite.commands;

import io.github.sh0inx.dragoncancellite.DragonCancelLite;
import io.github.sh0inx.dragoncancellite.Substring;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CheckCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length < 2) {
            for(String world : DragonCancelLite.getInstance().getWorldsToCancel()) {
                sender.sendMessage(IridiumColorAPI.process(DragonCancelLite.getInstance().getSubString(Substring.MESSAGEPREFIX)
                        + DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                        + "\"" + world + "\" - "
                        + "&c#cancelled"
                ));
            }

            return true;
        }

        String worldName = null;

        for(String world : DragonCancelLite.getInstance().getWorldsToCancel()) {
            if (world.equalsIgnoreCase(args[1])) {
                worldName = world;
            }
        }

        if(args.length > 2) {
            sender.sendMessage(IridiumColorAPI.process(DragonCancelLite.getInstance().getSubString(Substring.MESSAGEPREFIX)
                    + DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                    + "specify a single world pls"
            ));
            return false;
        }

        if(args[1].equalsIgnoreCase(worldName)) {
            sender.sendMessage(IridiumColorAPI.process(DragonCancelLite.getInstance().getSubString(Substring.MESSAGEPREFIX)
                    + DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                    + "\"" + args[1] + "\" - "
                    + "&c#cancelled"
            ));
        }

        if(!args[1].equalsIgnoreCase(worldName)) {
            sender.sendMessage(IridiumColorAPI.process(DragonCancelLite.getInstance().getSubString(Substring.MESSAGEPREFIX)
                    + DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                    + "\"" + args[1] + "\" - "
                    + "&epopulated"
            ));
        }

        return true;
    }
}
