package id.naturalsmp.naturalplayerwarps.commands;

import com.artillexstudios.axapi.utils.StringUtils;
import id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps;
import id.naturalsmp.naturalplayerwarps.commands.annotations.AllWarps;
import id.naturalsmp.naturalplayerwarps.commands.subcommands.Converter;
import id.naturalsmp.naturalplayerwarps.commands.subcommands.Reload;
import id.naturalsmp.naturalplayerwarps.enums.AccessList;
import id.naturalsmp.naturalplayerwarps.enums.Converters;
import id.naturalsmp.naturalplayerwarps.warps.Warp;
import id.naturalsmp.naturalplayerwarps.warps.WarpManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.orphan.OrphanCommand;

import java.util.Map;

import static id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps.LANG;
import static id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps.MESSAGEUTILS;

public class AdminCommand implements OrphanCommand {

    @DefaultFor({"~", "~ help"})
    @CommandPermission("naturalplayerwarps.admin.help")
    public void help(@NotNull CommandSender sender) {
        for (String m : LANG.getStringList("admin-help")) {
            sender.sendMessage(StringUtils.formatToString(m));
        }
    }

    @Subcommand("reload")
    @CommandPermission("naturalplayerwarps.admin.reload")
    public void reload(@NotNull CommandSender sender) {
        Reload.INSTANCE.execute(sender);
    }

    @Subcommand("delete")
    @CommandPermission("naturalplayerwarps.admin.delete")
    public void delete(@NotNull CommandSender sender, @AllWarps Warp warp) {
        NaturalPlayerWarps.getThreadedQueue().submit(() -> {
            NaturalPlayerWarps.getDatabase().deleteWarp(warp);
            MESSAGEUTILS.sendLang(sender, "admin.deleted", Map.of("%warp%", warp.getName()));
        });
    }

    @Subcommand("deleteid")
    @CommandPermission("naturalplayerwarps.admin.delete")
    public void deleteId(@NotNull CommandSender sender, int id) {
        NaturalPlayerWarps.getThreadedQueue().submit(() -> {
            Warp warp = null;
            for (Warp w : WarpManager.getWarps()) {
                if (id != w.getId()) continue;
                warp = w;
                break;
            }
            if (warp == null) {
                MESSAGEUTILS.sendLang(sender, "errors.not-found", Map.of("%warp%", String.valueOf(id)));
                throw new CommandErrorException();
            }
            NaturalPlayerWarps.getDatabase().deleteWarp(warp);
            MESSAGEUTILS.sendLang(sender, "admin.deleted", Map.of("%warp%", warp.getName()));
        });
    }

    @Subcommand("setowner")
    @CommandPermission("naturalplayerwarps.admin.setowner")
    public void setOwner(@NotNull CommandSender sender, @AllWarps Warp warp, OfflinePlayer player) {
        NaturalPlayerWarps.getThreadedQueue().submit(() -> {
            warp.setOwner(player.getUniqueId());
            NaturalPlayerWarps.getDatabase().updateWarp(warp);
            NaturalPlayerWarps.getDatabase().removeFromList(warp, AccessList.WHITELIST, player);
            NaturalPlayerWarps.getDatabase().removeFromList(warp, AccessList.BLACKLIST, player);

            MESSAGEUTILS.sendLang(sender, "admin.setowner", Map.of("%warp%", warp.getName(), "%player%", player.getName() == null ? "---" : player.getName()));
        });
    }

    @Subcommand("converter")
    @CommandPermission("naturalplayerwarps.admin.converter")
    public void converter(CommandSender sender, Converters converters) {
        Converter.INSTANCE.execute(sender, converters);
    }
}
