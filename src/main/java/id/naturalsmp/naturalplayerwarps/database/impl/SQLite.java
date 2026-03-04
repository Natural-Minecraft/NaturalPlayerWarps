package id.naturalsmp.naturalplayerwarps.database.impl;

import id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLite extends Base {
    private final String url = String.format("jdbc:sqlite:%s/data.db", NaturalPlayerWarps.getInstance().getDataFolder());

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String getType() {
        return "SQLite";
    }

    @Override
    public void setup() {
        super.setup();
    }
}
