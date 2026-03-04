package id.naturalsmp.naturalplayerwarps.user;

import com.artillexstudios.axguiframework.GuiFrame;
import id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps;
import id.naturalsmp.naturalplayerwarps.category.Category;
import id.naturalsmp.naturalplayerwarps.category.CategoryManager;
import id.naturalsmp.naturalplayerwarps.sorting.Sort;
import id.naturalsmp.naturalplayerwarps.sorting.SortingManager;
import id.naturalsmp.naturalplayerwarps.warps.Warp;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WarpUser {
    private final Player player;
    private int sortingIdx = 0;
    private int categoryIdx = -1;
    private final CircularFifoQueue<GuiFrame> lastGuis = new CircularFifoQueue<>(5);
    private List<Warp> favorites = Collections.synchronizedList(new ArrayList<>());

    public WarpUser(Player player) {
        this.player = player;

        NaturalPlayerWarps.getThreadedQueue().submit(() -> {
            NaturalPlayerWarps.getDatabase().loadOrUpdate(player);
            favorites = Collections.synchronizedList(NaturalPlayerWarps.getDatabase().getFavoriteWarps(player));
        });
    }

    public Player getPlayer() {
        return player;
    }

    public void resetSorting() {
        sortingIdx = 0;
    }

    public void changeSorting(int am) {
        sortingIdx += am;
    }

    public Sort getSorting() {
        int a = sortingIdx;
        int b = SortingManager.getEnabledSorting().size();
        return SortingManager.getEnabledSorting().get((a % b + b) % b);
    }

    public void resetCategory() {
        categoryIdx = -1;
    }

    public void changeCategory(int am) {
        categoryIdx += am;
    }

    public Category getCategory() {
        int a = categoryIdx;
        int b = CategoryManager.getCategories().size();
        return CategoryManager.getCategories().values().stream().toList().get((a % b + b) % b);
    }

    public CircularFifoQueue<GuiFrame> getLastGuis() {
        return lastGuis;
    }

    public void addGui(GuiFrame guiFrame) {
        lastGuis.add(guiFrame);
//        System.out.println("add: " + lastGuis.size() + " " + guiFrame);
    }

    public List<Warp> getFavorites() {
        return favorites;
    }

    public int getWarpLimit() {
        if (hasBypass(player)) return Integer.MAX_VALUE;
        int am = player.hasPermission("NaturalPlayerWarps.warps.1") ? 1 : 0;
        for (PermissionAttachmentInfo pai : player.getEffectivePermissions()) {
            if (!pai.getValue()) continue;
            if (!pai.getPermission().startsWith("NaturalPlayerWarps.warps.")) continue;

            int value = Integer.parseInt(pai.getPermission().substring(pai.getPermission().lastIndexOf('.') + 1));
            if (value > am) am = value;
        }
        return am;
    }

    private static boolean hasBypass(OfflinePlayer offlinePlayer) {
        if (offlinePlayer.isOp()) return true;
        final Player player = offlinePlayer.getPlayer();
        if (player == null) return false;
        if (player.hasPermission("*")) return true;
        if (player.hasPermission("NaturalPlayerWarps.warps.*")) return true;
        return false;
    }
}
