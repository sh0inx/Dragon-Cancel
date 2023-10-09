package io.github.sh0inx.dragoncancellite.commands;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import io.github.sh0inx.dragoncancellite.DragonCancelLite;
import io.github.sh0inx.dragoncancellite.Substring;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getSubString(Substring.DRAGONCANCELTITLE)));
        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getSubString(Substring.DRAGONCANCELSUBTITLE)));
        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                        + "/dc about&7: shows information about the plugin."));
        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                        + "/dc check <world>&7: checks cancel status of specified world"));
        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                        + "/dc help&7: shows the list of available commands (this list)."));

        return false;
    }
}
