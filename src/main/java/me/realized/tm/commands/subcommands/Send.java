package me.realized.tm.commands.subcommands;

import me.realized.tm.utilities.ProfileUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Send extends SubCommand {

    public Send() {
        super(new String[]{"send"}, "send <username> <amount>", "use.send", 3);
    }

    @Override
    public void run(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player)) {
            pm(sender, "&cConsole can not send tokens! :(");
            return;
        }

        Player player = (Player) sender;
        UUID target = ProfileUtil.getUniqueId(args[1]);

        if (target == null || !dataManager.found(target)) {
            pm(sender, config.getString("invalid-player").replace("%input%", args[1]));
            return;
        }

        int amount;

        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            pm(sender, config.getString("invalid-amount").replace("%input%", String.valueOf(args[2])));
            return;
        }

        long balance = dataManager.balance(player.getUniqueId());

        if (amount <= 0 || balance - amount < 0) {
            pm(sender, config.getString("invalid-amount").replace("%input%", String.valueOf(args[2])));
            return;
        }

        boolean removeSuccess = dataManager.remove(player.getUniqueId(), amount);

        if (!removeSuccess) {
            sendWarning(sender, "remove");
            return;
        }

        boolean addSuccess = dataManager.add(target, amount);

        if (!addSuccess) {
            sendWarning(sender, "add");
            return;
        }

        String sent = String.valueOf(amount);
        pm(sender, config.getString("on-send").replace("%amount%", sent).replace("%player%", args[1]));

        if (Bukkit.getPlayer(target) != null) {
            pm(Bukkit.getPlayer(target), config.getString("on-receive").replace("%amount%", sent));
        }
    }
}
