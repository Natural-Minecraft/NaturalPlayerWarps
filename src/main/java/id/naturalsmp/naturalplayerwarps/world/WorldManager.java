package id.naturalsmp.naturalplayerwarps.world;

import id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps;
import id.naturalsmp.naturalplayerwarps.warps.Warp;
import id.naturalsmp.naturalplayerwarps.warps.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.concurrent.ConcurrentHashMap;

public class WorldManager {
    private static final ConcurrentHashMap<World, Integer> worlds = new ConcurrentHashMap<>();

    public static void reload() {
        worlds.clear();

        NaturalPlayerWarps.getThreadedQueue().submit(() -> {
            for (World world : Bukkit.getWorlds()) {
                worlds.put(world, NaturalPlayerWarps.getDatabase().getWorldId(world));
            }
        });
    }

    public static ConcurrentHashMap<World, Integer> getWorlds() {
        return worlds;
    }

    public static void onWorldLoad(World world) {
        NaturalPlayerWarps.getThreadedQueue().submit(() -> {
            WorldManager.getWorlds().put(world, NaturalPlayerWarps.getDatabase().getWorldId(world));
        });

        for (Warp warp : WarpManager.getWarps()) {
            if (!world.getName().equals(warp.getWorldName())) continue;
            warp.getLocation().setWorld(world);
        }
    }

    public static void onWorldUnload(World world) {
        WorldManager.getWorlds().remove(world);
    }
}
