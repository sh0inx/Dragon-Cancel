package io.github.sh0inx.dragoncancellite.managers;

import io.github.sh0inx.dragoncancellite.DragonCancelLite;
import io.github.sh0inx.dragoncancellite.Substring;
import io.github.sh0inx.dragoncancellite.commands.AboutCommand;
import io.github.sh0inx.dragoncancellite.commands.CheckCommand;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import io.github.sh0inx.dragoncancellite.commands.HelpCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor{

    AboutCommand aboutCommand = new AboutCommand();
    CheckCommand checkCommand = new CheckCommand();
    HelpCommand helpCommand = new HelpCommand();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        try {
            if(args[0] != null || !args[0].isEmpty()) {

                try {
                    switch(args[0].toLowerCase()) {
                        case "about": {
                            aboutCommand.onCommand(sender, command, s, args);
                        }
                        case "info": {
                            break;
                        }
                        case "check": {
                            checkCommand.onCommand(sender, command, s, args);
                            break;
                        }
                        case "help": {
                            helpCommand.onCommand(sender, command, s, args);
                            break;
                        }
                        default: {
                            sender.sendMessage(IridiumColorAPI.process(
                                    DragonCancelLite.getInstance().getSubString(Substring.MESSAGEPREFIX)
                                            + DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                                            + "sorry, \"" + args[0] + "\" isn't a command"));
                        }
                    }

                } catch (Exception e) {
                    DragonCancelLite.getInstance().getLogger().warning("ERROR: Failed to handle command \"" + args[0] + "\". (" + e + ")");
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            //TODO: Have this bring up the default rewards screen (or just accept rewards, dependent on config).
            sender.sendMessage(IridiumColorAPI.process(
                    DragonCancelLite.getInstance().getSubString(Substring.MESSAGEPREFIX)
                            + DragonCancelLite.getInstance().getSubString(Substring.HIGHLIGHTCOLOR)
                            + "you gotta specify a command"));
        }

        return true;
    }
}