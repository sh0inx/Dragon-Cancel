package io.github.sh0inx.dragoncancel.commands;

import io.github.sh0inx.dragoncancel.DragonCancel;
import io.github.sh0inx.dragoncancel.Substring;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AboutCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        sender.sendMessage(IridiumColorAPI.process(DragonCancel.getInstance().getSubString(Substring.DRAGONCANCELTITLE)));
        sender.sendMessage(IridiumColorAPI.process(DragonCancel.getInstance().getSubString(
                Substring.HIGHLIGHTCOLOR) + "Version: &l" + DragonCancel.getInstance().getDescription().getVersion()));
        sender.sendMessage(IridiumColorAPI.process(DragonCancel.getInstance().getSubString(
                Substring.HIGHLIGHTCOLOR) + "Author: &l" + DragonCancel.getInstance().getDescription().getAuthors()));
        sender.sendMessage(IridiumColorAPI.process(DragonCancel.getInstance().getSubString(
                Substring.HIGHLIGHTCOLOR)) + "Modrinth Page: &l" + "[placeholder]");
        return true;
    }
}
