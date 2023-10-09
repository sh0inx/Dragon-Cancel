package io.github.sh0inx.dragoncancellite.commands;

import io.github.sh0inx.dragoncancellite.DragonCancelLite;
import io.github.sh0inx.dragoncancellite.Substring;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AboutCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getSubString(Substring.DRAGONCANCELTITLE)));
        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getSubString(Substring.DRAGONCANCELSUBTITLE)));
        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getSubString(Substring.LOWLIGHTCOLOR)
                        + "Version: "
                        + DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                        + DragonCancelLite.getInstance().getDescription().getVersion()));
        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getSubString(Substring.LOWLIGHTCOLOR)
                        + "Author: "
                        + DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                        + "sh0inx"));
        sender.sendMessage(IridiumColorAPI.process(
                DragonCancelLite.getInstance().getInstance().getSubString(Substring.LOWLIGHTCOLOR)
                        + "Modrinth Page: "
                        + DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                        + "[placeholder]"));
        return true;
    }
}
