package id.naturalsmp.naturalplayerwarps.warps;

import id.naturalsmp.naturalplayerwarps.hooks.currency.CurrencyHook;
import org.bukkit.Location;

public record TeleportData(Warp warp, long date, Location location, CurrencyHook currency, double teleportPrice) {
}
