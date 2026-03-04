package id.naturalsmp.naturalplayerwarps.commands.subcommands;

import id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps;
import id.naturalsmp.naturalplayerwarps.converters.ConverterBase;
import id.naturalsmp.naturalplayerwarps.converters.PlayerWarpsConverter;
import id.naturalsmp.naturalplayerwarps.enums.Converters;
import org.bukkit.command.CommandSender;

import static id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps.MESSAGEUTILS;

public enum Converter {
    INSTANCE;

    public void execute(CommandSender sender, Converters converters) {
        ConverterBase cv = switch (converters) {
            case PLAYER_WARPS -> new PlayerWarpsConverter();
        };
        MESSAGEUTILS.sendLang(sender, "converting");
        NaturalPlayerWarps.getThreadedQueue().submit(cv::run);
    }
}
