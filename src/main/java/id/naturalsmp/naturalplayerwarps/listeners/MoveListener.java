package id.naturalsmp.naturalplayerwarps.listeners;

import id.naturalsmp.naturalplayerwarps.warps.WarpQueue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps.MESSAGEUTILS;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo() == null) return;
        if (!WarpQueue.getQueue().containsKey(event.getPlayer())) return;
        // using distanceSquared to avoid heavy Math.sqrt call
        if (event.getFrom().distanceSquared(event.getTo()) < 0.005) return;
        WarpQueue.getQueue().remove(event.getPlayer());
        MESSAGEUTILS.sendLang(event.getPlayer(), "errors.moved");
    }
}
