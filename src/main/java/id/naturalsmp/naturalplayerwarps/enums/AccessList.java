package id.naturalsmp.naturalplayerwarps.enums;

public enum AccessList {
    WHITELIST("NaturalPlayerWarps_whitelisted", "whitelisted"),
    BLACKLIST("NaturalPlayerWarps_blacklisted", "blacklisted");

    private final String table;
    private final String route;
    AccessList(String table, String route) {
        this.table = table;
        this.route = route;
    }

    public String getTable() {
        return table;
    }

    public String getRoute() {
        return route;
    }
}
