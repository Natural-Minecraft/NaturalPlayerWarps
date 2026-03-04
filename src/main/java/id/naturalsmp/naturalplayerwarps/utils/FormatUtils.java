package id.naturalsmp.naturalplayerwarps.utils;

import id.naturalsmp.naturalplayerwarps.hooks.currency.CurrencyHook;
import id.naturalsmp.naturalplayerwarps.placeholders.WarpPlaceholders;

public class FormatUtils {

    public static String formatCurrency(CurrencyHook currencyHook, double amount) {
        return currencyHook == null ? WarpPlaceholders.format(amount) : currencyHook.getDisplayName()
                        .replace("%price%", WarpPlaceholders.format(amount));
    }
}
