package me.realized.tm.utilities;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UUIDMap {

    private static final Map<String, PlayerProfile> uuids = new ConcurrentHashMap<>();

    protected static void place(String name, UUID uuid) {
        uuids.put(name.toLowerCase(), new PlayerProfile(uuid));
    }

    protected static PlayerProfile get(String name) {
        PlayerProfile profile = uuids.get(name.toLowerCase());

        if (profile == null) {
            return null;
        }

        if (profile.getTime() + (1000 * 600) - System.currentTimeMillis() <= 0) {
            return null;
        }

        return profile;
    }
}
