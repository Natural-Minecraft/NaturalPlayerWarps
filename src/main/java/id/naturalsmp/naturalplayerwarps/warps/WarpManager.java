package id.naturalsmp.naturalplayerwarps.warps;

import id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps;

import java.util.ArrayList;

public class WarpManager {
    private static final ArrayList<Warp> warps = new ArrayList<>();

    public static void load() {
        NaturalPlayerWarps.getThreadedQueue().submit(() -> NaturalPlayerWarps.getDatabase().loadWarps());
    }

    public static ArrayList<Warp> getWarps() {
        return warps;
    }
}
