package id.naturalsmp.naturalplayerwarps.hooks;

import com.artillexstudios.axapi.utils.StringUtils;
import com.artillexstudios.axintegrations.AxIntegrations;
import com.artillexstudios.axintegrations.integration.protection.ProtectionIntegration;
import com.artillexstudios.axintegrations.integration.protection.ProtectionIntegrations;
import id.naturalsmp.naturalplayerwarps.hooks.currency.AxQuestBoardHook;
// import id.naturalsmp.naturalplayerwarps.hooks.currency.BeastTokensHook;
// import id.naturalsmp.naturalplayerwarps.hooks.currency.CoinsEngineHook;
import id.naturalsmp.naturalplayerwarps.hooks.currency.CurrencyHook;
// import id.naturalsmp.naturalplayerwarps.hooks.currency.EcoBitsHook;
import id.naturalsmp.naturalplayerwarps.hooks.currency.ExperienceHook;
// import id.naturalsmp.naturalplayerwarps.hooks.currency.KingdomsXHook;
import id.naturalsmp.naturalplayerwarps.hooks.currency.PlaceholderCurrencyHook;
import id.naturalsmp.naturalplayerwarps.hooks.currency.PlayerPointsHook;
import id.naturalsmp.naturalplayerwarps.hooks.currency.RedisEconomyHook;
// import id.naturalsmp.naturalplayerwarps.hooks.currency.RivalHarvesterHoesHook;
// import id.naturalsmp.naturalplayerwarps.hooks.currency.RoyaleEconomyHook;
import id.naturalsmp.naturalplayerwarps.hooks.currency.SuperMobCoinsHook;
// import id.naturalsmp.naturalplayerwarps.hooks.currency.TheOnlyMobCoins;
import id.naturalsmp.naturalplayerwarps.hooks.currency.TokenManagerHook;
// import id.naturalsmp.naturalplayerwarps.hooks.currency.UltraEconomyHook;
import id.naturalsmp.naturalplayerwarps.hooks.currency.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;

import static id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps.CURRENCIES;
import static id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps.HOOKS;

public class HookManager {
    private static final ArrayList<CurrencyHook> currency = new ArrayList<>();

    public static void setupHooks() {
        updateHooks();
    }

    public static void updateHooks() {
        currency.removeIf(currencyHook -> !currencyHook.isPersistent());

        ProtectionIntegrations.values().clear();
        AxIntegrations.INSTANCE.init();

        ProtectionIntegrations.values()
                .removeIf(integration -> !HOOKS.getBoolean("hooks.protection." + integration.id(), false));
        boolean modified = false;
        for (ProtectionIntegration integration : ProtectionIntegrations.values()) {
            if (HOOKS.getString("hooks.protection." + integration.id(), null) == null) {
                modified = true;
                HOOKS.set("hooks.protection." + integration.id(), true);
            }
            Bukkit.getConsoleSender().sendMessage(
                    StringUtils.formatToString("&#33FF33[NaturalPlayerWarps] Hooked into " + integration.id() + "!"));
        }
        if (modified)
            HOOKS.save();

        if (CURRENCIES.getBoolean("currencies.Experience.register", true))
            currency.add(new ExperienceHook());

        if (CURRENCIES.getBoolean("currencies.Vault.register", true)
                && Bukkit.getPluginManager().getPlugin("Vault") != null) {
            currency.add(new VaultHook());
            Bukkit.getConsoleSender()
                    .sendMessage(StringUtils.formatToString("&#33FF33[NaturalPlayerWarps] Hooked into Vault!"));
        }

        if (CURRENCIES.getBoolean("currencies.PlayerPoints.register", true)
                && Bukkit.getPluginManager().getPlugin("PlayerPoints") != null) {
            currency.add(new PlayerPointsHook());
            Bukkit.getConsoleSender()
                    .sendMessage(StringUtils.formatToString("&#33FF33[NaturalPlayerWarps] Hooked into PlayerPoints!"));
        }

        /*
         * if (CURRENCIES.getBoolean("currencies.CoinsEngine.register", true)
         * && Bukkit.getPluginManager().getPlugin("CoinsEngine") != null) {
         * for (Map<Object, Object> curr :
         * CURRENCIES.getMapList("currencies.CoinsEngine.enabled")) {
         * currency.add(new CoinsEngineHook((String) curr.get("currency-name"), (String)
         * curr.get("name")));
         * }
         * Bukkit.getConsoleSender()
         * .sendMessage(StringUtils.
         * formatToString("&#33FF33[NaturalPlayerWarps] Hooked into CoinsEngine!"));
         * }
         */

        /*
         * if (CURRENCIES.getBoolean("currencies.RoyaleEconomy.register", true) &&
         * Bukkit.getPluginManager().getPlugin("RoyaleEconomy") != null) {
         * currency.add(new RoyaleEconomyHook());
         * Bukkit.getConsoleSender().sendMessage(StringUtils.
         * formatToString("&#33FF33[NaturalPlayerWarps] Hooked into RoyaleEconomy!"));
         * }
         */

        /*
         * if (CURRENCIES.getBoolean("currencies.UltraEconomy.register", true)
         * && Bukkit.getPluginManager().getPlugin("UltraEconomy") != null) {
         * for (Map<Object, Object> curr :
         * CURRENCIES.getMapList("currencies.UltraEconomy.enabled")) {
         * currency.add(new UltraEconomyHook((String) curr.get("currency-name"),
         * (String) curr.get("name")));
         * }
         * Bukkit.getConsoleSender()
         * .sendMessage(StringUtils.
         * formatToString("&#33FF33[NaturalPlayerWarps] Hooked into UltraEconomy!"));
         * }
         */

        /*
         * if (CURRENCIES.getBoolean("currencies.KingdomsX.register", true) &&
         * Bukkit.getPluginManager().getPlugin("Kingdoms") != null) {
         * currency.add(new KingdomsXHook());
         * Bukkit.getConsoleSender().sendMessage(StringUtils.
         * formatToString("&#33FF33[NaturalPlayerWarps] Hooked into KingdomsX!"));
         * }
         */

        /*
         * if (CURRENCIES.getBoolean("currencies.RivalHarvesterHoes.register", true) &&
         * Bukkit.getPluginManager().getPlugin("RivalHarvesterHoes") != null) {
         * currency.add(new RivalHarvesterHoesHook());
         * Bukkit.getConsoleSender().sendMessage(StringUtils.
         * formatToString("&#33FF33[NaturalPlayerWarps] Hooked into RivalHarvesterHoes!"
         * ));
         * }
         */

        if (CURRENCIES.getBoolean("currencies.SuperMobCoins.register", true)
                && Bukkit.getPluginManager().getPlugin("SuperMobCoins") != null) {
            currency.add(new SuperMobCoinsHook());
            Bukkit.getConsoleSender()
                    .sendMessage(StringUtils.formatToString("&#33FF33[NaturalPlayerWarps] Hooked into SuperMobCoins!"));
        }

        /*
         * if (CURRENCIES.getBoolean("currencies.TheOnly-MobCoins.register", true) &&
         * Bukkit.getPluginManager().getPlugin("TheOnly-MobCoins") != null) {
         * currency.add(new TheOnlyMobCoins());
         * Bukkit.getConsoleSender().sendMessage(StringUtils.
         * formatToString("&#33FF33[NaturalPlayerWarps] Hooked into TheOnly-MobCoins!"))
         * ;
         * }
         */

        if (CURRENCIES.getBoolean("currencies.TokenManager.register", true)
                && Bukkit.getPluginManager().getPlugin("TokenManager") != null) {
            currency.add(new TokenManagerHook());
            Bukkit.getConsoleSender()
                    .sendMessage(StringUtils.formatToString("&#33FF33[NaturalPlayerWarps] Hooked into TokenManager!"));
        }

        if (CURRENCIES.getBoolean("currencies.AxQuestBoard.register", true)
                && Bukkit.getPluginManager().getPlugin("AxQuestBoard") != null) {
            currency.add(new AxQuestBoardHook());
            Bukkit.getConsoleSender()
                    .sendMessage(StringUtils.formatToString("&#33FF33[NaturalPlayerWarps] Hooked into AxQuestBoard!"));
        }

        if (CURRENCIES.getBoolean("currencies.RedisEconomy.register", true)
                && Bukkit.getPluginManager().getPlugin("RedisEconomy") != null) {
            for (Map<Object, Object> curr : CURRENCIES.getMapList("currencies.RedisEconomy.enabled")) {
                currency.add(new RedisEconomyHook((String) curr.get("currency-name"), (String) curr.get("name")));
            }
            Bukkit.getConsoleSender()
                    .sendMessage(StringUtils.formatToString("&#33FF33[NaturalPlayerWarps] Hooked into RedisEconomy!"));
        }

        /*
         * if (CURRENCIES.getBoolean("currencies.BeastTokens.register", true) &&
         * Bukkit.getPluginManager().getPlugin("BeastTokens") != null) {
         * currency.add(new BeastTokensHook());
         * Bukkit.getConsoleSender().sendMessage(StringUtils.
         * formatToString("&#33FF33[NaturalPlayerWarps] Hooked into BeastTokens!"));
         * }
         */

        /*
         * if (CURRENCIES.getBoolean("currencies.EcoBits.register", true)
         * && Bukkit.getPluginManager().getPlugin("EcoBits") != null) {
         * for (Map<Object, Object> curr :
         * CURRENCIES.getMapList("currencies.EcoBits.enabled")) {
         * currency.add(new EcoBitsHook((String) curr.get("currency-name"), (String)
         * curr.get("name")));
         * }
         * Bukkit.getConsoleSender()
         * .sendMessage(StringUtils.
         * formatToString("&#33FF33[NaturalPlayerWarps] Hooked into EcoBits!"));
         * }
         */

        for (String str : CURRENCIES.getSection("placeholder-currencies").getRoutesAsStrings(false)) {
            if (!CURRENCIES.getBoolean("placeholder-currencies." + str + ".register", false))
                continue;
            currency.add(new PlaceholderCurrencyHook(str, CURRENCIES.getSection("placeholder-currencies." + str)));
            Bukkit.getConsoleSender().sendMessage(StringUtils
                    .formatToString("&#33FF33[NaturalPlayerWarps] Loaded placeholder currency " + str + "!"));
        }

        for (CurrencyHook hook : currency)
            hook.setup();
    }

    @SuppressWarnings("unused")
    public static void registerCurrencyHook(@NotNull Plugin plugin, @NotNull CurrencyHook currencyHook) {
        currency.add(currencyHook);
        Bukkit.getConsoleSender().sendMessage(StringUtils.formatToString("&#33FF33[NaturalPlayerWarps] Hooked into "
                + plugin.getName()
                + "! Note: You must set the currency provider to CUSTOM or it will be overridden after reloading!"));
    }

    @NotNull
    public static ArrayList<CurrencyHook> getCurrency() {
        return currency;
    }

    @Nullable
    public static CurrencyHook getCurrencyHook(@NotNull String name) {
        for (CurrencyHook hook : currency) {
            if (!hook.getName().equals(name))
                continue;
            return hook;
        }

        return null;
    }

    public static boolean canBuild(Player player, Location location) {
        for (ProtectionIntegration integration : ProtectionIntegrations.values()) {
            if (integration.canBuild(player, location))
                continue;
            return false;
        }
        return true;
    }
}
