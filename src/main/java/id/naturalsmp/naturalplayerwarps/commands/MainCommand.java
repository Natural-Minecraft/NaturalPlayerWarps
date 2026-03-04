package id.naturalsmp.naturalplayerwarps.commands;

import com.artillexstudios.axapi.utils.StringUtils;
import id.naturalsmp.naturalplayerwarps.commands.annotations.AllWarps;
import id.naturalsmp.naturalplayerwarps.commands.annotations.OwnWarps;
import id.naturalsmp.naturalplayerwarps.commands.subcommands.Create;
import id.naturalsmp.naturalplayerwarps.commands.subcommands.Info;
import id.naturalsmp.naturalplayerwarps.commands.subcommands.Open;
import id.naturalsmp.naturalplayerwarps.guis.EditWarpGui;
import id.naturalsmp.naturalplayerwarps.warps.Warp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.orphan.OrphanCommand;

import static id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps.LANG;
import static id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps.MESSAGEUTILS;

public class MainCommand implements OrphanCommand {

    @DefaultFor({"~"})
    @CommandPermission("NaturalPlayerWarps.open")
    public void open(@NotNull CommandSender sender, @Optional @CommandPermission("NaturalPlayerWarps.use") Warp warp) {
        if (warp != null) {
            if (!(sender instanceof Player pl)) throw new CommandErrorException("must-be-player");
            warp.teleportPlayer(pl);
            return;
        }
        Open.INSTANCE.execute(sender, null);
    }

    @Subcommand({"help"})
    @CommandPermission("NaturalPlayerWarps.help")
    public void help(@NotNull CommandSender sender) {
        for (String m : LANG.getStringList("help")) {
            sender.sendMessage(StringUtils.formatToString(m));
        }
    }

    @Subcommand({"open"})
    @CommandPermission("NaturalPlayerWarps.open")
    public void open2(@NotNull CommandSender sender, @CommandPermission("NaturalPlayerWarps.open.other") @Optional Player player) {
        Open.INSTANCE.execute(sender, player);
    }

    @Subcommand({"warp", "go"})
    @CommandPermission("NaturalPlayerWarps.use")
    public void warp(@NotNull Player sender, @AllWarps Warp warp) {
        warp.teleportPlayer(sender);
    }

    @Subcommand({"create", "set"})
    @CommandPermission("NaturalPlayerWarps.create") // @CommandPermission("NaturalPlayerWarps.create.other") @Optional OfflinePlayer player
    public void create(@NotNull Player sender, String warpName) {
        Create.INSTANCE.execute(sender, warpName, null);
    }

    @Subcommand({"delete"})
    @CommandPermission("NaturalPlayerWarps.delete")
    public void delete(@NotNull Player sender, @OwnWarps Warp warp) {
        if (!warp.getOwner().equals(sender.getUniqueId())) {
            MESSAGEUTILS.sendLang(sender, "errors.not-your-warp");
            return;
        }
        warp.delete();
    }

    @Subcommand({"edit", "settings"})
    @CommandPermission("NaturalPlayerWarps.edit")
    public void edit(@NotNull Player sender, @OwnWarps Warp warp) {
        if (!warp.getOwner().equals(sender.getUniqueId())) {
            MESSAGEUTILS.sendLang(sender, "errors.not-your-warp");
            return;
        }
        new EditWarpGui(sender, warp).open();
    }

    @Subcommand({"info"})
    @CommandPermission("NaturalPlayerWarps.info")
    public void info(@NotNull CommandSender sender, @AllWarps Warp warp) {
        Info.INSTANCE.execute(sender, warp);
    }
}
