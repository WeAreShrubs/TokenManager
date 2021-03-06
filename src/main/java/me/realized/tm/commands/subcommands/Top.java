package me.realized.tm.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Top extends SubCommand {

    public Top() {
        super(new String[]{"top"}, "top", "use.top", 1);
    }

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        pm(sender, config.getString("top-total-users").replace("%users%", String.valueOf(dataManager.size())));
        pm(sender, config.getString("top-next-update").replace("%remaining%", dataManager.getNextUpdate()));

        List<String> top = dataManager.getTopBalances();

        pm(sender, config.getString("top-header").replace("%id%", "1").replace("%total%", String.valueOf(top.size())));

        for (String s : top) {
            String[] data = s.split(":");

            if (data.length <= 1) {
                pm(sender, s);
                break;
            }

            String format = config.getString("top-format");
            format = format.replace("%rank%", data[0]).replace("%name%", data[1]).replace("%tokens%", data[2]);
            pm(sender, format);
        }

        pm(sender, config.getString("top-footer"));
    }
}
