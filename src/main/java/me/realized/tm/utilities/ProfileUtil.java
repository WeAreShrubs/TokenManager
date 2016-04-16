package me.realized.tm.utilities;

import me.realized.tm.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class ProfileUtil {
    //KookyKraftMC Start
    static File spigotFile = new File(Bukkit.getWorldContainer(), "spigot.yml");//get spigot config file
    static FileConfiguration spigotCfg = YamlConfiguration.loadConfiguration(spigotFile);//load spigot config file
    @SuppressWarnings("deprecation")
    public static UUID getUniqueId(String username) {
        if (Bukkit.getOnlineMode() || spigotCfg.getBoolean("settings.bungeecord")) {//use spigot config file to see if bungeecord is enabled //KookyKraftMC end
            if (Bukkit.getPlayerExact(username) != null) {
                return Bukkit.getPlayerExact(username).getUniqueId();
            }

            if (Bukkit.getOfflinePlayer(username).hasPlayedBefore()) {
                return Bukkit.getOfflinePlayer(username).getUniqueId();
            }
        }

        PlayerProfile profile = UUIDMap.get(username);

        if (profile != null) {
            return profile.getUUID();
        }

        try {
            UUID uuid = UUIDFetcher.getUUIDOf(username);
            UUIDMap.place(username, uuid);
            return uuid;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String getName(UUID uuid) {
        if (Bukkit.getOnlineMode() || spigotCfg.getBoolean("settings.bungeecord")) {
            if (Bukkit.getPlayer(uuid) != null) {
                return Bukkit.getPlayer(uuid).getName();
            }

            if (Bukkit.getOfflinePlayer(uuid).hasPlayedBefore()) {
                return Bukkit.getOfflinePlayer(uuid).getName();
            }
        }

        PlayerProfile profile = NameMap.get(uuid);

        if (profile != null) {
            return profile.getName();
        }

        try {
            String name = NameFetcher.getNameOf(uuid);
            NameMap.place(uuid, name);
            return name;
        } catch (Exception e) {
            Core.getInstance().warn("Failed to fetch username for " + uuid.toString() + ": " + e.getMessage());
            return null;
        }
    }
}
